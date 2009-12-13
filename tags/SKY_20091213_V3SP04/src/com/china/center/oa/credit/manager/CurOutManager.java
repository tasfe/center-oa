/**
 * File Name: CurOutManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2009-12-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.manager;


import java.util.List;

import net.sourceforge.sannotations.annotation.Bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.china.center.common.MYException;
import com.china.center.oa.constant.CreditConstant;
import com.china.center.oa.credit.bean.CreditCoreBean;
import com.china.center.oa.credit.bean.CreditItemSecBean;
import com.china.center.oa.credit.bean.CreditItemThrBean;
import com.china.center.oa.credit.bean.CurOutBean;
import com.china.center.oa.credit.dao.CreditCoreDAO;
import com.china.center.oa.credit.dao.CreditItemSecDAO;
import com.china.center.oa.credit.dao.CreditItemThrDAO;
import com.china.center.oa.credit.dao.CreditlogDAO;
import com.china.center.oa.credit.dao.CurOutDAO;
import com.china.center.oa.credit.dao.CustomerCreditDAO;
import com.china.center.oa.credit.dao.OutStatDAO;
import com.china.center.oa.credit.vs.CustomerCreditBean;
import com.china.center.oa.customer.dao.CustomerDAO;
import com.china.center.oa.publics.User;
import com.china.center.oa.publics.helper.UserHelper;
import com.china.center.oa.tools.OATools;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.centet.yongyin.bean.OutBean;


/**
 * CurOutManager
 * 
 * @author ZHUZHU
 * @version 2009-12-2
 * @see CurOutManager
 * @since 1.0
 */
@Bean(name = "curOutManager")
public class CurOutManager
{
    private final Log _logger = LogFactory.getLog(getClass());

    private final Log _coreLog = LogFactory.getLog("core");

    private final Log triggerLog = LogFactory.getLog("trigger");

    private CurOutDAO curOutDAO = null;

    private CreditCoreDAO creditCoreDAO = null;

    private OutStatDAO outStatDAO = null;

    private CreditlogDAO creditlogDAO = null;

    private CustomerDAO customerDAO = null;

    private CustomerCreditDAO customerCreditDAO = null;

    private CreditItemSecDAO creditItemSecDAO = null;

    private CreditItemThrDAO creditItemThrDAO = null;

    private CustomerCreditManager customerCreditManager = null;

    private DataSourceTransactionManager transactionManager = null;

    /**
     * default constructor
     */
    public CurOutManager()
    {}

    /**
     * �ͻ�ͳ��
     */
    public void statOut()
    {
        triggerLog.info("ͳ�ƿͻ����ÿ�ʼ.....");

        List<String> customerIdList = outStatDAO.listCustomerIdList();

        User user = UserHelper.getSystemUser();

        CreditItemSecBean outItem = creditItemSecDAO.find(CreditConstant.OUT_COMMON_ITEM);

        CreditItemSecBean outDelayItem = creditItemSecDAO.find(CreditConstant.OUT_DELAY_ITEM);

        CreditItemThrBean maxDelayThrItem = creditItemThrDAO.findMaxDelayItem();

        if (outItem == null || outDelayItem == null || maxDelayThrItem == null)
        {
            _coreLog.error("miss CreditItemSecBean:" + CreditConstant.OUT_COMMON_ITEM + ".or:"
                           + CreditConstant.OUT_DELAY_ITEM + ".or:maxDelayThrItem");

            triggerLog.info("ͳ�ƿͻ����ý���.....");

            return;
        }

        CreditItemThrBean maxBusiness = creditItemThrDAO.findMaxBusiness();

        CreditItemThrBean totalBusiness = creditItemThrDAO.findTotalBusiness();

        // iterator to handle
        for (String cid : customerIdList)
        {
            try
            {
                handleEachCustomer(user, outItem, maxDelayThrItem, cid, maxBusiness, totalBusiness);
            }
            catch (Throwable e)
            {
                _logger.error(e, e);
            }
        }

        triggerLog.info("ͳ�ƿͻ����ý���.....");
    }

