/*
 * File Name: TriggerImpl.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-8-15
 * Grant: open source to everybody
 */
package com.china.centet.yongyin.trigger.impl;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.china.center.cache.CacheBootstrap;
import com.china.center.common.ConditionParse;
import com.china.center.jdbc.inter.PublicSQL;
import com.china.center.tools.Mathematica;
import com.china.center.tools.TimeTools;
import com.china.centet.yongyin.bean.BankBean;
import com.china.centet.yongyin.bean.Bill;
import com.china.centet.yongyin.bean.LocationBean;
import com.china.centet.yongyin.bean.LogBean;
import com.china.centet.yongyin.bean.Product;
import com.china.centet.yongyin.bean.ProductAmount;
import com.china.centet.yongyin.bean.StatBankBean;
import com.china.centet.yongyin.bean.StatBean;
import com.china.centet.yongyin.constant.Constant;
import com.china.centet.yongyin.constant.LockConstant;
import com.china.centet.yongyin.constant.OutConstanst;
import com.china.centet.yongyin.dao.BankDAO;
import com.china.centet.yongyin.dao.BillDAO;
import com.china.centet.yongyin.dao.CommonDAO;
import com.china.centet.yongyin.dao.CustomerDAO;
import com.china.centet.yongyin.dao.LocationDAO;
import com.china.centet.yongyin.dao.OutDAO;
import com.china.centet.yongyin.dao.ProductDAO;
import com.china.centet.yongyin.dao.StatDAO;
import com.china.centet.yongyin.dao.StorageDAO;
import com.china.centet.yongyin.trigger.Trigger;


/**
 * ����ͳ��
 * 
 * @author zhuzhu
 * @version 2007-8-15
 * @see
 * @since
 */
public class TriggerImpl implements Trigger
{
    private final Log _logger = LogFactory.getLog(getClass());

    private final Log logger = LogFactory.getLog("sec");

    private final Log effLogger = LogFactory.getLog("eff");

    private final Log monitorLog = LogFactory.getLog("bill");

    private StatDAO statDAO = null;

    private CommonDAO commonDAO = null;

    private BankDAO bankDAO = null;

    private BillDAO billDAO = null;

    private OutDAO outDAO = null;

    private CustomerDAO customerDAO = null;

    private LocationDAO locationDAO = null;

    private ProductDAO productDAO = null;

    private StorageDAO storageDAO = null;

    private PublicSQL publicSQL = null;

    private CacheBootstrap cacheBootstrap = null;

    private DataSourceTransactionManager transactionManager = null;

    public void statBankEveryDay()
    {
        // �������
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DAY_OF_MONTH, -1);

        String statId = TimeTools.getStringByFormat(new Date(cal.getTimeInMillis()), "yyyyMMdd");

        List<String> bankList = commonDAO.listAll("t_center_bank");

