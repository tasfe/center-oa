/**
 * File Name: BankDAO.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-12-15
 * Grant: open source to everybody
 */
package com.china.centet.yongyin.manager;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.china.center.common.ConditionParse;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.TimeTools;
import com.china.centet.yongyin.bean.GradeBean;
import com.china.centet.yongyin.bean.Logger;
import com.china.centet.yongyin.bean.MemberBean;
import com.china.centet.yongyin.bean.User;
import com.china.centet.yongyin.dao.ConsumeDAO;
import com.china.centet.yongyin.dao.ExchangeDAO;
import com.china.centet.yongyin.dao.GradeDAO;
import com.china.centet.yongyin.dao.LogDAO;
import com.china.centet.yongyin.dao.MemberDAO;
import com.china.centet.yongyin.dao.ProductTypeVSCustomerDAO;
import com.china.centet.yongyin.vs.ProductTypeVSCustomer;


/**
 * ��Ա��manager
 * 
 * @author zhuzhu
 * @version 2007-12-15
 * @see
 * @since
 */
public class MemeberManager
{
    private final Log _logger = LogFactory.getLog(getClass());

    private MemberDAO memberDAO = null;

    private ConsumeDAO consumeDAO = null;

    private GradeDAO gradeDAO = null;

    private LogDAO logDAO = null;

    private ExchangeDAO exchangeDAO = null;

    private ProductTypeVSCustomerDAO productTypeVSCustomerDAO = null;

    private DataSourceTransactionManager transactionManager = null;

    /**
     * default constructor
     */
    public MemeberManager()
    {}

