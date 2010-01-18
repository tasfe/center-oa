/**
 * File Name: ConsumeManager.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-12-15
 * Grant: open source to everybody
 */
package com.china.centet.yongyin.manager;


import java.util.ArrayList;
import java.util.List;

import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.china.center.cache.bean.MoreHashMap;
import com.china.center.common.MYException;
import com.china.center.eltools.ElTools;
import com.china.center.oa.note.bean.ShortMessageConstant;
import com.china.center.oa.note.bean.ShortMessageTaskBean;
import com.china.center.oa.note.dao.ShortMessageTaskDAO;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.RandomTools;
import com.china.center.tools.SequenceTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.centet.yongyin.bean.BaseBean;
import com.china.centet.yongyin.bean.FlowLogBean;
import com.china.centet.yongyin.bean.LocationBean;
import com.china.centet.yongyin.bean.OutBean;
import com.china.centet.yongyin.bean.PriceAskProviderBean;
import com.china.centet.yongyin.bean.Role;
import com.china.centet.yongyin.bean.StafferBean2;
import com.china.centet.yongyin.bean.StockBean;
import com.china.centet.yongyin.bean.StockItemBean;
import com.china.centet.yongyin.bean.StockPayBean;
import com.china.centet.yongyin.bean.StockPayItemBean;
import com.china.centet.yongyin.bean.User;
import com.china.centet.yongyin.bean.helper.LocationHelper;
import com.china.centet.yongyin.constant.Constant;
import com.china.centet.yongyin.constant.PriceConstant;
import com.china.centet.yongyin.constant.StockConstant;
import com.china.centet.yongyin.constant.SysConfigConstant;
import com.china.centet.yongyin.dao.BaseBeanDAO;
import com.china.centet.yongyin.dao.CommonDAO;
import com.china.centet.yongyin.dao.CommonDAO2;
import com.china.centet.yongyin.dao.FlowLogDAO;
import com.china.centet.yongyin.dao.LocationDAO;
import com.china.centet.yongyin.dao.ParameterDAO;
import com.china.centet.yongyin.dao.PriceAskProviderDAO;
import com.china.centet.yongyin.dao.PriceDAO;
import com.china.centet.yongyin.dao.StafferDAO2;
import com.china.centet.yongyin.dao.StockDAO;
import com.china.centet.yongyin.dao.StockItemDAO;
import com.china.centet.yongyin.dao.StockPayDAO;
import com.china.centet.yongyin.dao.StockPayItemDAO;
import com.china.centet.yongyin.vo.PriceAskProviderBeanVO;
import com.china.centet.yongyin.vo.StockBeanVO;
import com.china.centet.yongyin.vo.StockItemBeanVO;


/**
 * ��Ա���ѵ�manager
 * 
 * @author ZHUZHU
 * @version 2007-12-15
 * @see
 * @since
 */
public class StockManager
{
    private PriceDAO priceDAO = null;

    private StockDAO stockDAO = null;

    private CommonDAO commonDAO = null;

    private OutManager outManager = null;

    /**
     * ������־
     */
    private FlowLogDAO flowLogDAO = null;

    private StockItemDAO stockItemDAO = null;

    private LocationDAO locationDAO = null;

    private ParameterDAO parameterDAO = null;

    private StafferDAO2 stafferDAO2 = null;

    private CommonDAO2 commonDAO2 = null;

    private ShortMessageTaskDAO shortMessageTaskDAO = null;

    private BaseBeanDAO baseBeanDAO = null;

    private StockPayItemDAO stockPayItemDAO = null;

    private StockPayDAO stockPayDAO = null;

    private String stockLocation = "";

    private PriceAskProviderDAO priceAskProviderDAO = null;

    private static MoreHashMap<Integer, Integer, Role> map = new MoreHashMap<Integer, Integer, Role>();

    private static MoreHashMap<Integer, Integer, Role> rejectMap = new MoreHashMap<Integer, Integer, Role>();

