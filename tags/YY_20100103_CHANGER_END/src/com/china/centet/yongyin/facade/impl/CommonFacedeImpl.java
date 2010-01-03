/**
 *
 */
package com.china.centet.yongyin.facade.impl;


import com.china.center.tools.Security;
import com.china.centet.yongyin.bean.User;
import com.china.centet.yongyin.constant.Constant;
import com.china.centet.yongyin.dao.UserDAO;
import com.china.centet.yongyin.facade.CommonFacede;


/**
 * @author Administrator
 */
public class CommonFacedeImpl implements CommonFacede
{
    private UserDAO userDAO = null;

    /**
     * ʵ��
     */
    public User login(String name, String password)
    {
        User user = userDAO.findUserByLoginName(name);

        if (user == null)
        {
            return null;
        }

        // ��������
        if (user.getStatus() == Constant.LOGIN_STATUS_LOCK)
        {
            return null;
        }

        // ��֤����
        if (user.getPassword().equals(Security.getSecurity(password)))
        {
            return user;
        }
        else
        {
            if ( (user.getFail() + 1) >= Constant.LOGIN_FAIL_MAX)
            {
                userDAO.modifyStatus(user.getName(), Constant.LOGIN_STATUS_LOCK);

                userDAO.modifyFail(user.getName(), 0);
            }
            else
            {
                userDAO.modifyFail(user.getName(), user.getFail() + 1);
            }

            return null;
        }
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

}
