/*
 * File Name: LocationManager.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-12-15
 * Grant: open source to everybody
 */
package com.china.centet.yongyin.manager;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.china.center.common.ConditionParse;
import com.china.center.common.MYException;
import com.china.center.eltools.ElTools;
import com.china.center.oa.credit.bean.CreditLevelBean;
import com.china.center.oa.customer.bean.CustomerBean;
import com.china.center.oa.note.bean.ShortMessageConstant;
import com.china.center.oa.note.bean.ShortMessageTaskBean;
import com.china.center.oa.note.dao.ShortMessageTaskDAO;
import com.china.center.oa.note.manager.HandleMessage;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.ParamterMap;
import com.china.center.tools.RandomTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.centet.yongyin.bean.BaseBean;
import com.china.centet.yongyin.bean.BaseUser;
import com.china.centet.yongyin.bean.ConsignBean;
import com.china.centet.yongyin.bean.DepotpartBean;
import com.china.centet.yongyin.bean.FlowLogBean;
import com.china.centet.yongyin.bean.LocationBean;
import com.china.centet.yongyin.bean.LogBean;
import com.china.centet.yongyin.bean.OutBean;
import com.china.centet.yongyin.bean.Product;
import com.china.centet.yongyin.bean.StafferBean2;
import com.china.centet.yongyin.bean.StorageLogBean;
import com.china.centet.yongyin.bean.StorageRelationBean;
import com.china.centet.yongyin.bean.User;
import com.china.centet.yongyin.bean.helper.LocationHelper;
import com.china.centet.yongyin.bean.helper.LogBeanHelper;
import com.china.centet.yongyin.constant.Constant;
import com.china.centet.yongyin.constant.LockConstant;
import com.china.centet.yongyin.constant.OutConstanst;
import com.china.centet.yongyin.constant.SysConfigConstant;
import com.china.centet.yongyin.dao.CommonDAO;
import com.china.centet.yongyin.dao.CommonDAO2;
import com.china.centet.yongyin.dao.ConsignDAO;
import com.china.centet.yongyin.dao.DepotpartDAO;
import com.china.centet.yongyin.dao.LocationDAO;
import com.china.centet.yongyin.dao.OutDAO;
import com.china.centet.yongyin.dao.ParameterDAO;
import com.china.centet.yongyin.dao.ProductDAO;
import com.china.centet.yongyin.dao.StafferDAO2;
import com.china.centet.yongyin.dao.StorageDAO;
import com.china.centet.yongyin.dao.UserDAO;
import com.china.centet.yongyin.ext.dao.CreditLevelDAO;
import com.china.centet.yongyin.ext.dao.CustomerBaseDAO;


/**
 * OutManager
 * 
 * @author ZHUZHU
 * @version 2007-12-15
 * @see
 * @since
 */
public class OutManager
{
    private final Log _logger = LogFactory.getLog(getClass());

    private final Log monitorLog = LogFactory.getLog("bill");

    private final Log importLog = LogFactory.getLog("sec");

    private LocationDAO locationDAO = null;

    private CommonDAO commonDAO = null;

    private CommonDAO2 commonDAO2 = null;

    private ProductDAO productDAO = null;

    private UserDAO userDAO = null;

    private StafferDAO2 stafferDAO2 = null;

    private OutDAO outDAO = null;

    private ConsignDAO consignDAO = null;

    private StorageDAO storageDAO = null;

    private CustomerBaseDAO customerBaseDAO = null;

    private ParameterDAO parameterDAO = null;

    private CreditLevelDAO creditLevelDAO = null;

    private DepotpartDAO depotpartDAO = null;

    private ShortMessageTaskDAO shortMessageTaskDAO = null;

    /**
     * Ĭ�ϲ���
     */
    private String defaultDepotpartId = "";

    private int internal = 300000;

    private DataSourceTransactionManager transactionManager = null;

    /**
     * default constructor
     */
    public OutManager()
    {}

