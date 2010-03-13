/**
 * 
 */
package com.china.center.oa.product.manager;


import java.io.Serializable;
import java.util.Formatter;
import java.util.List;

import net.sourceforge.sannotations.annotation.Bean;
import net.sourceforge.sannotations.annotation.Property;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.china.center.common.ConditionParse;
import com.china.center.common.MYException;
import com.china.center.oa.constant.ProductConstant;
import com.china.center.oa.constant.StafferConstant;
import com.china.center.oa.helper.Helper;
import com.china.center.oa.mail.bean.MailBean;
import com.china.center.oa.mail.manager.MailMangaer;
import com.china.center.oa.note.bean.ShortMessageConstant;
import com.china.center.oa.note.bean.ShortMessageTaskBean;
import com.china.center.oa.note.dao.ShortMessageTaskDAO;
import com.china.center.oa.note.manager.HandleMessage;
import com.china.center.oa.product.bean.OutOrderBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.bean.ProductStatBean;
import com.china.center.oa.product.dao.OutOrderDAO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.dao.ProductStatDAO;
import com.china.center.oa.publics.User;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.CommonDAO2;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.RandomTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * @author ZHUZHU
 */
@Exceptional
@Bean(name = "productStatManager")
public class ProductStatManager
{
    private final Log _logger = LogFactory.getLog(getClass());

    private ProductStatDAO productStatDAO = null;

    private ShortMessageTaskDAO shortMessageTaskDAO = null;

    private ProductDAO productDAO = null;

    private OutOrderDAO outOrderDAO = null;

    private CommonDAO2 commonDAO2 = null;

    private MailMangaer mailMangaer = null;

    private StafferDAO stafferDAO = null;

    @Property(value = "${businessManagerId}")
    private String businessManagerId = "";

    public ProductStatManager()
    {}

    /**
     * ��Ʒͳ��
     */
    @Transactional(rollbackFor = MYException.class)
    public void statProduct()
    {
        String logTime = TimeTools.now_short();

        productStatDAO.deleteEntityBeansByFK(logTime);

        List<ProductStatBean> statResultList = productStatDAO.queryStatProductByCondition();

        for (ProductStatBean productStatBean : statResultList)
        {
            String productId = productStatBean.getProductId();

            ProductBean productBean = productDAO.find(productId);

            if (productBean == null)
            {
                _logger.warn("warn in statProduct,product not found in table:" + productId);

                continue;
            }

            productStatBean.setId(commonDAO2.getSquenceString20());

            productStatBean.setLogTime(logTime);

            productStatBean.setSailAvg(productStatBean.getSailAmount() / ProductConstant.STAT_DAYS);

            if (productStatBean.getSailAvg() == 0
                && productStatBean.getSailAmount() > (ProductConstant.STAT_DAYS / 2))
            {
                productStatBean.setSailAvg(1);
            }

            productStatBean.setDescription("�Զ�ͳ�ƽ��");

            // ��ʼ���� �����Ʒ�Ŀ���� < (������+������)*��ƽ�������� + ��֪���� ��ʼԤ��
            int sumAmount = productStatDAO.sumProductAmountByProductId(productId);

            int notSail = outOrderDAO.sumOrderByProductId(productId);

            productStatBean.setInventoryAmount(sumAmount);

            productStatBean.setOrderAmount(notSail);

            int cum = (productBean.getMakeDays() + productBean.getFlowDays())
                      * productStatBean.getSailAvg() + notSail;

            if (sumAmount < cum)
            {
                productStatBean.setStatus(ProductConstant.STAT_STATUS_ALERT);

                productStatBean.setSubtractAmount(cum - sumAmount);

                // ���Ͷ��ź��ʼ�����Ӫ�ܼ�
                noteAlert(productStatBean);
            }
        }

        productStatDAO.saveAllEntityBeans(statResultList);
    }