    static
    {
        map.put(StockConstant.STOCK_STATUS_REJECT, StockConstant.STOCK_STATUS_SUBMIT, Role.COMMON);

        map.put(StockConstant.STOCK_ITEM_STATUS_INIT, StockConstant.STOCK_STATUS_SUBMIT,
            Role.COMMON);

        map.put(StockConstant.STOCK_STATUS_SUBMIT, StockConstant.STOCK_STATUS_MANAGERPASS,
            Role.MANAGER);

        map.put(StockConstant.STOCK_STATUS_MANAGERPASS, StockConstant.STOCK_STATUS_PRICEPASS,
            Role.PRICE);

        map.put(StockConstant.STOCK_STATUS_PRICEPASS, StockConstant.STOCK_STATUS_END, Role.STOCK);

        map.put(StockConstant.STOCK_STATUS_STOCKPASS, StockConstant.STOCK_STATUS_END,
            Role.STOCKMANAGER);

        rejectMap.put(StockConstant.STOCK_STATUS_SUBMIT, StockConstant.STOCK_STATUS_REJECT,
            Role.MANAGER);

        rejectMap.put(StockConstant.STOCK_STATUS_MANAGERPASS, StockConstant.STOCK_STATUS_REJECT,
            Role.PRICE);

        rejectMap.put(StockConstant.STOCK_STATUS_PRICEPASS, StockConstant.STOCK_STATUS_REJECT,
            Role.STOCK);

        rejectMap.put(StockConstant.STOCK_STATUS_STOCKPASS, StockConstant.STOCK_STATUS_REJECT,
            Role.STOCKMANAGER);
    }

    /**
     * default constructor
     */
    public StockManager()
    {}

    /**
     * ����ɹ���
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    @Exceptional
    public boolean addStockBean(final User user, final StockBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(bean);

        LocationBean lb = locationDAO.findLocation(bean.getLocationId());

        if (lb == null)
        {
            throw new MYException("���򲻴���");
        }

        bean.setId(lb.getLocationCode() + "_CG" + TimeTools.now("yyyyMMddHHmm")
                   + commonDAO.getSquenceString());

        bean.setStatus(StockConstant.STOCK_STATUS_INIT);

        // ����ɹ�������
        stockDAO.saveEntityBean(bean);

        // ����ɹ��Ĳ�Ʒ
        List<StockItemBean> items = bean.getItem();

        for (StockItemBean stockItemBean : items)
        {
            stockItemBean.setStockId(bean.getId());

            stockItemDAO.saveEntityBean(stockItemBean);
        }

        return true;
    }

    /**
     * addStockPayBean(�������ɸ��)
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public boolean addStockPayBean(final User user, List<String> payItemIdList)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(payItemIdList);

        List<StockPayBean> payList = new ArrayList();

        for (String eachStr : payItemIdList)
        {
            StockPayItemBean sib = stockPayItemDAO.find(eachStr);

            if (sib == null)
            {
                throw new MYException("���ݴ���,��ȷ�ϲ���");
            }

            if (sib.getStatus() != StockConstant.STOCK_ITEM_PAY_STATUS_INIT)
            {
                throw new MYException("�����Ѿ�����,��ȷ�ϲ���");
            }

            StockPayBean payEach = getPay(sib.getProviderId(), payList);

            if (payEach == null)
            {
                StockPayBean pay = new StockPayBean();

                payList.add(pay);

                pay.setId(SequenceTools.getSequence(10));

                pay.setProviderId(sib.getProviderId());

                pay.setLogTime(TimeTools.now());

                pay.setStafferId(user.getStafferId());

                pay.setTotal(sib.getTotal());

                payEach = pay;
            }
            else
            {
                payEach.setTotal(sib.getTotal() + payEach.getTotal());
            }

            sib.setPayId(payEach.getId());

            sib.setStatus(StockConstant.STOCK_ITEM_PAY_STATUS_USED);

            // �޸ĳɰ�
            stockPayItemDAO.updateEntityBean(sib);
        }

        // ȫ������
        stockPayDAO.saveAllEntityBeans(payList);

        return true;
    }

    private StockPayBean getPay(String providerId, List<StockPayBean> payList)
    {
        for (StockPayBean stockPayBean : payList)
        {
            if (stockPayBean.getProviderId().equals(providerId))
            {
                return stockPayBean;
            }
        }

        return null;
    }

    /**
     * ��ѯ�ɹ���
     * 
     * @param id
     * @return
     * @throws MYException
     */
    public StockBeanVO findStockVO(String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(id);

        StockBeanVO vo = stockDAO.findVO(id);

        List<StockItemBeanVO> itemVO = stockItemDAO.queryEntityVOsByFK(id);

        vo.setItemVO(itemVO);

        return vo;
    }

