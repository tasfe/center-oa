/**
 * File Name: BillManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-26<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.manager;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.finance.bean.InBillBean;
import com.china.center.oa.finance.bean.OutBillBean;


/**
 * BillManager
 * 
 * @author ZHUZHU
 * @version 2010-12-26
 * @see BillManager
 * @since 3.0
 */
public interface BillManager
{
    /**
     * addInBillBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean addInBillBean(User user, InBillBean bean)
        throws MYException;

    /**
     * addInBillBeanWithoutTransaction
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean addInBillBeanWithoutTransaction(User user, InBillBean bean)
        throws MYException;

    boolean saveInBillInner(InBillBean bean)
        throws MYException;

    boolean updateInBillBean(User user, InBillBean bean)
        throws MYException;

    boolean updateInBillBeanChecks(User user, String id, String checks)
        throws MYException;

    boolean updateOutBillBeanChecks(User user, String id, String checks)
        throws MYException;

    /**
     * 更新核对状态(包括收付款)
     * 
     * @param user
     * @param id
     * @param checks
     * @return
     * @throws MYException
     */
    boolean updateBillBeanChecksWithoutTransactional(User user, String id, String checks)
        throws MYException;

    boolean deleteInBillBean(User user, String id)
        throws MYException;

    /**
     * 通过转账
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean passTransferOutBillBean(User user, String id)
        throws MYException;

    /**
     * 驳回转账
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean rejectTransferOutBillBean(User user, String id)
        throws MYException;

    /**
     * 分拆
     * 
     * @param user
     * @param id
     * @param newMoney
     * @return
     * @throws MYException
     */
    String splitInBillBean(User user, String id, double newMoney)
        throws MYException;

    /**
     * 返回newMoney的ID
     * 
     * @param user
     * @param id
     * @param newMoney
     * @return
     * @throws MYException
     */
    String splitInBillBeanWithoutTransactional(User user, String id, double newMoney)
        throws MYException;

    /**
     * addOutBillBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean addOutBillBean(User user, OutBillBean bean)
        throws MYException;

    boolean addOutBillBeanWithoutTransaction(User user, OutBillBean bean)
        throws MYException;

    /**
     * deleteOutBillBean
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean deleteOutBillBean(User user, String id)
        throws MYException;

    /**
     * 预收移交
     * 
     * @param user
     * @param dest
     * @return
     * @throws MYException
     */
    boolean chageBillToTran(User user)
        throws MYException;
}