    /**
     * noteAlert
     * 
     * @param productStatBean
     */
    private void noteAlert(ProductStatBean productStatBean)
    {
        List<StafferBean> stafferList = querybusinessManagers();

        // û����Ӫ�ܼ�
        if (ListTools.isEmptyOrNull(stafferList))
        {
            return;
        }

        MailBean mail = new MailBean();

        Formatter formatter = new Formatter();

        mail.setTitle(formatter.format("ϵͳ֪ͨ:[%s(%s)]�Ŀ������������[ȱ%d��]",
            productStatBean.getProductName(), productStatBean.getProductCode(),
            productStatBean.getSubtractAmount()).toString());

        StringBuilder builder = new StringBuilder();

        builder.append("�վ�����:").append(productStatBean.getSailAvg()).append(";");
        builder.append("15��������:").append(productStatBean.getSailAmount()).append(";");
        builder.append("���:").append(productStatBean.getInventoryAmount()).append(";");
        builder.append("������:").append(productStatBean.getOrderAmount()).append(";");
        builder.append("ȱ��:").append(productStatBean.getSubtractAmount()).append(";");

        mail.setContent(builder.toString());

        mail.setSenderId(StafferConstant.SUPER_STAFFER);

        mail.setReveiveIds(listToString(stafferList));

        // send mail
        try
        {
            mailMangaer.addMailWithoutTransactional(Helper.getSystemUser(), mail);
        }
        catch (MYException e)
        {
            _logger.error(e, e);
        }

        for (StafferBean stafferBean : stafferList)
        {
            // û���ֻ�����
            if (StringTools.isNullOrNone(stafferBean.getHandphone()))
            {
                continue;
            }

            // send short message
            ShortMessageTaskBean sms = new ShortMessageTaskBean();

            sms.setId(commonDAO2.getSquenceString20());

            sms.setFk(productStatBean.getId());

            sms.setType(HandleMessage.TYPE_PRODUCT);

            sms.setHandId(RandomTools.getRandomMumber(4));

            sms.setStatus(ShortMessageConstant.STATUS_INIT);

            sms.setMtype(ShortMessageConstant.MTYPE_ONLY_SEND);

            sms.setMessage("��Ʒ���Ԥ��:[" + productStatBean.getProductName() + "("
                           + productStatBean.getProductCode() + ")]�Ŀ������������[ȱ"
                           + productStatBean.getSubtractAmount() + "��]");

            sms.setReceiver(stafferBean.getHandphone());

            sms.setStafferId(stafferBean.getId());

            sms.setLogTime(TimeTools.now());

            // 24 hour
            sms.setEndTime(TimeTools.now(1));

            // ���￴����ǰ��ʱ�䣬һ��ֻ�ڰ��췢��
            sms.setSendTime(getSendTime());

            shortMessageTaskDAO.saveEntityBean(sms);
        }
    }

    private String getSendTime()
    {
        String sendTime = TimeTools.now();

        String minTime = TimeTools.now_short() + " 08:30:00";

        String maxTime = TimeTools.now_short() + " 17:00:00";

        String nextMaxTime = TimeTools.getDateString(1, TimeTools.SHORT_FORMAT) + " 08:30:00";

        if (sendTime.compareTo(minTime) < 0)
        {
            return minTime;
        }

        if (sendTime.compareTo(minTime) > 0 && sendTime.compareTo(maxTime) < 0)
        {
            return sendTime;
        }

        return nextMaxTime;
    }

    private String listToString(List<StafferBean> processers)
    {
        StringBuilder builder = new StringBuilder();

        for (StafferBean bean : processers)
        {
            builder.append(bean.getId()).append(';');
        }

        return builder.toString();
    }

    /**
     * ��ȡ��Ӫ�ܼ���ѡ
     * 
     * @return
     */
    private List<StafferBean> querybusinessManagers()
    {
        ConditionParse condition = new ConditionParse();

        condition.addWhereStr();

        condition.addCondition("postId", "=", this.businessManagerId);

        condition.addIntCondition("status", "=", StafferConstant.STATUS_COMMON);

        return this.stafferDAO.queryEntityBeansBycondition(condition);
    }