    /**
     * �ɹ�item�ĸı�
     * 
     * @param itemId
     * @param providerId
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    @Exceptional
    public boolean stockItemAskChange(String itemId, String providerId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(itemId, providerId);

        PriceAskProviderBean bean = priceAskProviderDAO.findBeanByAskIdAndProviderId(itemId,
            providerId);

        if (bean == null)
        {
            throw new MYException("ϵͳ����");
        }

        StockItemBean item = stockItemDAO.find(itemId);

        if (item == null)
        {
            throw new MYException("ϵͳ����");
        }

        item.setProviderId(providerId);

        item.setPrice(bean.getPrice());

        item.setTotal(bean.getPrice() * item.getAmount());

        // ����item
        stockItemDAO.updateEntityBean(item);

        List<StockItemBean> items = stockItemDAO.queryEntityBeansByFK(item.getStockId());

        double total = 0.0d;

        for (StockItemBean stockItemBean : items)
        {
            if (stockItemBean.getStatus() != StockConstant.STOCK_ITEM_STATUS_ASK)
            {
                throw new MYException("�ɹ����´���û��ѯ�۵Ĳ�Ʒ,����ͨ��");
            }

            total += stockItemBean.getTotal();
        }

        // ���²ɹ����ݵ��ܽ��
        stockDAO.updateTotal(item.getStockId(), total);

        return true;
    }

    /**
     * �ɹ�ѯ��
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    @Exceptional
    public boolean stockItemAsk(StockItemBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(bean);

        bean.setStatus(StockConstant.STOCK_ITEM_STATUS_ASK);

        bean.setTotal(bean.getPrice() * bean.getAmount());

        // ���޸�item��һЩֵ
        stockItemDAO.updateEntityBean(bean);

        // ɾ����ǰ��ѯ��
        priceAskProviderDAO.deleteEntityBeansByFK(bean.getId());

        for (PriceAskProviderBean priceAskProviderBean : bean.getAsks())
        {
            priceAskProviderDAO.saveEntityBean(priceAskProviderBean);
        }

        return true;
    }

    /**
     * ���²ɹ���
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    @Exceptional
    public boolean updateStockBean(final User user, final StockBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        StockBean sb = stockDAO.find(bean.getId());

        if (sb == null)
        {
            throw new MYException("�ɹ���������");
        }

        if (sb.getStatus() != StockConstant.STOCK_STATUS_REJECT
            && sb.getStatus() != StockConstant.STOCK_STATUS_INIT)
        {
            throw new MYException("�ɹ��������ڲ���״̬�����޸�");
        }

        // ���²ɹ�������
        stockDAO.updateEntityBean(bean);

        // ��ɾ��
        stockItemDAO.deleteEntityBeansByFK(bean.getId());

        List<StockItemBean> items = bean.getItem();

        for (StockItemBean stockItemBean : items)
        {
            stockItemBean.setStockId(bean.getId());

            stockItemDAO.saveEntityBean(stockItemBean);
        }

        return true;
    }

    /**
     * ���²ɹ���
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    @Exceptional
    public String endStock(final User user, final String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        if ( !user.getLocationID().equals(this.stockLocation))
        {
            throw new MYException("ֻ�вɹ�������Ա���ܽ����ɹ���");
        }

        String fullId = "";

        StockBean bean = stockDAO.find(id);

        if (bean == null)
        {
            throw new MYException("�ɹ���������");
        }

        if (bean.getStatus() != StockConstant.STOCK_STATUS_END)
        {
            throw new MYException("�ɹ��������ڲɹ���,���ܽ����ɹ�");
        }

        List<OutBean> beanList = autoToOut(user, id, bean);

        for (OutBean outBean : beanList)
        {
            fullId += outManager.coloneOutAndSubmitWithOutAffair(outBean, user) + ";";
        }

        // ����״̬���Ҽ�¼��־
        updateStockStatus(user, id, StockConstant.STOCK_STATUS_LASTEND, Constant.OPRMODE_PASS, "");

        return fullId;
    }

    /**
     * ���ݲɹ��������Զ���ⵥ��ÿ���ɹ���������һ����ⵥ
     * 
     * @param user
     * @param id
     * @param bean
     * @param out
     */
    private List<OutBean> autoToOut(final User user, final String id, StockBean bean)
    {
        List<OutBean> beanList = new ArrayList<OutBean>();

        List<StockItemBeanVO> items = stockItemDAO.queryEntityVOsByFK(id);

        for (StockItemBeanVO item : items)
        {
            List<BaseBean> baseList = new ArrayList<BaseBean>();

            OutBean out = new OutBean();

            out.setStatus(Constant.STATUS_SAVE);

            out.setStafferName(user.getStafferName());

            out.setType(Constant.OUT_TYPE_INBILL);

            out.setOutTime(TimeTools.now());

            out.setDepartment("�ɹ���");

            out.setCustomerId(item.getProviderId());

            out.setCustomerName(item.getProviderName());

            out.setLocationId(this.stockLocation);

            // ��Ʒ�������� �ɹ���
            out.setLocation(this.stockLocation);

            out.setTotal(item.getTotal());

            out.setDescription("�ɹ����Զ�ת������ⵥ,�ɹ�������:" + id);

            BaseBean baseBean = new BaseBean();

            baseBean.setValue(item.getTotal());
            baseBean.setLocationId(this.stockLocation);
            baseBean.setAmount(item.getAmount());
            baseBean.setProductId(item.getProductId());
            baseBean.setProductName(item.getProductName());
            baseBean.setUnit("��");
            baseBean.setPrice(item.getPrice());
            baseBean.setValue(item.getTotal());

            baseList.add(baseBean);

            out.setBaseList(baseList);

            beanList.add(out);
        }

        return beanList;
    }