        // ͳ��������˵�����������ͳ�Ƶ�����
        for (String bank : bankList)
        {
            StatBankBean sb = statDAO.findStatBankBean(statId, bank);

            if (sb != null)
            {
                continue;
            }

            // ִ��ͳ��
            statBankEveryDayInner(statId, bank);
        }
    }

    private void statBankEveryDayInner(String statId, String bank)
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();
        condtion.addCommonCondition("dates", "=", publicSQL.to_date(getF(statId), "yyyy-MM-dd"));

        BankBean bb = bankDAO.findBankByName(bank);

        if (bb == null)
        {
            return;
        }

        condtion.addCondition("bank", "=", bb.getId());

        List<Bill> billList = billDAO.queryBillByCondition(condtion);

        statBankInner(billList, statId, bank);
    }

    /**
     * ÿ��ͳ��
     * 
     * @param billList
     * @param statId
     * @param llastId
     * @param bank
     * @param isFlag
     * @return
     */
    private StatBankBean statBankInner(List<Bill> billList, String statId, String bank)
    {
        StatBankBean news = new StatBankBean();

        try
        {
            // ͳ�Ʊ��µ�
            double in = 0.0d;
            double out = 0.0d;
            for (Bill bill : billList)
            {
                if (bill.getType() == Constant.BILL_RECIVE)
                {
                    Mathematica math = new Mathematica(in);

                    in = math.add(bill.getMoneys()).toDouble();
                }

                if (bill.getType() == Constant.BILL_PAY)
                {
                    Mathematica math = new Mathematica(out);

                    out = math.add(bill.getMoneys()).toDouble();
                }
            }

            news.setInMoney(in);

            news.setOutMoney(out);

            // ��ý���
            Mathematica math = new Mathematica(in);

            news.setTatolMoney(math.subtract(out).toDouble());

            news.setBank(bank);

            news.setStatId(statId);

            news.setLogDate(TimeTools.now_short());

            BankBean bb = bankDAO.findBankByName(bank);

            if (bb != null)
            {
                news.setLocationId(bb.getLocationId());
            }

            statDAO.addStatBank(news);

            logger.info("ÿ��ͳ������:" + bank + "." + statId);
        }
        catch (Exception e)
        {
            _logger.error(e, e);
        }

        return news;
    }

    /**
     * ͳ��
     */
    public void statMoney()
    {
        // ����ϸ����Ƿ�ͳ��
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.MONTH, -1);

        String lastId = TimeTools.getStringByFormat(new Date(cal.getTimeInMillis()), "yyyyMM");

        cal.add(Calendar.MONTH, -1);

        String llastId = TimeTools.getStringByFormat(new Date(cal.getTimeInMillis()), "yyyyMM");

        try
        {
            List<BankBean> bankList = bankDAO.listEntityBeans();

            // �����Ѿ�ͳ���Ǿͷ���(��������ͳ�����)
            int hasCount = statDAO.countByStatId(lastId, true);

            if (hasCount == bankList.size())
            {
                logger.info(lastId + "�Ѿ�ͳ��!!!");

                return;
            }

            // ����ͳ�ƣ���������ͳ��
            logger.info(lastId + "δ��ȫͳ��:" + hasCount + ";�ܼ�:" + bankList.size());

            ConditionParse condtion = new ConditionParse();

            String begin = lastId + "01";

            Calendar _cal = Calendar.getInstance();

            _cal.add(Calendar.MONTH, -1);

            // ȡ�ý���ʱ��
            String end = lastId + _cal.getActualMaximum(Calendar.DAY_OF_MONTH);

            // ͳ��
            List<Bill> billList = null;
            for (BankBean temp : bankList)
            {
                _logger.info("ͳ��:" + temp.getName() + '[' + lastId + ']');

                condtion.addWhereStr();
                condtion.addCommonCondition("dates", ">=", publicSQL.to_date(getF(begin),
                    "yyyy-MM-dd"));
                condtion.addCommonCondition("dates", "<=", publicSQL.to_date(getF(end),
                    "yyyy-MM-dd"));
                condtion.addCondition("bank", "=", temp.getId());

                billList = billDAO.queryBillByCondition(condtion);

                // ͳ������
                statBank(billList, lastId, llastId, temp.getName(), true);

                // �����ѯ����
                condtion.clear();
            }

            List<StatBean> oo = statDAO.queryStatByCondition(new ConditionParse());

            for (StatBean statBean : oo)
            {
                System.out.println(statBean);
            }
        }
        catch (Exception e)
        {
            _logger.error(e, e);
        }
    }

    private String getF(String src)
    {
        return src.substring(0, 4) + '-' + src.substring(4, 6) + '-' + src.substring(6, 8);
    }

    // 200708
    public StatBean statBank(List<Bill> billList, String statId, String llastId, String bank,
                             boolean isFlag)
    {
        StatBean news = new StatBean();
        try
        {
            StatBean old = statDAO.findStatBeanByBank(llastId, bank.trim());

            // ������µ�ͳ�ƽ���
            if (old == null)
            {
                old = new StatBean();
            }

            // ͳ�Ʊ��µ�
            double in = 0.0d;
            double out = 0.0d;
            for (Bill bill : billList)
            {
                if (bill.getType() == Constant.BILL_RECIVE)
                {
                    Mathematica math = new Mathematica(in);

                    in = math.add(bill.getMoneys()).toDouble();
                }

                if (bill.getType() == Constant.BILL_PAY)
                {
                    Mathematica math = new Mathematica(out);

                    out = math.add(bill.getMoneys()).toDouble();
                }
            }

            news.setLastMoney(old.getTatolMoney());

            news.setInMoney(in);
            news.setOutMoney(out);

            // ��ý���
            Mathematica math = new Mathematica(old.getTatolMoney());

            news.setTatolMoney(math.add(in).subtract(out).toDouble());

            news.setBank(bank);

            news.setStatId(statId);

            news.setFlag(true);

            news.setLogDate(TimeTools.now_short());

            if (isFlag)
            {
                // ɾ��ͳ�ƣ�Ȼ������(��ֹ�ظ�)
                statDAO.deleteStatBean(statId, bank);

                statDAO.addStat(news);
            }
        }
        catch (Exception e)
        {
            _logger.error(e, e);
        }

        return news;
    }

    /**
     * ͬ����Ʒ����Ʒ������
     */
    public int synchronizedProduct()
    {

        int count = 0;
        List<LocationBean> locationList = locationDAO.listLocation();

        ProductAmount productAmount = null;

        for (LocationBean locationBean : locationList)
        {
            try
            {
                Thread.sleep(500);
            }
            catch (InterruptedException e1)
            {}

            // ��ѯ������û��ͬ���Ĳ�Ʒ
            List<Product> productList = productDAO.queryNotSynchronizedProductByLocationId(locationBean.getId());

            for (Product product : productList)
            {
                try
                {
                    productAmount = productDAO.findProductAmount(product.getId(),
                        locationBean.getId());

                    if (productAmount == null)
                    {
                        ProductAmount amonut = new ProductAmount();

                        amonut.setLocationId(locationBean.getId());

                        amonut.setProductName(product.getName());

                        amonut.setProductId(product.getId());

                        amonut.setNum(0);

                        productDAO.addProductAmount(amonut);

                        count++ ;

                        logger.info("������" + product.getName() + "��������ϵ!"
                                    + locationBean.getLocationName());
                    }
                }
                catch (Exception e)
                {
                    _logger.error(e, e);
                }
            }
        }

        return count;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.centet.yongyin.trigger.Trigger#modifyProductAmountInSystemLocation()
     */
    public void modifyProductAmountInSystemLocation()
    {
        List<String> productList = storageDAO.listProductIdListInOKDepotpart();

        for (final String productId : productList)
        {
            int yuSum = 0;

            Integer sum = getMay(productId);

            if (sum != null)
            {
                yuSum = sum;
            }

            // ��ʵ��
            int realAmount = storageDAO.sumProcutInOKDepotpart(productId);

            ProductAmount amount = productDAO.findProductAmount(productId,
                Constant.SYSTEM_LOCATION);

            if (amount == null)
            {
                logger.error(productId + "������(��ƷID)");

                continue;
            }

            Product product = productDAO.findProductById(productId);

            if (product == null)
            {
                logger.error(productId + "������(��ƷID)");

                continue;
            }

            int maySum = amount.getNum();

            final int realMaySum = realAmount - yuSum;

            if ( (realMaySum != maySum) && realMaySum > 0)
            {
                // �������
                synchronized (LockConstant.CENTER_PRODUCT_AMOUNT_LOCK)
                {
                    // ִ���������
                    if (doModify(productId, realMaySum))
                    {
                        addLog(product, maySum, realMaySum);
                    }
                }
            }
        }
    }

    /**
     * addLog
     * 
     * @param product
     * @param maySum
     * @param realMaySum
     */
    private void addLog(Product product, int maySum, final int realMaySum)
    {
        // ��¼�ı���־
        LogBean logBean = new LogBean();

        logBean.setOprType(0);

        logBean.setOutId("YY000000000000000");

        logBean.setType(OutConstanst.OUT_TYPE_MODIFY);

        logBean.setOutType(OutConstanst.OUT_TYPE_MODIFY);

        logBean.setProductName(product.getName());

        logBean.setCurrent(realMaySum - maySum);

        logBean.setValue(0.0d);

        logBean.setStaffer("SYSTEM");

        logBean.setRefId("");

        logBean.setBeforCount(maySum);

        logBean.setAfterCount(realMaySum);

        logBean.setLocationId(Constant.SYSTEM_LOCATION);

        logBean.setLog("ϵͳ�Զ��������:" + logBean.getCurrent());

        System.out.println(logBean);

        monitorLog.info(logBean);
    }

    /**
     * doModify
     * 
     * @param productId
     * @param realMaySum
     */
    private boolean doModify(final String productId, final int realMaySum)
    {
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    productDAO.modifyTatol(productId, realMaySum, Constant.SYSTEM_LOCATION);

                    return Boolean.TRUE;
                }
            });
        }
        catch (Exception e)
        {
            _logger.error(e, e);

            return false;
        }

        return true;
    }

    /**
     * getMay
     * 
     * @param productId
     * @return
     */
    private Integer getMay(String productId)
    {
        Map map = new HashMap();

        map.put("type", Constant.OUT_TYPE_OUTBILL);

        map.put("location", Constant.SYSTEM_LOCATION);

        map.put("productId", productId);

        map.put("beginDate", TimeTools.getDateString( -120, "yyyy-MM-dd"));

        map.put("endDate", TimeTools.now_short());

        // ��ѯԤ��ռ�õ�
        Integer sum = outDAO.sumPreassignAmount(map);
        return sum;
    }

    /**
     * @return the statDAO
     */
    public StatDAO getStatDAO()
    {
        return statDAO;
    }

    /**
     * @param statDAO
     *            the statDAO to set
     */
    public void setStatDAO(StatDAO statDAO)
    {
        this.statDAO = statDAO;
    }

    /**
     * @return the commonDAO
     */
    public CommonDAO getCommonDAO()
    {
        return commonDAO;
    }

    /**
     * @param commonDAO
     *            the commonDAO to set
     */
    public void setCommonDAO(CommonDAO commonDAO)
    {
        this.commonDAO = commonDAO;
    }

    /**
     * @return the billDAO
     */
    public BillDAO getBillDAO()
    {
        return billDAO;
    }

    /**
     * @param billDAO
     *            the billDAO to set
     */
    public void setBillDAO(BillDAO billDAO)
    {
        this.billDAO = billDAO;
    }

    /**
     * @return the productDAO
     */
    public ProductDAO getProductDAO()
    {
        return productDAO;
    }

    /**
     * @param productDAO
     *            the productDAO to set
     */
    public void setProductDAO(ProductDAO productDAO)
    {
        this.productDAO = productDAO;
    }

    /**
     * @return the outDAO
     */
    public OutDAO getOutDAO()
    {
        return outDAO;
    }

    /**
     * @param outDAO
     *            the outDAO to set
     */
    public void setOutDAO(OutDAO outDAO)
    {
        this.outDAO = outDAO;
    }

    /**
     * @return the customerDAO
     */
    public CustomerDAO getCustomerDAO()
    {
        return customerDAO;
    }

    /**
     * @param customerDAO
     *            the customerDAO to set
     */
    public void setCustomerDAO(CustomerDAO customerDAO)
    {
        this.customerDAO = customerDAO;
    }

    /**
     * @return the publicSQL
     */
    public PublicSQL getPublicSQL()
    {
        return publicSQL;
    }

    /**
     * @param publicSQL
     *            the publicSQL to set
     */
    public void setPublicSQL(PublicSQL publicSQL)
    {
        this.publicSQL = publicSQL;
    }

    /**
     * @return the bankDAO
     */
    public BankDAO getBankDAO()
    {
        return bankDAO;
    }

    /**
     * @param bankDAO
     *            the bankDAO to set
     */
    public void setBankDAO(BankDAO bankDAO)
    {
        this.bankDAO = bankDAO;
    }

    /**
     * @return the locationDAO
     */
    public LocationDAO getLocationDAO()
    {
        return locationDAO;
    }

    /**
     * @param locationDAO
     *            the locationDAO to set
     */
    public void setLocationDAO(LocationDAO locationDAO)
    {
        this.locationDAO = locationDAO;
    }

    public void printCacheEfficiency()
    {
        Set<Class> set = cacheBootstrap.cacheKeySet();

        for (Class claz : set)
        {
            effLogger.info(claz.getName() + "[MORE]," + cacheBootstrap.moreCacheEfficiency(claz));

            effLogger.info(claz.getName() + "[SINGLE],"
                           + cacheBootstrap.singleCacheEfficiency(claz));
        }
    }

    /**
     * @return the cacheBootstrap
     */
    public CacheBootstrap getCacheBootstrap()
    {
        return cacheBootstrap;
    }

    /**
     * @param cacheBootstrap
     *            the cacheBootstrap to set
     */
    public void setCacheBootstrap(CacheBootstrap cacheBootstrap)
    {
        this.cacheBootstrap = cacheBootstrap;
    }

    /**
     * @return the storageDAO
     */
    public StorageDAO getStorageDAO()
    {
        return storageDAO;
    }

    /**
     * @param storageDAO
     *            the storageDAO to set
     */
    public void setStorageDAO(StorageDAO storageDAO)
    {
        this.storageDAO = storageDAO;
    }

    /**
     * @return the transactionManager
     */
    public DataSourceTransactionManager getTransactionManager()
    {
        return transactionManager;
    }

    /**
     * @param transactionManager
     *            the transactionManager to set
     */
    public void setTransactionManager(DataSourceTransactionManager transactionManager)
    {
        this.transactionManager = transactionManager;
    }
}
