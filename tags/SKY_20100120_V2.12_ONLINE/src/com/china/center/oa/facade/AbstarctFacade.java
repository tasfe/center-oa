/**
 * File Name: AbstarctFacade.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-9<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.facade;


import java.util.List;

import com.china.center.common.MYException;
import com.china.center.oa.constant.AuthConstant;
import com.china.center.oa.constant.PublicConstant;
import com.china.center.oa.publics.User;
import com.china.center.oa.publics.vs.RoleAuthBean;


/**
 * AbstarctFacade
 * 
 * @author zhuzhu
 * @version 2008-11-9
 * @see AbstarctFacade
 * @since 1.0
 */
public abstract class AbstarctFacade
{
    public boolean containAuth(User user, String authId)
    {
        if (authId.equals(AuthConstant.PUNLIC_AUTH))
        {
            return true;
        }

        List<RoleAuthBean> authList = user.getAuth();

        for (RoleAuthBean roleAuthBean : authList)
        {
            if (roleAuthBean.getAuthId().equals(authId))
            {
                return true;
            }
        }

        return false;
    }

    public void checkUser(User user)
        throws MYException
    {
        if (user == null)
        {
            throw new MYException("�û�������");
        }

        if (user.getStatus() == PublicConstant.LOGIN_STATUS_LOCK)
        {
            throw new MYException("�û�������,û���κβ���Ȩ��");
        }
    }

    protected MYException noAuth()
    {
        return new MYException("û��Ȩ��");
    }
}