    /**
     * �ɹ�������
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    @Exceptional
    public String stockItemChangeToOut(final User user, final String id, String tranNo)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        if ( !user.getLocationID().equals(this.stockLocation))
        {
            throw new MYException("ֻ�вɹ�������Ա���ܵ����ɹ���");
        }

        String fullId = null;

        StockItemBeanVO item = stockItemDAO.findVO(id);

        if (item == null)
        {
            throw new MYException("�ɹ��������");
        }

        if (item.getHasRef() != StockConstant.STOCK_ITEM_REF_NO)
        {
            throw new MYException("�ɹ�����ܵ���");
        }

        StockBean bean = stockDAO.find(item.getStockId());

        if (bean == null)
        {
            throw new MYException("�ɹ���������");
        }

        // ������ⵥ outManager
        OutBean out = new OutBean();

        itemAutoToOut(user, tranNo, item, bean, out);

        fullId = outManager.coloneOutAndSubmitWithOutAffair(out, user);

        if ( !StringTools.isNullOrNone(fullId))
        {
            StockItemBean sb = stockItemDAO.find(id);

            sb.setRefOutId(fullId);

            sb.setHasRef(StockConstant.STOCK_ITEM_REF_YES);

            stockItemDAO.updateEntityBean(sb);
        }

        return fullId;
    }

    /**
     * @param user
     * @param tranNo
     * @param item
     * @param bean
     * @param out
     */
    private void itemAutoToOut(final User user, String tranNo, StockItemBeanVO item,
                               StockBean bean, OutBean out)
    {
        out.setStatus(Constant.STATUS_SAVE);

        out.setStafferName(user.getStafferName());

        out.setType(Constant.OUT_TYPE_INBILL);

        out.setOutType(Constant.INBILL_OUT);

        out.setOutTime(TimeTools.now());

        out.setDepartment("�ɹ���");

        out.setCustomerId(item.getProviderId());

        out.setCustomerName(item.getProviderName());

        out.setLocationId(this.stockLocation);

        // ��Ʒ�������� �ɹ���
        out.setLocation(this.stockLocation);

        out.setDestinationId(LocationHelper.SYSTEMLOCATION);

        out.setTranNo(tranNo);

        out.setTotal(item.getTotal());

        out.setDescription("�ɹ����Զ�����ת���ɵ�����,�ɹ�������:" + bean.getId());

        List<BaseBean> baseList = new ArrayList<BaseBean>();

        BaseBean baseBean = new BaseBean();

        baseBean.setValue( -item.getTotal());
        baseBean.setLocationId(this.stockLocation);
        baseBean.setAmount( -item.getAmount());
        baseBean.setProductId(item.getProductId());
        baseBean.setProductName(item.getProductName());
        baseBean.setUnit("��");
        baseBean.setPrice(item.getPrice());
        baseBean.setValue(item.getTotal());

        baseList.add(baseBean);

        out.setBaseList(baseList);
    }