    /**
     * ���ӻ�Ա
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean addMember(final MemberBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(bean, bean.getCardNo());

        try
        {

            if (memberDAO.countByCardNo(bean.getCardNo()) > 0)
            {
                // ���ڴ˿���
                throw new MYException("�����Ѿ�����:" + bean.getCardNo());
            }

            GradeBean gbean = gradeDAO.find(bean.getGrade());

            if (gbean == null)
            {
                bean.setRebate(1.0d);
            }
            else
            {
                bean.setRebate(gbean.getRebate());
            }

            // ���ӹ���Ա���������ݿ����������
            TransactionTemplate tran = new TransactionTemplate(transactionManager);

            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    memberDAO.saveEntityBean(bean);

                    return Boolean.TRUE;
                }
            });
        }
        catch (TransactionException e)
        {
            _logger.error("���ӻ�Ա����", e);
            throw new MYException("���ݿ��ڲ�����");
        }
        catch (DataAccessException e)
        {
            _logger.error("���ӻ�Ա����", e);
            throw new MYException(e.getCause().toString());
        }

        return true;
    }

    /**
     * ���ӻ�Ա
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean bingProductTypeToCustmer(final String customerId, final String[] productTypeIds)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(customerId, productTypeIds);

        try
        {
            // ���ӹ���Ա���������ݿ����������
            TransactionTemplate tran = new TransactionTemplate(transactionManager);

            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    productTypeVSCustomerDAO.delVSByCustomerId(customerId);

                    for (String item : productTypeIds)
                    {
                        ProductTypeVSCustomer bean = new ProductTypeVSCustomer();

                        bean.setCustomerId(customerId);

                        bean.setProductTypeId(item);

                        productTypeVSCustomerDAO.saveEntityBean(bean);
                    }

                    return Boolean.TRUE;
                }
            });
        }
        catch (TransactionException e)
        {
            _logger.error(e, e);
            throw new MYException("ϵͳ����");
        }
        catch (DataAccessException e)
        {
            _logger.error(e, e);
            throw new MYException("ϵͳ����");
        }

        return true;
    }

    /**
     * �޸Ļ�Ա
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean updateMember(final MemberBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(bean, bean.getId());

        try
        {
            MemberBean mem = memberDAO.find(bean.getId());

            if (mem == null)
            {
                throw new MYException("��Ա������");
            }

            // ���޸Ļ���
            bean.setPoint(mem.getPoint());

            MemberBean mem1 = memberDAO.findMemberByCardNo(bean.getCardNo());

            if (mem1 != null)
            {
                if ( !mem1.getId().equals(bean.getId()))
                {
                    throw new MYException("��Ա���Ų����ظ�:" + bean.getCardNo());
                }

            }

            if ( !bean.getCardNo().equals(mem.getCardNo()))
            {
                _logger.info("����:" + mem.getCardNo() + "|" + bean.getCardNo());
            }

            // ���ӹ���Ա���������ݿ����������
            TransactionTemplate tran = new TransactionTemplate(transactionManager);

            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    memberDAO.updateEntityBean(bean);

                    return Boolean.TRUE;
                }
            });
        }
        catch (TransactionException e)
        {
            _logger.error("�޸Ļ�Ա����", e);
            throw new MYException("���ݿ��ڲ�����");
        }
        catch (DataAccessException e)
        {
            _logger.error("�޸Ļ�Ա����", e);
            throw new MYException(e.getCause().toString());
        }

        return true;
    }

    /**
     * ɾ����Ա
     * 
     * @param id
     * @return
     * @throws MYException
     */
    public boolean delMember(User user, final String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(id);

        MemberBean mem = null;
        try
        {
            mem = memberDAO.find(id);

            if (mem == null)
            {
                throw new MYException("��Ա������");
            }

            // ���ӹ���Ա���������ݿ����������
            TransactionTemplate tran = new TransactionTemplate(transactionManager);

            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    memberDAO.deleteEntityBean(id);

                    // ɾ����Ա�����Ѽ�¼
                    consumeDAO.delConsumesByMemberId(id);

                    // ɾ���һ���¼
                    exchangeDAO.delExchangesByMemberId(id);

                    return Boolean.TRUE;
                }
            });
        }
        catch (TransactionException e)
        {
            _logger.error("ɾ����Ա����", e);
            throw new MYException("���ݿ��ڲ�����");
        }
        catch (DataAccessException e)
        {
            _logger.error("ɾ����Ա����", e);
            throw new MYException(e.getCause().toString());
        }

        setLog(user, mem);

        return true;
    }

    /**
     * @param user
     * @param mem
     */
    private void setLog(User user, MemberBean mem)
    {
        Logger log = new Logger();

        log.setUserId(user.getId());

        log.setModule("��Ա");

        log.setOperation("ɾ��");

        log.setLogTime(TimeTools.now());

        log.setLog("�ɹ�ɾ����Ա:" + mem.getCardNo());

        log.setLocationId(user.getLocationID());

        logDAO.saveEntityBean(log);
    }

    public MemberBean findMemberById(String id)
    {
        return memberDAO.find(id);
    }

    public MemberBean findMemberByCardNo(String cardNo)
    {
        return memberDAO.findMemberByCardNo(cardNo);
    }

    public int countByCondtion(ConditionParse condtion)
    {
        condtion.addWhereStr();

        return memberDAO.countBycondition(condtion.toString());
    }

    public List<MemberBean> queryMemberByCondtion(ConditionParse condtion, PageSeparate page)
    {
        return memberDAO.queryEntityBeansBycondition(condtion, page);
    }

    /**
     * @return the memberDAO
     */
    public MemberDAO getMemberDAO()
    {
        return memberDAO;
    }

    /**
     * @param memberDAO
     *            the memberDAO to set
     */
    public void setMemberDAO(MemberDAO memberDAO)
    {
        this.memberDAO = memberDAO;
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
     * @return the consumeDAO
     */
    public ConsumeDAO getConsumeDAO()
    {
        return consumeDAO;
    }

    /**
     * @param consumeDAO
     *            the consumeDAO to set
     */
    public void setConsumeDAO(ConsumeDAO consumeDAO)
    {
        this.consumeDAO = consumeDAO;
    }

    /**
     * @return the logDAO
     */
    public LogDAO getLogDAO()
    {
        return logDAO;
    }

    /**
     * @param logDAO
     *            the logDAO to set
     */
    public void setLogDAO(LogDAO logDAO)
    {
        this.logDAO = logDAO;
    }

    /**
     * @return the gradeDAO
     */
    public GradeDAO getGradeDAO()
    {
        return gradeDAO;
    }

    /**
     * @param gradeDAO
     *            the gradeDAO to set
     */
    public void setGradeDAO(GradeDAO gradeDAO)
    {
        this.gradeDAO = gradeDAO;
    }

    /**
     * @return the exchangeDAO
     */
    public ExchangeDAO getExchangeDAO()
    {
        return exchangeDAO;
    }

    /**
     * @param exchangeDAO
     *            the exchangeDAO to set
     */
    public void setExchangeDAO(ExchangeDAO exchangeDAO)
    {
        this.exchangeDAO = exchangeDAO;
    }

    /**
     * @return the productTypeVSCustomerDAO
     */
    public ProductTypeVSCustomerDAO getProductTypeVSCustomerDAO()
    {
        return productTypeVSCustomerDAO;
    }

    /**
     * @param productTypeVSCustomerDAO
     *            the productTypeVSCustomerDAO to set
     */
    public void setProductTypeVSCustomerDAO(ProductTypeVSCustomerDAO productTypeVSCustomerDAO)
    {
        this.productTypeVSCustomerDAO = productTypeVSCustomerDAO;
    }

}