    /**
     * ɾ����ʷ����
     */
    @Transactional(rollbackFor = MYException.class)
    public void deleteHistoryData()
    {
        // ɾ����ȥ6���µ�����
        String logTime = TimeTools.getDateFullString( -6 * 30, TimeTools.SHORT_FORMAT);

        ConditionParse condition = new ConditionParse();

        condition.addWhereStr();

        condition.addCondition("logTime", "<=", logTime);

        productStatDAO.deleteEntityBeansByCondition(condition);

        // ɾ��6���µĶ���
        logTime = TimeTools.getDateFullString( -6 * 30, TimeTools.LONG_FORMAT);

        condition = new ConditionParse();

        condition.addWhereStr();

        condition.addCondition("logTime", "<=", logTime);

        outOrderDAO.deleteEntityBeansByCondition(condition);
    }

    /**
     * addOutOrderBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean addOutOrder(User user, OutOrderBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        bean.setId(commonDAO2.getSquenceString20());

        outOrderDAO.saveEntityBean(bean);

        return true;
    }

    /**
     * deleteOutOrder
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean cancleOutOrder(User user, Serializable id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        OutOrderBean old = checkCancel(id);

        old.setStatus(ProductConstant.ORDER_STATUS_END);

        outOrderDAO.updateEntityBean(old);

        return true;
    }

    /**
     * checkCancel
     * 
     * @param id
     * @return
     * @throws MYException
     */
    private OutOrderBean checkCancel(Serializable id)
        throws MYException
    {
        OutOrderBean old = outOrderDAO.find(id);

        if (old == null)
        {
            throw new MYException("���ݴ���,��ȷ�ϲ���");
        }

        if (old.getStatus() != ProductConstant.ORDER_STATUS_COMMON)
        {
            throw new MYException("���ݴ���,��ȷ�ϲ���");
        }

        return old;
    }

    public ProductStatDAO getProductStatDAO()
    {
        return productStatDAO;
    }

    public void setProductStatDAO(ProductStatDAO productStatDAO)
    {
        this.productStatDAO = productStatDAO;
    }

    public CommonDAO2 getCommonDAO2()
    {
        return commonDAO2;
    }

    public void setCommonDAO2(CommonDAO2 commonDAO2)
    {
        this.commonDAO2 = commonDAO2;
    }

    public ProductDAO getProductDAO()
    {
        return productDAO;
    }

    public void setProductDAO(ProductDAO productDAO)
    {
        this.productDAO = productDAO;
    }

    /**
     * @return the outOrderDAO
     */
    public OutOrderDAO getOutOrderDAO()
    {
        return outOrderDAO;
    }

    /**
     * @param outOrderDAO
     *            the outOrderDAO to set
     */
    public void setOutOrderDAO(OutOrderDAO outOrderDAO)
    {
        this.outOrderDAO = outOrderDAO;
    }

    /**
     * @return the mailMangaer
     */
    public MailMangaer getMailMangaer()
    {
        return mailMangaer;
    }

    /**
     * @param mailMangaer
     *            the mailMangaer to set
     */
    public void setMailMangaer(MailMangaer mailMangaer)
    {
        this.mailMangaer = mailMangaer;
    }

    /**
     * @return the businessManagerId
     */
    public String getBusinessManagerId()
    {
        return businessManagerId;
    }

    /**
     * @param businessManagerId
     *            the businessManagerId to set
     */
    public void setBusinessManagerId(String businessManagerId)
    {
        this.businessManagerId = businessManagerId;
    }

    /**
     * @return the stafferDAO
     */
    public StafferDAO getStafferDAO()
    {
        return stafferDAO;
    }

    /**
     * @param stafferDAO
     *            the stafferDAO to set
     */
    public void setStafferDAO(StafferDAO stafferDAO)
    {
        this.stafferDAO = stafferDAO;
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
}
