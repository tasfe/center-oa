/**
 * File Name: OrgManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-23<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager;


import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.wrap.StafferOrgWrap;


/**
 * OrgManager
 * 
 * @author ZHUZHU
 * @version 2010-6-23
 * @see OrgManager
 * @since 1.0
 */
public interface OrgManager
{
    List<PrincipalshipBean> querySubPrincipalship(String id)
        throws MYException;

    boolean addBean(User user, PrincipalshipBean bean)
        throws MYException;

    boolean addBeanWithoutTransactional(User user, PrincipalshipBean bean)
        throws MYException;

    boolean updateBean(User user, PrincipalshipBean bean, boolean modfiyParent)
        throws MYException;

    boolean delBean(User user, String id)
        throws MYException;

    boolean delBeanWithoutTransactional(User user, String id)
        throws MYException;

    List<StafferOrgWrap> queryAllSubStaffer(String prinRootId)
        throws MYException;
}