    /**
     * ���²ɹ���
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    @Exceptional
    public boolean delStockBean(final User user, final String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        StockBean sb = stockDAO.find(id);

        if (sb == null)
        {
            throw new MYException("�ɹ���������");
        }

        if ( !user.getId().equals(sb.getUserId()))
        {
            throw new MYException("û��Ȩ��");
        }

        if (sb.getStatus() != StockConstant.STOCK_STATUS_REJECT
            && sb.getStatus() != StockConstant.STOCK_STATUS_INIT)
        {
            throw new MYException("�ɹ��������ڳ�ʼ���߲���״̬����ɾ��");
        }

        // ���²ɹ�������
        stockDAO.deleteEntityBean(id);

        // ��ɾ��
        stockItemDAO.deleteEntityBeansByFK(id);

        flowLogDAO.deleteEntityBeansByFK(id);

        return true;
    }

    /**
     * ͨ��
     * 
     * @param user
     * @param id
     * @param reason
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class, DataAccessException.class})
    @Exceptional
    public boolean passStock(final User user, final String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        StockBeanVO sb = stockDAO.findVO(id);

        if (sb == null)
        {
            throw new MYException("�ɹ���������");
        }

        if (user.getRole() != getOprRole(sb.getStatus()))
        {
            throw new MYException("���ܲ���");
        }

        int nextStatus = getNextStatus(sb.getStatus());

        List<StockItemBeanVO> itemList = stockItemDAO.queryEntityVOsByFK(id);

        double total = 0.0d;

        // ������ύ��Ҫ��֤�Ƿ�������ѯ��
        checkSubmit(sb, nextStatus, itemList);

        // ѯ�۴��� ��Ҫȫ����item��ѯ�۽���
        if (nextStatus == StockConstant.STOCK_STATUS_PRICEPASS)
        {
            for (StockItemBean stockItemBean : itemList)
            {
                if (stockItemBean.getStatus() != StockConstant.STOCK_ITEM_STATUS_ASK)
                {
                    throw new MYException("�ɹ����´���û��ѯ�۵Ĳ�Ʒ,����ͨ��");
                }

                total += stockItemBean.getTotal();
            }

            stockDAO.updateTotal(id, total);
        }

        // �ɹ�����ͨ���� ���Ƚ�����5�򣬻�����ѡ��۸�����ͣ���Ҫ�ɹ��������
        if (sb.getStatus() == StockConstant.STOCK_STATUS_PRICEPASS)
        {
            if ( !checkMin(itemList))
            {
                stockDAO.updateExceptStatus(id, StockConstant.EXCEPTSTATUS_EXCEPTION_MIN);

                nextStatus = StockConstant.STOCK_STATUS_STOCKPASS;
            }

            StringBuilder sbStr = new StringBuilder();

            if ( !checkItemMoney(itemList, sbStr))
            {
                stockDAO.updateExceptStatus(id, StockConstant.EXCEPTSTATUS_EXCEPTION_MONEY);

                // ֪ͨ���³�(���³���IDΪ5)
                List<StafferBean2> sbList = stafferDAO2.queryByPostId("5");

                for (StafferBean2 stafferBean2 : sbList)
                {
                    sendSMSInner(user, stafferBean2, id, sb, sbStr.toString());
                }

                nextStatus = StockConstant.STOCK_STATUS_STOCKPASS;
            }
        }

        // ��������ʱ�� �Ѹ���item��״̬��־�ɽ���(��ѯ����Ϊ�ο�)
        if (nextStatus == StockConstant.STOCK_STATUS_END)
        {
            for (StockItemBean stockItemBean2 : itemList)
            {
                // ����״̬
                stockItemBean2.setStatus(StockConstant.STOCK_ITEM_STATUS_END);

                // ���¼�¼ʱ��
                stockItemBean2.setLogTime(TimeTools.now());

                stockItemDAO.updateEntityBean(stockItemBean2);
            }
        }

        String reason = "";

        // ��������ѯ�۵���������
        if (nextStatus == StockConstant.STOCK_STATUS_MANAGERPASS
            && sb.getType() == PriceConstant.PRICE_ASK_TYPE_NET)
        {
            // ֱ�ӵ�(�ɹ�����)
            nextStatus = StockConstant.STOCK_STATUS_PRICEPASS;

            // �������ռ۸�Ϊѯ�ۼ۸�
            for (StockItemBean iitem : itemList)
            {
                PriceAskProviderBean ppbs = priceAskProviderDAO.find(iitem.getPriceAskProviderId());

                if (ppbs == null)
                {
                    throw new MYException("���ݴ���,��ȷ�ϲ���");
                }

                iitem.setProviderId(ppbs.getProviderId());

                iitem.setPrice(iitem.getPrePrice());

                iitem.setTotal(iitem.getPrice() * iitem.getAmount());

                stockItemDAO.updateEntityBean(iitem);
            }

            reason = "����ѯ�۲ɹ�����ѯ��Աѯ��";
        }

        updateStockStatus(user, id, nextStatus, Constant.OPRMODE_PASS, reason);

        return true;
    }

    /**
     * sendSMSInner
     * 
     * @param user
     * @param sb
     * @param id
     * @param sbvo
     * @param message
     */
    private void sendSMSInner(User user, StafferBean2 sb, String id, StockBeanVO sbvo,
                              String message)
    {
        // send short message
        ShortMessageTaskBean sms = new ShortMessageTaskBean();

        sms.setId(commonDAO2.getSquenceString20());

        sms.setFk(id);

        sms.setType(104);

        sms.setHandId(RandomTools.getRandomMumber(4));

        sms.setStatus(ShortMessageConstant.STATUS_INIT);

        sms.setMtype(ShortMessageConstant.MTYPE_ONLY_SEND);

        sms.setFktoken("0");

        sms.setMessage(sbvo.getUserName() + "����Ĳɹ���" + message);

        sms.setReceiver(sb.getHandphone());

        sms.setStafferId(sb.getId());

        sms.setLogTime(TimeTools.now());

        // 24 hour
        sms.setEndTime(TimeTools.now(1));

        // internal
        sms.setSendTime(TimeTools.now());

        // add sms
        shortMessageTaskDAO.saveEntityBean(sms);
    }