    /**
     * ����(�޸�)
     * 
     * @param locationBean
     * @return String ���۵���ID
     * @throws Exception
     */
    public String addOut(final OutBean outBean, final Map dataMap, final User user)
        throws MYException
    {
        ParamterMap request = new ParamterMap(dataMap);

        String fullId = request.getParameter("fullId");

        if (StringTools.isNullOrNone(fullId))
        {
            // �ȱ���
            String id = getAll(commonDAO.getSquence());

            LocationBean location = locationDAO.findLocation(outBean.getLocationId());

            if (location == null)
            {
                _logger.error("���򲻴���:" + outBean.getLocationId());

                throw new MYException("���򲻴���:" + outBean.getLocationId());
            }

            String flag = location.getLocationCode();

            String time = TimeTools.getStringByFormat(new Date(), "yyMMddHHmm");

            fullId = flag + time + id;

            outBean.setId(id);

            outBean.setFullId(fullId);

            dataMap.put("modify", false);
        }
        else
        {
            dataMap.put("modify", true);
        }

        final String totalss = request.getParameter("totalss");

        outBean.setTotal(MathTools.parseDouble(totalss));

        // ����ⵥ
        outBean.setStatus(Constant.STATUS_SAVE);

        outBean.setInway(Constant.IN_WAY_NO);

        // ���baseList
        final String[] nameList = request.getParameter("nameList").split("~");
        final String[] idsList = request.getParameter("idsList").split("~");
        final String[] unitList = request.getParameter("unitList").split("~");
        final String[] amontList = request.getParameter("amontList").split("~");
        final String[] priceList = request.getParameter("priceList").split("~");
        final String[] totalList = request.getParameter("totalList").split("~");
        final String[] desList = (" " + request.getParameter("desList") + " ").split("~");

        // ��֯BaseBean
        double ttatol = 0.0d;
        for (int i = 0; i < nameList.length; i++ )
        {
            ttatol += (Double.parseDouble(priceList[i]) * Integer.parseInt(amontList[i]));
        }

        outBean.setTotal(ttatol);

        // ���ӹ���Ա���������ݿ����������
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    if ((Boolean)dataMap.get("modify"))
                    {
                        outDAO.delOutById2(outBean.getFullId());

                        outDAO.delOutBaseById2(outBean.getFullId());
                    }

                    // ������ⵥ
                    outDAO.addOut(outBean);

                    if (StringTools.isNullOrNone(outBean.getLocation()))
                    {
                        outBean.setLocation(outBean.getLocationId());
                    }

                    // ��֯BaseBean
                    for (int i = 0; i < nameList.length; i++ )
                    {
                        BaseBean base = new BaseBean();
                        base.setOutId(outBean.getFullId());
                        base.setProductId(idsList[i]);

                        if (StringTools.isNullOrNone(base.getProductId()))
                        {
                            throw new RuntimeException("��ƷIDΪ��,���ݲ��걸");
                        }

                        base.setProductName(nameList[i]);
                        base.setUnit(unitList[i]);
                        base.setPrice(Double.parseDouble(priceList[i]));
                        base.setAmount(Integer.parseInt(amontList[i]));
                        base.setValue(Double.parseDouble(totalList[i]));
                        base.setLocationId(outBean.getLocation());

                        // �ܲ���ʱ����д��λ
                        if (LocationHelper.isSystemLocation(outBean.getLocation()))
                        {
                            StorageRelationBean sbean = storageDAO.findStorageRelationByDepotpartAndProcut(
                                defaultDepotpartId, base.getProductId());

                            if (sbean != null)
                            {
                                base.setStorageId(sbean.getStorageId());
                            }
                        }

                        base.setDescription(desList[i].trim());

                        // ���ӵ�����Ʒ��base��
                        outDAO.addBase(base);
                    }

                    sendSMS(outBean, user);

                    return Boolean.TRUE;
                }
            });
        }
        catch (TransactionException e)
        {
            _logger.error("���ӿⵥ����", e);
            throw new MYException("���ݿ��ڲ�����");
        }
        catch (DataAccessException e)
        {
            _logger.error("���ӿⵥ����", e);
            throw new MYException(e.getCause().toString());
        }
        catch (Exception e)
        {
            _logger.error("���ӿⵥ����", e);
            throw new MYException("ϵͳ��������ϵ����Ա");
        }

        return fullId;
    }

    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public String coloneOutAndSubmitAffair(final OutBean outBean, final User user)
        throws MYException
    {
        return coloneOutAndSubmitWithOutAffair(outBean, user);
    }

    /**
     * ������Ĵ���
     * 
     * @param outBean
     * @param user
     * @return
     * @throws MYException
     */
    public String coloneOutAndSubmitWithOutAffair(OutBean outBean, User user)
        throws MYException
    {
        // �ȱ���
        String id = getAll(commonDAO.getSquence());

        LocationBean location = locationDAO.findLocation(outBean.getLocationId());

        if (location == null)
        {
            _logger.error("���򲻴���:" + outBean.getLocationId());

            throw new MYException("���򲻴���:" + outBean.getLocationId());
        }

        String flag = location.getLocationCode();

        String time = TimeTools.getStringByFormat(new Date(), "yyMMddHHmm");

        final String fullId = flag + time + id;

        outBean.setId(id);

        outBean.setFullId(fullId);

        // ����ⵥ
        outBean.setStatus(Constant.STATUS_SAVE);

        // ������ⵥ
        outDAO.addOut(outBean);

        List<BaseBean> list = outBean.getBaseList();

        for (BaseBean baseBean : list)
        {
            baseBean.setOutId(fullId);
            // ���ӵ�����Ʒ��base��
            outDAO.addBase(baseBean);
        }

        // �ύ
        this.submitWithOutAffair(fullId, user);

        return fullId;
    }

    /**
     * �ύ
     * 
     * @param outBean
     * @param user
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = {MYException.class})
    @Exceptional
    public boolean submit(final String fullId, final User user)
        throws MYException
    {
        return submitWithOutAffair(fullId, user);
    }

    /**
     * ����û���ύ
     * 
     * @param fullId
     * @param user
     * @return
     * @throws MYException
     */
    private synchronized boolean submitWithOutAffair(final String fullId, final User user)
        throws MYException
    {
        final OutBean outBean = outDAO.findOutById(fullId);

        if (outBean == null)
        {
            throw new MYException("���ݴ���,��ȷ�ϲ���");
        }

        int preStatus = outBean.getStatus();

        if (preStatus != 0 && preStatus != 2)
        {
            throw new MYException("�����Ѿ��ύ,�����²���");
        }

        final List<BaseBean> baseList = checkSubmit(fullId, outBean);

        final List<LogBean> logList = new ArrayList<LogBean>();

        if (LocationHelper.isSystemLocation(outBean.getLocation()))
        {
            // NOTE synchronized handle product
            synchronized (LockConstant.CENTER_PRODUCT_AMOUNT_LOCK)
            {
                processBaseList(user, outBean, baseList, logList);
            }
        }
        else
        {
            // ����base�����
            processBaseList(user, outBean, baseList, logList);
        }

        // �޸Ŀⵥ��״̬
        int status = processOutStutus(fullId, user, outBean);

        // ������;
        processOutInWay(fullId, outBean);

        // �������ݿ���־
        addOutLog(fullId, user, outBean, "�ύ", Constant.OPR_OUT_PASS, Constant.STATUS_SUBMIT);

        outBean.setStatus(status);

        sendSMS(outBean, user);

        OutBean newOutBean = outDAO.findOutById(fullId);

        // ��¼��־
        for (LogBean logBean : logList)
        {
            logBean.setPreStatus(preStatus);

            logBean.setAfterStatus(newOutBean.getStatus());

            logBean.setUser(user.getName());

            monitorLog.info(logBean);
        }

        return true;
    }

    /**
     * @param fullId
     * @param outBean
     */
    private void processOutInWay(final String fullId, final OutBean outBean)
    {
        // ��������ĵ��룬��Ҫ����;����ⵥȡ����
        if (outBean.getType() == Constant.OUT_TYPE_INBILL
            && outBean.getOutType() == Constant.INBILL_IN)
        {
            String ofullid = outBean.getRefOutFullId();

            outDAO.updataInWay(ofullid, Constant.IN_WAY_OVER);

            importLog.info(ofullid + "����;״̬�ı����;����");
        }

        // ��������ĵ���
        if (outBean.getType() == Constant.OUT_TYPE_INBILL
            && outBean.getOutType() == Constant.INBILL_OUT)
        {
            outDAO.updataInWay(fullId, Constant.IN_WAY);

            importLog.info(fullId + "����;״̬�ı����;");
        }
    }

    /**
     * @param fullId
     * @param user
     * @param outBean
     * @throws MYException
     */
    private int processOutStutus(final String fullId, final User user, final OutBean outBean)
        throws MYException
    {
        int result = 0;

        if (outBean.getType() == Constant.OUT_TYPE_OUTBILL)
        {
            importLog.info(fullId + ":" + user.getStafferName() + ":" + 1 + ":redrectFrom:"
                           + outBean.getStatus());
            // ���ⵥ
            try
            {
                outDAO.modifyOutStatus2(fullId, 1);

                result = 1;

                // ������Ҫ����ͻ������ý��-�Ƿ����������ľ�������
                boolean outCredit = parameterDAO.getBoolean(SysConfigConstant.OUT_CREDIT);

                CustomerBean cbean = customerBaseDAO.find(outBean.getCustomerId());

                // �����߼�����(�����ǻ����տ�����д��߼�)
                if (outCredit && cbean != null
                    && !StringTools.isNullOrNone(cbean.getCreditLevelId())
                    && outBean.getReserve3() == OutConstanst.OUT_SAIL_TYPE_COMMON)
                {
                    double noPayBusiness = outDAO.sumNoPayBusiness(outBean.getCustomerId(),
                        CommonTools.getFinanceBeginDate(), CommonTools.getFinanceEndDate());

                    // query customer credit
                    CreditLevelBean clevel = creditLevelDAO.find(cbean.getCreditLevelId());

                    if (clevel != null)
                    {
                        // �����˿ͻ����þ�����--�����������ľ�������
                        if (noPayBusiness > clevel.getMoney())
                        {
                            outBean.setReserve6("�ͻ������������:"
                                                + MathTools.formatNum(clevel.getMoney())
                                                + ".��ǰδ������(�����˵�):"
                                                + MathTools.formatNum(noPayBusiness));

                            outDAO.updateOutReserve2(fullId, OutConstanst.OUT_CREDIT_OVER,
                                outBean.getReserve6());

                        }
                    }
                }

                // ʹ��ҵ��Ա�����ö��
                if (outCredit && outBean.getReserve3() == OutConstanst.OUT_SAIL_TYPE_CREDIT)
                {
                    StafferBean2 sb2 = stafferDAO2.find(outBean.getStafferId());

                    if (sb2 == null)
                    {
                        throw new MYException("���ݲ��걸,�����²���");
                    }

                    double noPayBusiness = outDAO.sumNoPayAndAvouchBusinessByStafferId(
                        outBean.getStafferId(), CommonTools.getFinanceBeginDate(),
                        CommonTools.getFinanceEndDate());

                    if (noPayBusiness > sb2.getCredit())
                    {
                        throw new MYException(sb2.getName() + "�����ö���Ѿ���֧,�����²���");
                    }
                }
            }
            catch (Exception e)
            {
                throw new MYException(e.toString());
            }
        }
        else
        {
            // ���ֱ��ͨ��
            importLog.info(fullId + ":" + user.getStafferName() + ":" + 3 + ":redrectFrom:"
                           + outBean.getStatus());

            try
            {
                outDAO.modifyOutStatus2(fullId, 3);

                result = 3;
            }
            catch (Exception e)
            {
                throw new MYException(e.toString());
            }

            outDAO.modifyData(fullId, TimeTools.now("yyyy-MM-dd"));
        }

        return result;
    }

    /**
     * ����base���
     * 
     * @param user
     * @param outBean
     * @param baseList
     * @param logList
     * @throws MYException
     */
    private void processBaseList(final User user, final OutBean outBean,
                                 final List<BaseBean> baseList, final List<LogBean> logList)
        throws MYException
    {
        for (BaseBean element : baseList)
        {
            LogBean bean = LogBeanHelper.getLogBean(outBean, element, Constant.LOG_OPR_SUBMIT,
                user.getStafferName());

            // ������ܲ��ĵ�����Ҫ�Բ�������������������ⵥ
            if (LocationHelper.isSystemLocation(outBean.getLocation())
                && outBean.getType() == Constant.OUT_TYPE_INBILL)
            {
                if (StringTools.isNullOrNone(outBean.getDepotpartId()))
                {
                    throw new MYException("��ָ������");
                }

                StorageRelationBean relation = storageDAO.findStorageRelationByDepotpartAndProcut(
                    outBean.getDepotpartId(), element.getProductId());

                DepotpartBean db = depotpartDAO.findDepotpartById(outBean.getDepotpartId());

                if (db == null)
                {
                    throw new MYException("������ȱ��ָ������");
                }

                Product pro = productDAO.findProductById(element.getProductId());

                if (relation == null)
                {
                    relation = new StorageRelationBean();

                    relation.setDepotpartId(outBean.getDepotpartId());

                    // Ĭ�ϴ�λ
                    relation.setStorageId("0");

                    relation.setProductId(element.getProductId());

                    relation.setLocationId(outBean.getLocation());
                }

                int old = relation.getAmount();

                relation.setAmount(relation.getAmount() + element.getAmount());

                if (relation.getAmount() < 0)
                {
                    throw new MYException("�����²�����Ʒ:" + pro.getName() + ",��������");
                }

                updateRelationInner(outBean.getFullId(), relation, old, element.getAmount(),
                    user.getStafferName(), Constant.OPR_STORAGE_OUTBILLIN);

                // �������ۿ������
                saleProductChange(relation, element.getAmount(), bean);
            }
            else
            {
                // ���ݵ��ӵ�����ֱ�Ӳ������ۿ��(�ֲ������µĵ��ݴӷֲ����Լ������)
                int last = 0;

                int tatol = productDAO.getTotal(element.getProductId(), element.getLocationId());

                if (outBean.getType() == Constant.OUT_TYPE_OUTBILL)
                {
                    last = tatol - element.getAmount();
                }
                else
                {
                    // ���ⵥ��Ҫ���ӿ��
                    last = tatol + element.getAmount();
                }

                bean.setBeforCount(tatol);

                bean.setAfterCount(last);

                bean.setLocationId(element.getLocationId());

                // ��֤����
                if (last < 0)
                {
                    throw new MYException(element.getProductName() + "����������.�ִ�:" + tatol);
                }

                productDAO.modifyTatol(element.getProductId(), last, element.getLocationId());
            }

            logList.add(bean);
        }
    }

    /**
     * ���submit��׼��
     * 
     * @param fullId
     * @param outBean
     * @return
     * @throws MYException
     */
    private List<BaseBean> checkSubmit(final String fullId, final OutBean outBean)
        throws MYException
    {
        if (outBean == null)
        {
            throw new MYException(fullId + " ������");
        }

        if (outBean.getStatus() != Constant.STATUS_SAVE
            && outBean.getStatus() != Constant.STATUS_REJECT)
        {
            throw new MYException(fullId + " ״̬����,�����ύ");
        }

        final List<BaseBean> baseList = outDAO.queryBaseByOutFullId(fullId);

        // �ȼ�����
        for (BaseBean element : baseList)
        {
            int last = 0;

            if (outBean.getType() == Constant.OUT_TYPE_OUTBILL)
            {
                // ���ⵥ��֤��������Ŀ��
                int tatol = productDAO.getTotal(element.getProductId(), outBean.getLocation());

                if (tatol < element.getAmount())
                {
                    throw new MYException(element.getProductName() + "����������.�ִ�:" + tatol);
                }
            }
            else
            {
                // ���ⵥ��Ҫ���ӿ�� ��ⵥ��Ҫ��֤�Լ�����Ŀ��
                int tatol = productDAO.getTotal(element.getProductId(), outBean.getLocationId());

                last = tatol + element.getAmount();

                if (last < 0)
                {
                    throw new MYException(element.getProductName() + "����������.�ִ�:" + tatol);
                }
            }
        }

        // ��������ĵ��룬��Ҫ��ref�ĵ���
        if (outBean.getType() == Constant.OUT_TYPE_INBILL
            && outBean.getOutType() == Constant.INBILL_IN)
        {
            String ofullid = outBean.getRefOutFullId();

            if (StringTools.isNullOrNone(ofullid))
            {
                throw new MYException("�����ǵ���Ŀⵥ��Ҫ�����Ŀⵥ��Ӧ�������²���ѡ������Ŀⵥ");
            }

            OutBean temp = outDAO.findOutById(ofullid);

            if (temp == null)
            {
                throw new MYException("ѡ������Ŀⵥ�����ڣ������²���ѡ������Ŀⵥ");
            }

            if (temp.getInway() != Constant.IN_WAY)
            {
                throw new MYException("ѡ������Ŀⵥ������;�У���ȷ��");
            }
        }

        return baseList;
    }

    /**
     * Description:�������ۿ��ı䶯
     * 
     * @param temp
     * @param bean
     * @since <IVersion>
     */
    private void saleProductChange(StorageRelationBean bean, int change, LogBean log)
    {
        DepotpartBean temp = depotpartDAO.findDepotpartById(bean.getDepotpartId());

        // ��ǰ���ۿ��
        int current = productDAO.getTotal(bean.getProductId(), bean.getLocationId());

        // �������Ʒ�ֵķ����˱䶯����Ҫͬ�����������
        if (temp.getType() == Constant.TYPE_DEPOTPART_OK)
        {
            int count = current + change;

            if (count < 0)
            {
                throw new RuntimeException("���ۿ���Ʒ�������䶯���С��0��ȷ��ʵ�ʿ������ۿ��");
            }

            log.setBeforCount(current);

            log.setLocationId(bean.getLocationId());

            // ͬ�������ۿ������
            productDAO.modifyTatol(bean.getProductId(), count, bean.getLocationId());

            log.setAfterCount(count);
        }
        else
        {
            log.setAfterCount(current);

            log.setBeforCount(current);

            log.setLog("��⵽����Ʒ����");

            log.setLocationId(bean.getLocationId());
        }
    }

    /**
     * ����(ֻ�����۵�����ⵥ)
     * 
     * @param outBean
     * @param user
     * @return
     * @throws Exception
     */
    public synchronized boolean reject(final String fullId, final User user, final String reason)
        throws MYException
    {
        final OutBean outBean = outDAO.findOutById(fullId);

        if (outBean == null)
        {
            throw new MYException("���۵������ڣ������²���");
        }

        final List<BaseBean> baseList = outDAO.queryBaseByOutFullId(fullId);

        final String locationId = outBean.getLocation();

        if (LocationHelper.isSystemLocation(locationId))
        {
            // NOTE synchronized handle product
            synchronized (LockConstant.CENTER_PRODUCT_AMOUNT_LOCK)
            {
                doReject(fullId, user, reason, outBean, baseList, locationId);
            }
        }
        else
        {
            doReject(fullId, user, reason, outBean, baseList, locationId);
        }

        return true;

    }

    /**
     * doReject
     * 
     * @param fullId
     * @param user
     * @param reason
     * @param outBean
     * @param baseList
     * @param locationId
     * @throws MYException
     */
    private void doReject(final String fullId, final User user, final String reason,
                          final OutBean outBean, final List<BaseBean> baseList,
                          final String locationId)
        throws MYException
    {
        checkReject(outBean, baseList, locationId);

        final List<LogBean> logList = new ArrayList<LogBean>();

        // �����������ݿ����������
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    for (BaseBean element : baseList)
                    {
                        LogBean bean = LogBeanHelper.getLogBean(outBean, element,
                            Constant.LOG_OPR_REJECT, user.getStafferName());

                        int last = 0;

                        int tatol = productDAO.getTotal(element.getProductId(), locationId);

                        // ���ⵥ��Ҫ���ӿ��
                        if (outBean.getType() == Constant.OUT_TYPE_OUTBILL)
                        {
                            last = tatol + element.getAmount();
                        }
                        else
                        {
                            // ��ⵥ��������
                            last = tatol - element.getAmount();
                        }

                        bean.setBeforCount(tatol);

                        bean.setAfterCount(last);

                        bean.setLocationId(element.getLocationId());

                        // ��֤����
                        if (last < 0)
                        {
                            throw new RuntimeException(element.getProductName() + "����������.�ִ�:"
                                                       + tatol);
                        }

                        productDAO.modifyTatol(element.getProductId(), last, locationId);

                        // �����ⵥ���ܲ��ĵ��ӱ�������Ҫ���㵽��Ӧ�Ĳ�������(�ҵ����д��ڲ���)
                        if (Constant.SYSTEM_LOCATION.equals(outBean.getLocation())
                            && outBean.getType() == Constant.OUT_TYPE_INBILL)
                        {
                            processRejectIn(fullId, user, outBean, element);
                        }

                        logList.add(bean);
                    }

                    // ������۵�����Ҫɾ��������
                    if (outBean.getType() == Constant.OUT_TYPE_OUTBILL)
                    {
                        ConsignBean beans = consignDAO.findConsignById(fullId);

                        if (beans != null)
                        {
                            consignDAO.delConsign(fullId);
                        }
                    }

                    if (outBean.getType() == Constant.OUT_TYPE_OUTBILL)
                    {
                        importLog.info(fullId + ":" + user.getStafferName() + ":"
                                       + Constant.STATUS_REJECT + ":redrectFrom:"
                                       + outBean.getStatus());
                        outDAO.modifyOutStatus2(outBean.getFullId(), Constant.STATUS_REJECT);
                    }
                    else
                    {
                        importLog.info(fullId + ":" + user.getStafferName() + ":"
                                       + Constant.STATUS_SAVE + ":redrectFrom:"
                                       + outBean.getStatus());

                        outDAO.modifyOutStatus2(outBean.getFullId(), Constant.STATUS_SAVE);
                    }

                    // �����޸���;��ʽ
                    outDAO.updataInWay(fullId, Constant.IN_WAY_NO);

                    addOutLog(fullId, user, outBean, reason, Constant.OPR_OUT_REJECT,
                        Constant.STATUS_REJECT);

                    return Boolean.TRUE;
                }

            });
        }
        catch (TransactionException e)
        {
            _logger.error("���ӿⵥ����", e);
            throw new MYException("���ݿ��ڲ�����");
        }
        catch (DataAccessException e)
        {
            _logger.error("���ӿⵥ����", e);
            throw new MYException(e.getCause().toString());
        }
        catch (Exception e)
        {
            _logger.error("���ӿⵥ����", e);
            throw new MYException("ϵͳ����,�����²���");
        }

        // ��¼��־
        for (LogBean logBean : logList)
        {
            logBean.setUser(user.getName());

            monitorLog.info(logBean);
        }
    }

    /**
     * �����ص���֤
     * 
     * @param outBean
     * @param baseList
     * @param locationId
     * @throws MYException
     */
    private void checkReject(final OutBean outBean, final List<BaseBean> baseList,
                             final String locationId)
        throws MYException
    {
        if (outBean == null)
        {
            throw new MYException("���۵������ڣ������²���");
        }

        boolean outbill = true;
        if (outBean.getType() == Constant.OUT_TYPE_OUTBILL)
        {
            if (outBean.getStatus() == Constant.STATUS_SAVE
                || outBean.getStatus() == Constant.STATUS_REJECT
                || outBean.getStatus() == Constant.STATUS_PASS)
            {
                throw new MYException("״̬�����Բ���!");
            }
        }

        if (outBean.getType() == Constant.OUT_TYPE_INBILL)
        {
            outbill = false;
            // ֻ��ͨ������ⵥ�ſ��Ա�����
            if (outBean.getStatus() != Constant.STATUS_PASS
                || outBean.getType() != Constant.INBILL_OUT)
            {
                throw new MYException("״̬�����Բ���!");
            }
        }

        // �ȼ�����
        for (BaseBean element : baseList)
        {
            // ���۵�����(���ӿ��) ��ⵥ���ٿ��
            int tatol = productDAO.getTotal(element.getProductId(), locationId);

            int after = 0;

            if (outbill)
            {
                after = tatol + element.getAmount();
            }
            else
            {
                after = tatol - element.getAmount();
            }

            if (after < 0)
            {
                throw new MYException(element.getProductName() + "����������.�ִ�:" + tatol);
            }
        }
    }

    /**
     * �����ܲ���ⵥ�Ĳ���
     * 
     * @param fullId
     * @param user
     * @param outBean
     * @param element
     */
    private void processRejectIn(final String fullId, final User user, final OutBean outBean,
                                 BaseBean element)
    {
        if (StringTools.isNullOrNone(outBean.getDepotpartId()))
        {
            throw new RuntimeException(fullId + ":����ⵥ����������Ӧ�ôӲ���������ٲ�Ʒ������������ⵥ��û�в��������ʵ���ݵ�׼ȷ��");
        }

        StorageRelationBean relation = storageDAO.findStorageRelationByDepotpartAndProcut(
            outBean.getDepotpartId(), element.getProductId());

        DepotpartBean db = depotpartDAO.findDepotpartById(outBean.getDepotpartId());

        if (db == null)
        {
            throw new RuntimeException("������ָ������������");
        }

        Product pro = productDAO.findProductById(element.getProductId());

        if (relation == null)
        {
            throw new RuntimeException("�����²���[" + db.getName() + "]ȱ�ٲ�Ʒ:" + pro.getName());
        }

        int old = relation.getAmount();

        relation.setAmount(relation.getAmount() - element.getAmount());

        if (relation.getAmount() < 0)
        {
            throw new RuntimeException("�����²�����Ʒ:" + pro.getName() + ",��������");
        }

        updateRelationInner(outBean.getFullId(), relation, old, element.getAmount(),
            user.getStafferName(), Constant.OPR_STORAGE_OUTBILLIN);
    }

    /**
     * ���ͨ��
     * 
     * @param outBean
     * @param user
     * @return
     * @throws Exception
     */
    public synchronized boolean pass(final String fullId, final User user, final int nextStatus,
                                     final String reason, final String depotpartId)
        throws MYException
    {
        final OutBean outBean = outDAO.findOutById(fullId);

        final int oldStatus = outBean.getStatus();

        if (outBean == null)
        {
            throw new MYException("���۵������ڣ������²���");
        }

        // ���pass������
        if (outBean.getStatus() == Constant.STATUS_SAVE
            || outBean.getStatus() == Constant.STATUS_REJECT)
        {
            throw new MYException("״̬������ͨ��!");
        }

        final String location = outBean.getLocation();

        // ������ܲ�����ͨ���������۵�����Ҫ��������ͨ��
        if (LocationHelper.isSystemLocation(location) && nextStatus == Constant.STATUS_FLOW_PASS
            && outBean.getType() == Constant.OUT_TYPE_OUTBILL)
        {
            ConsignBean beans = consignDAO.findConsignById(fullId);

            if (beans != null)
            {
                if (beans.getCurrentStatus() == Constant.CONSIGN_INIT)
                {
                    throw new MYException("������û������ͨ�������ȴ�������");
                }
            }
        }

        // �����������ݿ����������
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    // �޸Ĳ���
                    if ( !StringTools.isNullOrNone(depotpartId))
                    {
                        outBean.setDepotpartId(depotpartId);

                        outDAO.updateOut(outBean);

                        // �ܲ�����Żᴦ���(���۵�)
                        if (LocationHelper.isSystemLocation(outBean.getLocation())
                            && outBean.getType() == Constant.OUT_TYPE_OUTBILL)
                        {
                            // �޸Ĳ����Ĳ�Ʒ����
                            List<BaseBean> baseList = outDAO.queryBaseByOutFullId(outBean.getFullId());

                            DepotpartBean depotpartBean = depotpartDAO.findDepotpartById(depotpartId);

                            if (depotpartBean == null)
                            {
                                throw new RuntimeException("����������");
                            }

                            if (depotpartBean.getType() != Constant.TYPE_DEPOTPART_OK)
                            {
                                throw new RuntimeException("ֻ�ܴ���Ʒ���г���");
                            }

                            // ѭ�������޸Ĳ�����Ӧ�Ĳ�Ʒ����
                            StorageRelationBean relation = null;
                            Product product = null;
                            for (BaseBean baseBean : baseList)
                            {
                                relation = storageDAO.findStorageRelationByDepotpartAndProcut(
                                    depotpartId, baseBean.getProductId());

                                product = productDAO.findProductById(baseBean.getProductId());

                                if (relation == null)
                                {
                                    throw new RuntimeException("����Ĳ����²�����ָ���Ĳ�Ʒ:"
                                                               + product.getName());
                                }

                                int old = relation.getAmount();

                                relation.setAmount(relation.getAmount() - baseBean.getAmount());

                                if (relation.getAmount() < 0)
                                {
                                    throw new RuntimeException("����Ĳ�����ָ���Ĳ�Ʒ��" + product.getName()
                                                               + "�����������㣬���ʵ");
                                }

                                // ���²����ڲ��Ĳ�Ʒ����
                                updateRelationInner(outBean.getFullId(), relation, old,
                                    -baseBean.getAmount(), user.getStafferName(),
                                    Constant.OPR_STORAGE_OUTBILL);
                            }
                        }

                    }

                    importLog.info(outBean.getFullId() + ":" + user.getStafferName() + ":"
                                   + nextStatus + ":redrectFrom:" + oldStatus);
                    // ���ⵥ
                    try
                    {
                        outDAO.modifyOutStatus2(outBean.getFullId(), nextStatus);
                    }
                    catch (Exception e)
                    {
                        throw new RuntimeException(e.toString());
                    }

                    // ��Ҫ�ѻؿ���־�ö�
                    if (nextStatus == Constant.STATUS_PASS
                        && outBean.getType() == Constant.OUT_TYPE_OUTBILL)
                    {
                        outDAO.modifyData(fullId, TimeTools.now("yyyy-MM-dd"));

                        long add = outBean.getReday() * 24 * 3600 * 1000L;

                        // ������Ҫ�ѳ��ⵥ�Ļؿ������޸�
                        outDAO.modifyReDate2(fullId, TimeTools.getStringByFormat(new Date(
                            new Date().getTime() + add), "yyyy-MM-dd"));
                    }

                    // ���ⵥ�ܾ������ͨ���󣬳��ⵥת����������Ա��ͬʱ�Զ����ɷ�����
                    if (nextStatus == Constant.STATUS_MANAGER_PASS
                        && LocationHelper.isSystemLocation(location))
                    {
                        ConsignBean bean = new ConsignBean();

                        bean.setCurrentStatus(Constant.CONSIGN_INIT);

                        bean.setFullId(outBean.getFullId());

                        consignDAO.addConsign(bean);
                    }

                    addOutLog(fullId, user, outBean, reason, Constant.OPR_OUT_PASS, nextStatus);

                    outBean.setStatus(nextStatus);

                    sendSMS(outBean, user);

                    return Boolean.TRUE;
                }
            });
        }
        catch (TransactionException e)
        {
            _logger.error("ͨ���ⵥ����", e);
            throw new MYException("���ݿ��ڲ�����");
        }
        catch (DataAccessException e)
        {
            _logger.error("ͨ���ⵥ����", e);
            throw new MYException(e.getCause().toString());
        }
        catch (Exception e)
        {
            _logger.error("ͨ���ⵥ����", e);
            throw new MYException("�����쳣:" + e.getMessage());
        }

        return true;

    }

    /**
     * CHECK(���۵����ս�)
     * 
     * @param outBean
     * @param user
     * @return
     * @throws Exception
     */
    public boolean check(final String fullId, final User user, final String checks)
        throws MYException
    {
        final OutBean outBean = outDAO.findOutById(fullId);

        if (outBean == null)
        {
            throw new MYException("���۵������ڣ������²���");
        }

        // �����������ݿ����������
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {

                    outDAO.modifyChecks(fullId, checks);

                    try
                    {
                        outDAO.modifyOutStatus2(fullId, Constant.STATUS_SEC_PASS);
                    }
                    catch (Exception e)
                    {
                        throw new RuntimeException(e.toString());
                    }

                    addOutLog(fullId, user, outBean, "�˶�", Constant.OPR_OUT_PASS,
                        Constant.STATUS_SEC_PASS);

                    return Boolean.TRUE;
                }
            });
        }
        catch (TransactionException e)
        {
            _logger.error("�˶Կⵥ����", e);
            throw new MYException("���ݿ��ڲ�����");
        }
        catch (DataAccessException e)
        {
            _logger.error("�˶Կⵥ����", e);
            throw new MYException(e.getCause().toString());
        }
        catch (Exception e)
        {
            _logger.error("�˶Կⵥ����", e);
            throw new MYException("ϵͳ����,�����²���");
        }

        return true;

    }

    public OutBean findOutById(final String fullId)
    {
        OutBean out = outDAO.findOutById(fullId);

        if (out == null)
        {
            return null;
        }

        out.setBaseList(outDAO.queryBaseByOutFullId(fullId));

        return out;
    }

    /**
     * ɾ���ⵥ
     * 
     * @param fullId
     * @return
     */
    public boolean delOut(final String fullId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(fullId);

        // �����������ݿ����������
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    outDAO.delOutById2(fullId);

                    outDAO.delOutBaseById2(fullId);

                    // ɾ��������¼
                    outDAO.delOutLogByFullId(fullId);

                    return Boolean.TRUE;
                }
            });
        }
        catch (TransactionException e)
        {
            _logger.error("ɾ���ⵥ����", e);
            throw new MYException("���ݿ��ڲ�����");
        }
        catch (DataAccessException e)
        {
            _logger.error("ɾ���ⵥ����", e);
            throw new MYException(e.getCause().toString());
        }
        catch (Exception e)
        {
            _logger.error("ɾ���ⵥ����", e);
            throw new MYException("ϵͳ����,�����²���");
        }

        return true;
    }

    /**
     * ɾ���ⵥ
     * 
     * @param fullId
     * @return
     */
    public boolean updateOut(final OutBean out)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(out);

        JudgeTools.judgeParameterIsNull(out.getFullId());

        // �����������ݿ����������
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    outDAO.updateOut(out);

                    return Boolean.TRUE;
                }
            });
        }
        catch (TransactionException e)
        {
            _logger.error("�޸Ŀⵥ����", e);
            throw new MYException("���ݿ��ڲ�����");
        }
        catch (DataAccessException e)
        {
            _logger.error("�޸Ŀⵥ����", e);
            throw new MYException(e.getCause().toString());
        }
        catch (Exception e)
        {
            _logger.error("�޸Ŀⵥ����", e);
            throw new MYException("ϵͳ����,�����²���");
        }

        return true;
    }

    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public boolean modifyPay(String fullId, int pay)
    {
        // ��Ҫ�����Ƿ��� flowId
        OutBean out = outDAO.findOutById(fullId);

        if (out == null)
        {
            return false;
        }

        // ���getRedateΪ��˵���Ѿ���ǰ�ؿ���
        if ( !StringTools.isNullOrNone(out.getRedate()))
        {
            int delay = TimeTools.cdate(TimeTools.now(), out.getRedate());

            if (delay > 0)
            {
                outDAO.modifyTempType(fullId, delay);
            }
            else
            {
                outDAO.modifyTempType(fullId, 0);
            }
        }

        outDAO.modifyReDate2(fullId, TimeTools.now_short());

        return outDAO.modifyPay2(fullId, pay);
    }

    @Transactional(rollbackFor = {MYException.class})
    @Exceptional
    public boolean mark(String fullId, boolean status)
    {
        return outDAO.mark2(fullId, status);
    }

    @Transactional(rollbackFor = {MYException.class})
    @Exceptional
    public boolean modifyReDate(String fullId, String reDate)
    {
        return outDAO.modifyReDate2(fullId, reDate);
    }

    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public boolean modifyOutHadPay(String fullId, String hadPay)
    {
        return outDAO.modifyOutHadPay2(fullId, hadPay);
    }

    public List<LocationBean> listLocation()
    {
        return locationDAO.listLocation();
    }

    public LocationBean findLocationById(String id)
    {
        return locationDAO.findLocation(id);
    }

    /**
     * @return the locationDAO
     */
    public LocationDAO getLocationDAO()
    {
        return locationDAO;
    }

    /**
     * @return the transactionManager
     */
    public DataSourceTransactionManager getTransactionManager()
    {
        return transactionManager;
    }

    /**
     * @param locationDAO
     *            the locationDAO to set
     */
    public void setLocationDAO(LocationDAO locationDAO)
    {
        this.locationDAO = locationDAO;
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
     * @return the commonDAO
     */
    public CommonDAO getCommonDAO()
    {
        return commonDAO;
    }

    /**
     * @return the productDAO
     */
    public ProductDAO getProductDAO()
    {
        return productDAO;
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
     * @param productDAO
     *            the productDAO to set
     */
    public void setProductDAO(ProductDAO productDAO)
    {
        this.productDAO = productDAO;
    }

    /**
     * @return the userDAO
     */
    public UserDAO getUserDAO()
    {
        return userDAO;
    }

    /**
     * @param userDAO
     *            the userDAO to set
     */
    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
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

    private String getAll(int i)
    {
        String s = "00000000" + i;

        return s.substring(s.length() - 9);
    }

    /**
     * @return the consignDAO
     */
    public ConsignDAO getConsignDAO()
    {
        return consignDAO;
    }

    /**
     * @param consignDAO
     *            the consignDAO to set
     */
    public void setConsignDAO(ConsignDAO consignDAO)
    {
        this.consignDAO = consignDAO;
    }

    /**
     * Description: �ڲ��޸Ĵ�λ�Ĳ�Ʒ����
     * 
     * @param fullId
     * @param bean
     * @param old
     * @param change
     * @since <IVersion>
     */
    private void updateRelationInner(String fullId, StorageRelationBean bean, int old, int change,
                                     String stafferName, int type)
    {
        // �޸Ĺ�ϵ�Ĳ�Ʒ����
        if (StringTools.isNullOrNone(bean.getId()))
        {
            storageDAO.addStorageRelation(bean);
        }
        else
        {
            storageDAO.updateStorageRelation(bean);
        }

        // ��¼�����Ĳ�Ʒ�춯����
        StorageLogBean log = new StorageLogBean();

        BeanUtil.copyProperties(log, bean);

        log.setStorageId(bean.getStorageId());

        log.setType(type);

        log.setPreAmount(old);

        log.setAfterAmount(bean.getAmount());

        log.setChangeAmount(change);

        log.setLogTime(TimeTools.now());

        log.setUser(stafferName);

        log.setDescription(fullId);

        storageDAO.addStorageLog(log);
    }

    /**
     * ���Ͷ���
     * 
     * @param out
     * @param user
     */
    private void sendSMS(OutBean out, User user)
    {
        // 1:���
        if (out.getType() == 1)
        {
            return;
        }

        // 0:���� 1:�ύ 2:���� 3:���� 4:������ͨ�� 6:�ܾ������ͨ��
        if (out.getStatus() == 0 || out.getStatus() == 2 || out.getStatus() == 4)
        {
            return;
        }

        // ���Ͷ��Ÿ������ܾ������
        if (out.getStatus() == 1)
        {
            ConditionParse condtition = new ConditionParse();

            condtition.addCondition("locationId", "=", out.getLocationId());

            condtition.addIntCondition("status", "=", 0);

            condtition.addIntCondition("role", "=", 4);

            queryUserToSendSMS(out, user, condtition, "�ܾ�������");
        }

        // ���Ͷ��Ÿ�������(���ܲ�)
        if (out.getStatus() == 6 && !"0".equals(out.getLocation()))
        {
            ConditionParse condtition = new ConditionParse();

            condtition.addCondition("locationId", "=", out.getLocationId());

            condtition.addIntCondition("status", "=", 0);

            condtition.addIntCondition("role", "=", 1);

            queryUserToSendSMS(out, user, condtition, "���Ա����");
        }

    }

    /**
     * queryUserToSendSMS
     * 
     * @param out
     * @param user
     * @param condtition
     */
    private void queryUserToSendSMS(OutBean out, User user, ConditionParse condtition,
                                    String tokenName)
    {
        List<BaseUser> userList = ListTools.distinct(userDAO.queryEntityBeansBycondition(condtition));

        for (BaseUser baseUser : userList)
        {
            StafferBean2 sb = stafferDAO2.find(baseUser.getStafferId());

            if (sb != null && !StringTools.isNullOrNone(sb.getHandphone()))
            {
                sendSMSInner(out, user, sb, tokenName);
            }
        }
    }

    /**
     * sendSMS
     * 
     * @param out
     * @param user
     * @param sb
     */
    private void sendSMSInner(OutBean out, User user, StafferBean2 sb, String tokenName)
    {
        StafferBean2 realStaffer = stafferDAO2.find(out.getStafferId());

        if (realStaffer == null)
        {
            return;
        }

        // send short message
        ShortMessageTaskBean sms = new ShortMessageTaskBean();

        sms.setId(commonDAO2.getSquenceString20());

        sms.setFk(out.getFullId());

        sms.setType(HandleMessage.TYPE_OUT);

        sms.setHandId(RandomTools.getRandomMumber(4));

        sms.setStatus(ShortMessageConstant.STATUS_INIT);

        sms.setMtype(ShortMessageConstant.MTYPE_ONLY_SEND_RECEIVE);

        sms.setFktoken(String.valueOf(out.getStatus()));

        sms.setMessage(realStaffer.getName() + "�������۵�[" + out.getDescription() + "(�ؿ�����:"
                       + out.getReday() + ";�ܽ��:" + ElTools.formatNum(out.getTotal()) + ")]"
                       + "��������(" + tokenName + ").0ͨ��,1����.�ظ���ʽ[" + sms.getHandId() + ":0]��["
                       + sms.getHandId() + ":1:����]");

        sms.setReceiver(sb.getHandphone());

        sms.setStafferId(sb.getId());

        sms.setLogTime(TimeTools.now());

        // 24 hour
        sms.setEndTime(TimeTools.now(1));

        // internal
        sms.setSendTime(TimeTools.getDateTimeString(this.internal));

        // add sms
        shortMessageTaskDAO.saveEntityBean(sms);
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
     * @return the depotpartDAO
     */
    public DepotpartDAO getDepotpartDAO()
    {
        return depotpartDAO;
    }

    /**
     * @param depotpartDAO
     *            the depotpartDAO to set
     */
    public void setDepotpartDAO(DepotpartDAO depotpartDAO)
    {
        this.depotpartDAO = depotpartDAO;
    }

    /**
     * ������־
     * 
     * @param fullId
     * @param user
     * @param outBean
     */
    private void addOutLog(final String fullId, final User user, final OutBean outBean,
                           String des, int mode, int astatus)
    {
        FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());

        log.setDescription(des);
        log.setFullId(fullId);
        log.setOprMode(mode);
        log.setLogTime(TimeTools.now());

        log.setPreStatus(outBean.getStatus());

        log.setAfterStatus(astatus);

        outDAO.addOutLog(log);
    }

    /**
     * @return the defaultDepotpartId
     */
    public String getDefaultDepotpartId()
    {
        return defaultDepotpartId;
    }

    /**
     * @param defaultDepotpartId
     *            the defaultDepotpartId to set
     */
    public void setDefaultDepotpartId(String defaultDepotpartId)
    {
        this.defaultDepotpartId = defaultDepotpartId;
    }

    /**
     * @return the shortMessageTaskDAO
     */
    public ShortMessageTaskDAO getShortMessageTaskDAO()
    {
        return shortMessageTaskDAO;
    }

    /**
     * @param shortMessageTaskDAO
     *            the shortMessageTaskDAO to set
     */
    public void setShortMessageTaskDAO(ShortMessageTaskDAO shortMessageTaskDAO)
    {
        this.shortMessageTaskDAO = shortMessageTaskDAO;
    }

    /**
     * @return the stafferDAO2
     */
    public StafferDAO2 getStafferDAO2()
    {
        return stafferDAO2;
    }

    /**
     * @param stafferDAO2
     *            the stafferDAO2 to set
     */
    public void setStafferDAO2(StafferDAO2 stafferDAO2)
    {
        this.stafferDAO2 = stafferDAO2;
    }

    /**
     * @return the commonDAO2
     */
    public CommonDAO2 getCommonDAO2()
    {
        return commonDAO2;
    }

    /**
     * @param commonDAO2
     *            the commonDAO2 to set
     */
    public void setCommonDAO2(CommonDAO2 commonDAO2)
    {
        this.commonDAO2 = commonDAO2;
    }

    /**
     * @return the internal
     */
    public int getInternal()
    {
        return internal;
    }

    /**
     * @param internal
     *            the internal to set
     */
    public void setInternal(int internal)
    {
        this.internal = internal;
    }

    /**
     * @return the parameterDAO
     */
    public ParameterDAO getParameterDAO()
    {
        return parameterDAO;
    }

    /**
     * @param parameterDAO
     *            the parameterDAO to set
     */
    public void setParameterDAO(ParameterDAO parameterDAO)
    {
        this.parameterDAO = parameterDAO;
    }

    /**
     * @return the creditLevelDAO
     */
    public CreditLevelDAO getCreditLevelDAO()
    {
        return creditLevelDAO;
    }

    /**
     * @param creditLevelDAO
     *            the creditLevelDAO to set
     */
    public void setCreditLevelDAO(CreditLevelDAO creditLevelDAO)
    {
        this.creditLevelDAO = creditLevelDAO;
    }

    /**
     * @return the customerBaseDAO
     */
    public CustomerBaseDAO getCustomerBaseDAO()
    {
        return customerBaseDAO;
    }

    /**
     * @param customerBaseDAO
     *            the customerBaseDAO to set
     */
    public void setCustomerBaseDAO(CustomerBaseDAO customerBaseDAO)
    {
        this.customerBaseDAO = customerBaseDAO;
    }
}
