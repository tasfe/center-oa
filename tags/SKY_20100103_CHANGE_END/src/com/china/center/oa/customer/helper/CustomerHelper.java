/**
 * File Name: CustomerHelper.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-9<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.customer.helper;


import com.china.center.oa.customer.bean.AbstractBean;
import com.china.center.tools.DecSecurity;


/**
 * CustomerHelper
 * 
 * @author zhuzhu
 * @version 2008-11-9
 * @see CustomerHelper
 * @since 1.0
 */
public abstract class CustomerHelper
{
    /**
     * ����
     * 
     * @param bean
     */
    public static void encryptCustomer(AbstractBean bean)
    {
        bean.setAddress(DecSecurity.encrypt(bean.getAddress()));

        bean.setHandphone(DecSecurity.encrypt(bean.getHandphone()));

        bean.setTel(DecSecurity.encrypt(bean.getTel()));

        bean.setFax(DecSecurity.encrypt(bean.getFax()));

        bean.setMail(DecSecurity.encrypt(bean.getMail()));

        bean.setQq(DecSecurity.encrypt(bean.getQq()));

        bean.setMsn(DecSecurity.encrypt(bean.getMsn()));

        bean.setWeb(DecSecurity.encrypt(bean.getWeb()));
    }

    /**
     * ����
     * 
     * @param bean
     */
    public static void decryptCustomer(AbstractBean bean)
    {
        bean.setAddress(DecSecurity.decrypt(bean.getAddress()));

        bean.setHandphone(DecSecurity.decrypt(bean.getHandphone()));

        bean.setTel(DecSecurity.decrypt(bean.getTel()));

        bean.setFax(DecSecurity.decrypt(bean.getFax()));

        bean.setMail(DecSecurity.decrypt(bean.getMail()));

        bean.setQq(DecSecurity.decrypt(bean.getQq()));

        bean.setMsn(DecSecurity.decrypt(bean.getMsn()));

        bean.setWeb(DecSecurity.decrypt(bean.getWeb()));
    }

    /**
     * ����
     * 
     * @param bean
     */
    public static void handleCustomer(AbstractBean bean)
    {
        bean.setAddress("����");

        bean.setHandphone("����");

        bean.setTel("����");

        bean.setFax("����");

        bean.setMail("����");

        bean.setQq("����");

        bean.setMsn("����");

        bean.setWeb("����");
    }
}