    /**
     * checkSubmit
     * 
     * @param sb
     * @param nextStatus
     * @param itemList
     * @throws MYException
     */
    private void checkSubmit(StockBean sb, int nextStatus, List<StockItemBeanVO> itemList)
        throws MYException
    {
        if (nextStatus == StockConstant.STOCK_STATUS_SUBMIT
            && sb.getType() == PriceConstant.PRICE_ASK_TYPE_NET)
        {
            // ��ҪУ��������
            for (StockItemBean iitem : itemList)
            {
                if (StringTools.isNullOrNone(iitem.getPriceAskProviderId()))
                {
                    throw new MYException("���ݴ���,�����²���");
                }

                int sum = stockItemDAO.sumNetProductByPid(iitem.getPriceAskProviderId())
                          + iitem.getAmount();

                PriceAskProviderBeanVO ppb = priceAskProviderDAO.findVO(iitem.getPriceAskProviderId());

                if (ppb == null)
                {
                    throw new MYException("���ݴ���,�����²���");
                }

                if (ppb.getSupportAmount() < sum)
                {
                    throw new MYException(
                        "�����ɹ��й�Ӧ��[%s]�ṩ�Ĳ�Ʒ[%s]����ֻ��%d�Ѿ�ʹ����%d,������Ĳɹ�����[%d]�Ѿ�����(��������ҵ��Ա�Ѿ��ɹ�һ��)",
                        ppb.getProviderName(), ppb.getProductName(), ppb.getSupportAmount(),
                        (sum - iitem.getAmount()), iitem.getAmount());
                }
            }
        }
    }

    /**
     * ���ɹ����Ƿ�����С�ļ۸�
     * 
     * @param item
     * @return
     */
    private boolean checkMin(List<StockItemBeanVO> item)
    {
        List<PriceAskProviderBean> ppbs = null;
        for (StockItemBean stockItemBean : item)
        {
            ppbs = priceAskProviderDAO.queryEntityBeansByFK(stockItemBean.getId());

            double min = Integer.MAX_VALUE;
            for (PriceAskProviderBean priceAskProviderBean : ppbs)
            {
                min = Math.min(priceAskProviderBean.getPrice(), min);
            }

            if (stockItemBean.getPrice() > min)
            {
                return false;
            }
        }

        return true;
    }

    /**
     * ���ɹ��ĵ��ȼ۸���û�г���5���
     * 
     * @param item
     * @return
     */
    private boolean checkItemMoney(List<StockItemBeanVO> item, StringBuilder sbStr)
    {
        int max = parameterDAO.getInt(SysConfigConstant.STOCK_MAX_SINGLE_MONEY);

        for (StockItemBeanVO stockItemBean : item)
        {
            if (stockItemBean.getTotal() >= max)
            {
                sbStr.append("[" + stockItemBean.getProductName() + "]�Ĳɹ����Ϊ:"
                             + ElTools.formatNum(stockItemBean.getTotal()) + ",��ע��");

                return false;
            }
        }

        return true;
    }

    /**
     * ����
     * 
     * @param user
     * @param id
     * @param status
     * @param reason
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    @Exceptional
    public boolean rejectStock(final User user, final String id, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        StockBean sb = stockDAO.find(id);

        if (sb == null)
        {
            throw new MYException("�ɹ���������");
        }

        if (sb.getStatus() == StockConstant.STOCK_STATUS_REJECT
            || sb.getStatus() == StockConstant.STOCK_STATUS_INIT
            || sb.getStatus() == StockConstant.STOCK_STATUS_END
            || sb.getStatus() == StockConstant.STOCK_STATUS_LASTEND)
        {
            throw new MYException("�ɹ���״̬����");
        }

        Role role = rejectMap.getValue2(sb.getStatus());

        if (user.getRole() != role)
        {
            throw new MYException("���ܲ���");
        }

        int nextStatus = StockConstant.STOCK_STATUS_REJECT;

        recoverStockItemAsk(id);

        return updateStockStatus(user, id, nextStatus, Constant.OPRMODE_REJECT, reason);
    }

    /**
     * �ָ��ɹ�ѯ�۵ĳ�ʼ״̬
     * 
     * @param id
     */
    private void recoverStockItemAsk(final String id)
    {
        List<StockItemBean> item = stockItemDAO.queryEntityBeansByFK(id);

        // ɾ��ѯ��
        for (StockItemBean stockItemBean : item)
        {
            stockItemBean.setProviderId("");

            stockItemBean.setStatus(StockConstant.STOCK_ITEM_STATUS_INIT);

            stockItemBean.setPrice(0.0d);

            stockItemBean.setTotal(0.0d);

            // ���޸�item��һЩֵ
            stockItemDAO.updateEntityBean(stockItemBean);

            // ɾ����ǰ��ѯ��
            priceAskProviderDAO.deleteEntityBeansByFK(stockItemBean.getId());
        }
    }