    /**
     * ����ÿ���ͻ�
     * 
     * @param user
     * @param outItem
     * @param maxDelayThrItem
     * @param customerBean
     */
    private void handleEachCustomer(User user, CreditItemSecBean outItem,
                                    CreditItemThrBean maxDelayThrItem, String cid,
                                    CreditItemThrBean maxBusiness, CreditItemThrBean totalBusiness)
    {
        // ��2009-12-01��ʼ�����ͻ�����Ϊ
        List<OutBean> outList = outStatDAO.queryNoneStatByCid(cid);

        // ��ʼ����
        for (OutBean outBean : outList)
        {
            // ������û������
            if (outBean.getPay() == CreditConstant.PAY_YES && outBean.getTempType() == 0)
            {
                handleCommon(user, cid, outItem, outBean);
            }

            // ���ڵ�
            if (outBean.getPay() == CreditConstant.PAY_YES && outBean.getTempType() > 0)
            {
                // ���� ������־(����Ѿ�����)
                handleDelay(user, cid, outBean, maxDelayThrItem, true);
            }

            // ��δ����ģ������Ƿ��Ѿ�����
            if (outBean.getPay() == CreditConstant.PAY_NOT)
            {
                int delay = TimeTools.cdate(TimeTools.now_short(), outBean.getRedate());

                // �ȴ�����
                if (delay <= 0)
                {
                    return;
                }

                // ��������
                handleDelay(user, cid, outBean, maxDelayThrItem, false);
            }

        }

        // ��������׶�
        if (maxBusiness != null)
        {
            double maxBusinessAmount = outStatDAO.queryMaxBusiness(cid,
                OATools.getFinanceBeginDate(), OATools.getFinanceEndDate());

            CreditCoreBean old = creditCoreDAO.findByUnique(cid);

            // ���������ʷ����,������Ϊ��׼
            if (old != null)
            {
                maxBusinessAmount = Math.max(old.getOldMaxBusiness(), maxBusinessAmount);
            }

            CreditItemThrBean sigleItem = creditItemThrDAO.findSingleMaxBusinessByValue(maxBusinessAmount);

            handleSingle(user, cid, sigleItem, maxBusinessAmount);
        }

        // �ܽ��׶�
        if (totalBusiness != null)
        {
            // ���ﵽ���µĲ�����Ⱦͻ����¼�����
            double sumBusiness = outStatDAO.sumBusiness(cid, OATools.getFinanceBeginDate(),
                OATools.getFinanceEndDate());

            CreditItemThrBean sumItem = creditItemThrDAO.findTotalBusinessByValue(sumBusiness);

            handleTotal(user, cid, sumItem, sumBusiness);
        }
    }