    /**
     * stock���ص�ѯ��
     * 
     * @param user
     * @param id
     * @param status
     * @param reason
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    @Exceptional
    public boolean rejectStockToAsk(final User user, final String id, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        StockBean sb = stockDAO.find(id);

        if (sb == null)
        {
            throw new MYException("�ɹ���������");
        }

        // ֻ����ѯ��Աͨ���²��ܲ��ص�ѯ��Ա
        if (sb.getStatus() != StockConstant.STOCK_STATUS_PRICEPASS)
        {
            throw new MYException("�ɹ���״̬����");
        }

        if (user.getRole() != Role.STOCK)
        {
            throw new MYException("���ܲ���");
        }

        recoverStockItemAsk(id);

        return updateStockStatus(user, id, StockConstant.STOCK_STATUS_MANAGERPASS,
            Constant.OPRMODE_REJECT, reason);
    }

    /**
     * ���²ɹ�����״̬
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    private boolean updateStockStatus(final User user, final String id, int nextStatus,
                                      int oprMode, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        StockBean sb = stockDAO.find(id);

        if (sb == null)
        {
            throw new MYException("�ɹ���������");
        }

        // ���²ɹ�������
        stockDAO.updateStatus(id, nextStatus);

        addLog(user, id, nextStatus, sb, oprMode, reason);

        return true;
    }

    /**
     * ���²ɹ�����״̬
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public boolean updateStockPayStatus(final User user, final String id, int payStatus)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        checkPay(user, id, payStatus);

        // ���²ɹ�������
        stockDAO.updatePayStatus(id, payStatus);

        handleStockItemPay(user, id, payStatus);

        return true;
    }

    /**
     * handleStockItemPay
     * 
     * @param user
     * @param id
     * @param payStatus
     */
    private void handleStockItemPay(final User user, final String id, int payStatus)
    {
        if (payStatus == StockConstant.STOCK_PAY_YES)
        {
            // NOTE �Զ����ɸ��(���ݲɹ���item)
            List<StockItemBean> stockItemList = stockItemDAO.queryEntityBeansByFK(id);

            for (StockItemBean stockItemBean : stockItemList)
            {
                StockPayItemBean spib = new StockPayItemBean();

                spib.setId(SequenceTools.getSequence(10));

                spib.setStockId(id);

                spib.setStockItemId(stockItemBean.getId());

                spib.setStafferId(user.getStafferId());

                spib.setProviderId(stockItemBean.getProviderId());

                spib.setProductId(stockItemBean.getProductId());

                spib.setAmount(stockItemBean.getAmount());

                spib.setLogTime(TimeTools.now());

                spib.setPrice(stockItemBean.getPrice());

                spib.setTotal(stockItemBean.getPrice() * stockItemBean.getAmount());

                spib.setStatus(StockConstant.STOCK_ITEM_PAY_STATUS_INIT);

                stockPayItemDAO.saveEntityBean(spib);
            }
        }
    }

    /**
     * ����Ƿ�����޸Ĳɹ����ĸ���״̬
     * 
     * @param id
     * @param payStatus
     * @throws MYException
     */
    private void checkPay(User user, String id, int payStatus)
        throws MYException
    {
        StockBean sb = stockDAO.find(id);

        if (sb == null)
        {
            throw new MYException("�ɹ���������");
        }

        if (sb.getPay() == StockConstant.STOCK_PAY_YES)
        {
            throw new MYException("�ɹ�������ܲ���");
        }

        // ����
        if (payStatus == StockConstant.STOCK_PAY_APPLY)
        {
            if (user.getRole() != Role.STOCK)
            {
                throw new MYException("û��Ȩ��");
            }

            if (sb.getPay() != StockConstant.STOCK_PAY_NO
                && sb.getPay() != StockConstant.STOCK_PAY_REJECT)
            {
                throw new MYException("�ɹ����������븶��");
            }
        }

        if (payStatus == StockConstant.STOCK_PAY_REJECT)
        {
            if (user.getRole() != Role.MANAGER
                || !LocationHelper.isSystemLocation(user.getLocationID()))
            {
                throw new MYException("û��Ȩ��");
            }

            if (sb.getPay() != StockConstant.STOCK_PAY_APPLY)
            {
                throw new MYException("�ɹ������ܲ������븶��");
            }
        }

        if (payStatus == StockConstant.STOCK_PAY_YES)
        {
            if (user.getRole() != Role.STOCKMANAGER)
            {
                throw new MYException("û��Ȩ��");
            }

            if (sb.getPay() != StockConstant.STOCK_PAY_APPLY)
            {
                throw new MYException("�ɹ�������ͨ�����븶��");
            }
        }
    }

    /**
     * �����һ��״̬
     * 
     * @param current
     * @param isPass
     * @return
     */
    private int getNextStatus(int current)
    {
        return map.getValue1(current);
    }

    private Role getOprRole(int current)
    {
        return map.getValue2(current);
    }

    /**
     * ����log
     * 
     * @param user
     * @param id
     * @param status
     * @param sb
     */
    private void addLog(final User user, final String id, int status, StockBean sb, int oprMode,
                        String reason)
    {
        FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());

        log.setLogTime(TimeTools.now());

        log.setFullId(id);

        // ��������
        log.setOprMode(oprMode);

        log.setPreStatus(sb.getStatus());

        log.setAfterStatus(status);

        log.setDescription(reason);

        flowLogDAO.saveEntityBean(log);
    }

    /**
     * @return the priceDAO
     */
    public PriceDAO getPriceDAO()
    {
        return priceDAO;
    }

    /**
     * @param priceDAO
     *            the priceDAO to set
     */
    public void setPriceDAO(PriceDAO priceDAO)
    {
        this.priceDAO = priceDAO;
    }

    /**
     * @return the stockDAO
     */
    public StockDAO getStockDAO()
    {
        return stockDAO;
    }

    /**
     * @param stockDAO
     *            the stockDAO to set
     */
    public void setStockDAO(StockDAO stockDAO)
    {
        this.stockDAO = stockDAO;
    }

    /**
     * @return the priceAskProviderDAO
     */
    public PriceAskProviderDAO getPriceAskProviderDAO()
    {
        return priceAskProviderDAO;
    }

    /**
     * @param priceAskProviderDAO
     *            the priceAskProviderDAO to set
     */
    public void setPriceAskProviderDAO(PriceAskProviderDAO priceAskProviderDAO)
    {
        this.priceAskProviderDAO = priceAskProviderDAO;
    }

    /**
     * @return the stockItemDAO
     */
    public StockItemDAO getStockItemDAO()
    {
        return stockItemDAO;
    }

    /**
     * @param stockItemDAO
     *            the stockItemDAO to set
     */
    public void setStockItemDAO(StockItemDAO stockItemDAO)
    {
        this.stockItemDAO = stockItemDAO;
    }

    /**
     * @return the flowLogDAO
     */
    public FlowLogDAO getFlowLogDAO()
    {
        return flowLogDAO;
    }

    /**
     * @param flowLogDAO
     *            the flowLogDAO to set
     */
    public void setFlowLogDAO(FlowLogDAO flowLogDAO)
    {
        this.flowLogDAO = flowLogDAO;
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
     * @return the outManager
     */
    public OutManager getOutManager()
    {
        return outManager;
    }

    /**
     * @param outManager
     *            the outManager to set
     */
    public void setOutManager(OutManager outManager)
    {
        this.outManager = outManager;
    }

    /**
     * @return the stockLocation
     */
    public String getStockLocation()
    {
        return stockLocation;
    }

    /**
     * @param stockLocation
     *            the stockLocation to set
     */
    public void setStockLocation(String stockLocation)
    {
        this.stockLocation = stockLocation;
    }

    /**
     * @return the baseBeanDAO
     */
    public BaseBeanDAO getBaseBeanDAO()
    {
        return baseBeanDAO;
    }

    /**
     * @param baseBeanDAO
     *            the baseBeanDAO to set
     */
    public void setBaseBeanDAO(BaseBeanDAO baseBeanDAO)
    {
        this.baseBeanDAO = baseBeanDAO;
    }

    /**
     * @return the stockPayItemDAO
     */
    public StockPayItemDAO getStockPayItemDAO()
    {
        return stockPayItemDAO;
    }

    /**
     * @param stockPayItemDAO
     *            the stockPayItemDAO to set
     */
    public void setStockPayItemDAO(StockPayItemDAO stockPayItemDAO)
    {
        this.stockPayItemDAO = stockPayItemDAO;
    }

    /**
     * @return the stockPayDAO
     */
    public StockPayDAO getStockPayDAO()
    {
        return stockPayDAO;
    }

    /**
     * @param stockPayDAO
     *            the stockPayDAO to set
     */
    public void setStockPayDAO(StockPayDAO stockPayDAO)
    {
        this.stockPayDAO = stockPayDAO;
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
}