    /**
     * handleCommon
     * 
     * @param user
     * @param customerBean
     * @param cid
     * @param outItem
     */
    private void handleCommon(final User user, final String cid, final CreditItemSecBean outItem,
                              final OutBean outBean)
    {
        CustomerCreditBean customerCreditBean = customerCreditDAO.findByUnique(cid,
            CreditConstant.OUT_COMMON_ITEM);

        // �ӷ� ������־(����Ѿ����� reserve1=1)
        if (customerCreditBean == null)
        {
            customerCreditBean = new CustomerCreditBean();

            customerCreditBean.setVal(outItem.getPer());

            customerCreditBean.setLog("���۵�[" + outBean.getFullId() + "]��������,�ӷ�:"
                                      + MathTools.formatNum(outItem.getPer()) + ".�ӷֺ�:"
                                      + MathTools.formatNum(outItem.getPer()));
        }
        else
        {
            customerCreditBean.setLog("���۵�["
                                      + outBean.getFullId()
                                      + "]��������,�ӷ�:"
                                      + MathTools.formatNum(outItem.getPer())
                                      + ".�ӷֺ�:"
                                      + MathTools.formatNum(customerCreditBean.getVal()
                                                            + outItem.getPer()));

            customerCreditBean.setVal(outItem.getPer() + customerCreditBean.getVal());
        }

        customerCreditBean.setCid(cid);

        customerCreditBean.setLogTime(TimeTools.now());

        customerCreditBean.setPtype(CreditConstant.CREDIT_TYPE_DYNAMIC);

        customerCreditBean.setItemId(CreditConstant.OUT_COMMON_ITEM);

        customerCreditBean.setPitemId(CreditConstant.OUT_COMMON_ITEM_PARENT);

        customerCreditBean.setValueId("0");

        final CustomerCreditBean fcustomerCreditBean = customerCreditBean;

        // ���������ݿ����������
        TransactionTemplate tranTemplate = new TransactionTemplate(transactionManager);

        try
        {
            // must send each item in Transaction(may be wait)
            tranTemplate.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    try
                    {
                        customerCreditManager.interposeCreditInner(user, cid, fcustomerCreditBean);
                    }
                    catch (MYException e)
                    {
                        _logger.warn(e, e);
                        throw new RuntimeException(e.getErrorContent());
                    }

                    // ����out�����״̬
                    outStatDAO.updateReserve1ByFullId(outBean.getFullId(),
                        CreditConstant.CREDIT_OUT_END,
                        MathTools.formatNum(fcustomerCreditBean.getVal()));

                    double minus = fcustomerCreditBean.getVal();

                    saveCurLog(cid, outBean, minus);

                    triggerLog.info("handle out stat[" + cid + "]:" + outBean.getFullId());

                    return Boolean.TRUE;
                }
            });
        }
        catch (Throwable e)
        {
            _logger.error(e, e);
        }
    }

    /**
     * handleSingle
     * 
     * @param user
     * @param cid
     * @param sigleItem
     * @param maxBusinessAmount
     */
    private void handleSingle(final User user, final String cid,
                              final CreditItemThrBean sigleItem, final double maxBusinessAmount)
    {
        CustomerCreditBean customerCreditBean = customerCreditDAO.findByUnique(cid,
            CreditConstant.OUT_MAX_BUSINESS);

        // ��������׶�
        if (customerCreditBean == null)
        {
            customerCreditBean = new CustomerCreditBean();

            customerCreditBean.setVal(sigleItem.getPer());
        }

        customerCreditBean.setLog("��������׶�[" + MathTools.formatNum(maxBusinessAmount) + "],�ӷ�:"
                                  + MathTools.formatNum(sigleItem.getPer()));

        customerCreditBean.setCid(cid);

        customerCreditBean.setLogTime(TimeTools.now());

        customerCreditBean.setPtype(CreditConstant.CREDIT_TYPE_DYNAMIC);

        customerCreditBean.setItemId(CreditConstant.OUT_MAX_BUSINESS);

        customerCreditBean.setPitemId(CreditConstant.OUT_MAX_BUSINESS_PARENT);

        customerCreditBean.setValueId(sigleItem.getId());

        final CustomerCreditBean fcustomerCreditBean = customerCreditBean;

        // ���������ݿ����������
        TransactionTemplate tranTemplate = new TransactionTemplate(transactionManager);

        try
        {
            // must send each item in Transaction(may be wait)
            tranTemplate.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    try
                    {
                        customerCreditManager.interposeCreditInner(user, cid, fcustomerCreditBean);
                    }
                    catch (MYException e)
                    {
                        _logger.warn(e, e);
                        throw new RuntimeException(e.getErrorContent());
                    }

                    triggerLog.info("handle single max stat[" + cid + "]:" + maxBusinessAmount);

                    // ������־
                    saveCore(cid, maxBusinessAmount, true);

                    return Boolean.TRUE;
                }
            });
        }
        catch (Throwable e)
        {
            _logger.error(e, e);
        }
    }

    /**
     * handleSingle
     * 
     * @param user
     * @param cid
     * @param sumItem
     * @param amount
     */
    private void handleTotal(final User user, final String cid, final CreditItemThrBean sumItem,
                             final double amount)
    {
        CustomerCreditBean customerCreditBean = customerCreditDAO.findByUnique(cid,
            CreditConstant.OUT_TOTAL_BUSINESS);

        // ��������׶�
        if (customerCreditBean == null)
        {
            customerCreditBean = new CustomerCreditBean();

            customerCreditBean.setVal(sumItem.getPer());
        }

        customerCreditBean.setLog("��������ܽ��׶�[" + MathTools.formatNum(amount) + "],�ӷ�:"
                                  + MathTools.formatNum(sumItem.getPer()));

        customerCreditBean.setCid(cid);

        customerCreditBean.setLogTime(TimeTools.now());

        customerCreditBean.setPtype(CreditConstant.CREDIT_TYPE_DYNAMIC);

        customerCreditBean.setItemId(CreditConstant.OUT_TOTAL_BUSINESS);

        customerCreditBean.setPitemId(CreditConstant.OUT_TOTAL_BUSINESS_PARENT);

        customerCreditBean.setValueId(sumItem.getId());

        final CustomerCreditBean fcustomerCreditBean = customerCreditBean;

        // ���������ݿ����������
        TransactionTemplate tranTemplate = new TransactionTemplate(transactionManager);

        try
        {
            // must send each item in Transaction(may be wait)
            tranTemplate.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    try
                    {
                        customerCreditManager.interposeCreditInner(user, cid, fcustomerCreditBean);
                    }
                    catch (MYException e)
                    {
                        _logger.warn(e, e);
                        throw new RuntimeException(e.getErrorContent());
                    }

                    triggerLog.info("handle sum Total stat[" + cid + "]:" + amount);

                    // ������־
                    saveCore(cid, amount, false);

                    return Boolean.TRUE;
                }
            });
        }
        catch (Throwable e)
        {
            _logger.error(e, e);
        }
    }

    /**
     * �������ڵ����۵�
     * 
     * @param user
     * @param customerBean
     * @param cid
     * @param outItem
     * @param outBean
     */
    private void handleDelay(final User user, final String cid, final OutBean outBean,
                             final CreditItemThrBean maxDelayThrItem, boolean isEnd)
    {
        CustomerCreditBean customerCreditBean = customerCreditDAO.findByUnique(cid,
            CreditConstant.OUT_DELAY_ITEM);

        if ( !isEnd)
        {
            int delay = TimeTools.cdate(TimeTools.now_short(), outBean.getRedate());

            outBean.setTempType(delay);
        }

        final CreditItemThrBean delayItemThrBean = creditItemThrDAO.findDelayItemByDays(outBean.getTempType());

        // ��������ʱ��
        if ( !isEnd)
        {
            // ��������ϴεĸ��¾ͷ���(��ֹ�ظ��۳�)
            if (outBean.getReserve5().equals(delayItemThrBean.getId()))
            {
                return;
            }
        }

        // ������������Ѿ��������������������ȫ������
        if (outBean.getTempType() >= maxDelayThrItem.getIndexPos())
        {
            isEnd = true;
        }

        // ���� ������־
        final double currentMinus = handleCurrentMinus(outBean, delayItemThrBean);

        if (customerCreditBean == null)
        {
            customerCreditBean = new CustomerCreditBean();

            // ����ָ��
            customerCreditBean.setVal( -currentMinus);

            customerCreditBean.setLog("���۵�[" + outBean.getFullId() + "]����["
                                      + outBean.getTempType() + "��]����,����:"
                                      + MathTools.formatNum(currentMinus) + ".���ֺ�:"
                                      + MathTools.formatNum( -currentMinus));
        }
        else
        {
            customerCreditBean.setLog("���۵�["
                                      + outBean.getFullId()
                                      + "]����["
                                      + outBean.getTempType()
                                      + "��]����,����:"
                                      + MathTools.formatNum(currentMinus)
                                      + ".���ֺ�:"
                                      + MathTools.formatNum( (customerCreditBean.getVal() - currentMinus)));

            customerCreditBean.setVal(customerCreditBean.getVal() - currentMinus);
        }

        customerCreditBean.setCid(cid);

        customerCreditBean.setLogTime(TimeTools.now());

        customerCreditBean.setPtype(CreditConstant.CREDIT_TYPE_DYNAMIC);

        customerCreditBean.setItemId(CreditConstant.OUT_DELAY_ITEM);

        customerCreditBean.setPitemId(CreditConstant.OUT_DELAY_ITEM_PARENT);

        customerCreditBean.setValueId(delayItemThrBean.getId());

        final CustomerCreditBean fcustomerCreditBean = customerCreditBean;

        final boolean fisEnd = isEnd;

        // ���������ݿ����������
        TransactionTemplate tranTemplate = new TransactionTemplate(transactionManager);

        try
        {
            // must send each item in Transaction(may be wait)
            tranTemplate.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    try
                    {
                        customerCreditManager.interposeCreditInner(user, cid, fcustomerCreditBean);
                    }
                    catch (MYException e)
                    {
                        _logger.warn(e, e);

                        throw new RuntimeException(e.getErrorContent());
                    }

                    // ����out�����״̬
                    if (fisEnd)
                    {
                        outStatDAO.updateReserve1ByFullId(outBean.getFullId(),
                            CreditConstant.CREDIT_OUT_END,
                            MathTools.formatNum(fcustomerCreditBean.getVal()));
                    }
                    else
                    {
                        // ���µ�ǰ�Ѿ����ڼ���
                        outStatDAO.updateReserve5ByFullId(outBean.getFullId(),
                            delayItemThrBean.getId());
                    }

                    saveCurLog(cid, outBean, fcustomerCreditBean.getVal());

                    triggerLog.info("handle out stat[" + cid + "]:" + outBean.getFullId());

                    return Boolean.TRUE;
                }
            });
        }
        catch (Throwable e)
        {
            _logger.error(e, e);
        }
    }

    /**
     * handleCurrentMinus
     * 
     * @param outBean
     * @param delayItemThrBean
     * @return
     */
    private double handleCurrentMinus(final OutBean outBean,
                                      final CreditItemThrBean delayItemThrBean)
    {
        boolean isNearestDelay = false;

        OutBean nearestById = outStatDAO.findNearestById(outBean.getId(), outBean.getCustomerId());

        if (nearestById != null && nearestById.getTempType() > 0)
        {
            isNearestDelay = true;
        }

        double currentMinus = delayItemThrBean.getPer();

        // �ϴ��Ѿ��۳���һ������
        if ( !StringTools.isNullOrNone(outBean.getReserve4()))
        {
            // ����������
            double hasMinus = Math.abs(MathTools.parseDouble(outBean.getReserve4()));

            currentMinus = currentMinus - hasMinus;
        }

        if (isNearestDelay)
        {
            // �۳�˫����
            return currentMinus * 2;
        }

        return currentMinus;
    }

    /**
     * @return the curOutDAO
     */
    public CurOutDAO getCurOutDAO()
    {
        return curOutDAO;
    }

    /**
     * @param curOutDAO
     *            the curOutDAO to set
     */
    public void setCurOutDAO(CurOutDAO curOutDAO)
    {
        this.curOutDAO = curOutDAO;
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
     * @return the outStatDAO
     */
    public OutStatDAO getOutStatDAO()
    {
        return outStatDAO;
    }

    /**
     * @param outStatDAO
     *            the outStatDAO to set
     */
    public void setOutStatDAO(OutStatDAO outStatDAO)
    {
        this.outStatDAO = outStatDAO;
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

    /**
     * @return the creditlogDAO
     */
    public CreditlogDAO getCreditlogDAO()
    {
        return creditlogDAO;
    }

    /**
     * @param creditlogDAO
     *            the creditlogDAO to set
     */
    public void setCreditlogDAO(CreditlogDAO creditlogDAO)
    {
        this.creditlogDAO = creditlogDAO;
    }

    /**
     * @return the creditItemSecDAO
     */
    public CreditItemSecDAO getCreditItemSecDAO()
    {
        return creditItemSecDAO;
    }

    /**
     * @param creditItemSecDAO
     *            the creditItemSecDAO to set
     */
    public void setCreditItemSecDAO(CreditItemSecDAO creditItemSecDAO)
    {
        this.creditItemSecDAO = creditItemSecDAO;
    }

    /**
     * @return the customerCreditManager
     */
    public CustomerCreditManager getCustomerCreditManager()
    {
        return customerCreditManager;
    }

    /**
     * @param customerCreditManager
     *            the customerCreditManager to set
     */
    public void setCustomerCreditManager(CustomerCreditManager customerCreditManager)
    {
        this.customerCreditManager = customerCreditManager;
    }

    /**
     * @return the customerCreditDAO
     */
    public CustomerCreditDAO getCustomerCreditDAO()
    {
        return customerCreditDAO;
    }

    /**
     * @param customerCreditDAO
     *            the customerCreditDAO to set
     */
    public void setCustomerCreditDAO(CustomerCreditDAO customerCreditDAO)
    {
        this.customerCreditDAO = customerCreditDAO;
    }

    /**
     * @return the creditItemThrDAO
     */
    public CreditItemThrDAO getCreditItemThrDAO()
    {
        return creditItemThrDAO;
    }

    /**
     * @param creditItemThrDAO
     *            the creditItemThrDAO to set
     */
    public void setCreditItemThrDAO(CreditItemThrDAO creditItemThrDAO)
    {
        this.creditItemThrDAO = creditItemThrDAO;
    }

    /**
     * saveCurLog
     * 
     * @param customerBean
     * @param outBean
     * @param minus
     */
    private void saveCurLog(final String cid, final OutBean outBean, double minus)
    {
        // add log CurOutBean
        CurOutBean log = new CurOutBean();

        log.setCid(cid);

        log.setDelay(outBean.getTempType());

        log.setLogTime(TimeTools.now());

        log.setOutId(outBean.getFullId());

        log.setVal(minus);

        curOutDAO.saveEntityBean(log);
    }

    /**
     * @return the creditCoreDAO
     */
    public CreditCoreDAO getCreditCoreDAO()
    {
        return creditCoreDAO;
    }

    /**
     * @param creditCoreDAO
     *            the creditCoreDAO to set
     */
    public void setCreditCoreDAO(CreditCoreDAO creditCoreDAO)
    {
        this.creditCoreDAO = creditCoreDAO;
    }

    /**
     * saveCore
     * 
     * @param cid
     * @param amount
     * @param isMax
     */
    private void saveCore(final String cid, final double amount, boolean isMax)
    {
        CreditCoreBean core = new CreditCoreBean();

        core.setCid(cid);

        if (isMax)
        {
            core.setMaxBusiness(amount);
        }
        else
        {
            core.setSumTotal(amount);
        }

        core.setYear(TimeTools.getYeay());

        core.setLogTime(TimeTools.now());

        CreditCoreBean old = creditCoreDAO.findByUnique(cid);

        if (old == null)
        {
            creditCoreDAO.saveEntityBean(core);
        }
        else
        {
            core.setId(old.getId());

            if (isMax)
            {
                core.setSumTotal(old.getSumTotal());
            }
            else
            {
                core.setMaxBusiness(old.getMaxBusiness());
            }

            creditCoreDAO.updateEntityBean(core);
        }
    }
}
