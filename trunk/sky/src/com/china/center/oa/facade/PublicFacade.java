/**
 * File Name: PublicFacade.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.facade;


import java.util.List;

import net.sourceforge.sannotations.annotation.Bean;

import com.china.center.common.MYException;
import com.china.center.oa.constant.AuthConstant;
import com.china.center.oa.publics.User;
import com.china.center.oa.publics.bean.DepartmentBean;
import com.china.center.oa.publics.bean.LocationBean;
import com.china.center.oa.publics.bean.PostBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.RoleBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.bean.UserBean;
import com.china.center.oa.publics.helper.RoleHelper;
import com.china.center.oa.publics.manager.DepartmentManager;
import com.china.center.oa.publics.manager.LocationManager;
import com.china.center.oa.publics.manager.OrgManager;
import com.china.center.oa.publics.manager.PostManager;
import com.china.center.oa.publics.manager.RoleManager;
import com.china.center.oa.publics.manager.StafferManager;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.oa.publics.vs.LocationVSCityBean;
import com.china.center.tools.JudgeTools;


/**
 * PublicFacade(Ȩ�޿���)
 * 
 * @author zhuzhu
 * @version 2008-11-2
 * @see PublicFacade
 * @since 1.0
 */
@Bean(name = "publicFacade")
public class PublicFacade extends AbstarctFacade
{
    private LocationManager locationManager = null;

    private StafferManager stafferManager = null;

    private RoleManager roleManager = null;

    private UserManager userManager = null;

    private OrgManager orgManager = null;

    private DepartmentManager departmentManager = null;

    private PostManager postManager = null;

    /**
     * default constructor
     */
    public PublicFacade()
    {}

    /**
     * addLocationBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean addLocationBean(User user, LocationBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        if (containAuth(user, AuthConstant.LOCATION_OPR))
        {
            return locationManager.addBean(user, bean);
        }
        else
        {
            throw new MYException("û��Ȩ��");
        }
    }

    /**
     * addLocationVSCity
     * 
     * @param user
     * @param locationId
     * @param list
     * @return
     * @throws MYException
     */
    public boolean addLocationVSCity(User user, String locationId, List<LocationVSCityBean> list)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, locationId, list);

        if (containAuth(user, AuthConstant.LOCATION_OPR))
        {
            return locationManager.addLocationVSCity(user, locationId, list);
        }
        else
        {
            throw new MYException("û��Ȩ��");
        }
    }

    /**
     * delLocationBean
     * 
     * @param user
     * @param locationId
     * @return
     * @throws MYException
     */
    public boolean delLocationBean(User user, String locationId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, locationId);

        if (containAuth(user, AuthConstant.LOCATION_OPR))
        {
            return locationManager.delBean(user, locationId);
        }
        else
        {
            throw new MYException("û��Ȩ��");
        }
    }

    /**
     * addStafferBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean addStafferBean(User user, StafferBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        if (containAuth(user, AuthConstant.STAFFER_OPR))
        {
            return stafferManager.addBean(user, bean);
        }
        else
        {
            throw new MYException("û��Ȩ��");
        }
    }

    /**
     * updateStafferBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean updateStafferBean(User user, StafferBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        if (containAuth(user, AuthConstant.STAFFER_OPR))
        {
            return stafferManager.updateBean(user, bean);
        }
        else
        {
            throw new MYException("û��Ȩ��");
        }
    }

    /**
     * updateStafferBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean updateStafferPwkey(User user, StafferBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        if (RoleHelper.isSuperManager(user))
        {
            return stafferManager.updatePwkey(user, bean);
        }
        else
        {
            throw new MYException("û��Ȩ��");
        }
    }

    /**
     * delStafferBean
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    public boolean delStafferBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        if (containAuth(user, AuthConstant.STAFFER_OPR))
        {
            return stafferManager.delBean(user, id);
        }
        else
        {
            throw new MYException("û��Ȩ��");
        }
    }

    /**
     * addRoleBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean addRoleBean(String userId, RoleBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.ROLE_OPR))
        {
            return roleManager.addBean(user, bean);
        }
        else
        {
            throw new MYException("û��Ȩ��");
        }
    }

    /**
     * updateRoleBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean updateRoleBean(String userId, RoleBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.ROLE_OPR))
        {
            return roleManager.updateBean(user, bean);
        }
        else
        {
            throw new MYException("û��Ȩ��");
        }
    }

    /**
     * delRoleBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean delRoleBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.ROLE_OPR))
        {
            return roleManager.delBean(user, id);
        }
        else
        {
            throw new MYException("û��Ȩ��");
        }
    }

    /**
     * addUserBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean addUserBean(String userId, UserBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.USER_OPR))
        {
            return userManager.addBean(user, bean);
        }
        else
        {
            throw new MYException("û��Ȩ��");
        }
    }

    /**
     * updateUserBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean updateUserBean(String userId, UserBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.USER_OPR))
        {
            return userManager.updateBean(user, bean);
        }
        else
        {
            throw new MYException("û��Ȩ��");
        }
    }

    /**
     * delRoleBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean delUserBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.USER_OPR))
        {
            return userManager.delBean(user, id);
        }
        else
        {
            throw new MYException("û��Ȩ��");
        }
    }

    /**
     * updateUserPassword
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean updateUserPassword(String userId, String id, String password)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, password);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.USER_OPR))
        {
            return userManager.updatePassword(id, password);
        }
        else
        {
            throw new MYException("û��Ȩ��");
        }
    }

    /**
     * updateUserStatus
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean updateUserStatus(String userId, String id, int status)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.USER_OPR))
        {
            return userManager.updateStatus(id, status);
        }
        else
        {
            throw new MYException("û��Ȩ��");
        }
    }

    /**
     * updateUserLocation
     * 
     * @param userId
     * @param id
     * @param locationId
     * @return
     * @throws MYException
     */
    public boolean updateUserLocation(String userId, String id, String locationId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.USER_OPR))
        {
            return userManager.updateLocation(id, locationId);
        }
        else
        {
            throw new MYException("û��Ȩ��");
        }
    }

    /**
     * addOrgBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean addOrgBean(String userId, PrincipalshipBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.ORG_MANAGER))
        {
            return orgManager.addBean(user, bean);
        }
        else
        {
            throw new MYException("û��Ȩ��");
        }
    }

    /**
     * updateOrgBean
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean updateOrgBean(String userId, PrincipalshipBean bean, boolean modfiyParent)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.ORG_MANAGER))
        {
            return orgManager.updateBean(user, bean, modfiyParent);
        }
        else
        {
            throw new MYException("û��Ȩ��");
        }
    }

    public boolean delOrgBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.ORG_MANAGER))
        {
            return orgManager.delBean(user, id);
        }
        else
        {
            throw new MYException("û��Ȩ��");
        }
    }

    /**
     * addDepartmentBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean addDepartmentBean(String userId, DepartmentBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.DEPARTMENT_MANAGER))
        {
            return departmentManager.addBean(user, bean);
        }
        else
        {
            throw new MYException("û��Ȩ��");
        }
    }

    /**
     * updateDepartmentBean
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean updateDepartmentBean(String userId, DepartmentBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.DEPARTMENT_MANAGER))
        {
            return departmentManager.updateBean(user, bean);
        }
        else
        {
            throw new MYException("û��Ȩ��");
        }
    }

    /**
     * delDepartmentBean
     * 
     * @param userId
     * @param id
     * @return
     * @throws MYException
     */
    public boolean delDepartmentBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.DEPARTMENT_MANAGER))
        {
            return departmentManager.delBean(user, id);
        }
        else
        {
            throw new MYException("û��Ȩ��");
        }
    }

    /**
     * addPostBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean addPostBean(String userId, PostBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.POST_MANAGER))
        {
            return postManager.addBean(user, bean);
        }
        else
        {
            throw new MYException("û��Ȩ��");
        }
    }

    /**
     * updatePostBean
     * 
     * @param userId
     * @param bean
     * @return
     * @throws MYException
     */
    public boolean updatePostBean(String userId, PostBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, bean);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.POST_MANAGER))
        {
            return postManager.updateBean(user, bean);
        }
        else
        {
            throw new MYException("û��Ȩ��");
        }
    }

    /**
     * delPostBean
     * 
     * @param userId
     * @param id
     * @return
     * @throws MYException
     */
    public boolean delPostBean(String userId, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(userId, id);

        User user = userManager.findUser(userId);

        checkUser(user);

        if (containAuth(user, AuthConstant.POST_MANAGER))
        {
            return postManager.delBean(user, id);
        }
        else
        {
            throw new MYException("û��Ȩ��");
        }
    }

    /**
     * @return the locationManager
     */
    public LocationManager getLocationManager()
    {
        return locationManager;
    }

    /**
     * @param locationManager
     *            the locationManager to set
     */
    public void setLocationManager(LocationManager locationManager)
    {
        this.locationManager = locationManager;
    }

    /**
     * @return the stafferManager
     */
    public StafferManager getStafferManager()
    {
        return stafferManager;
    }

    /**
     * @param stafferManager
     *            the stafferManager to set
     */
    public void setStafferManager(StafferManager stafferManager)
    {
        this.stafferManager = stafferManager;
    }

    /**
     * @return the roleManager
     */
    public RoleManager getRoleManager()
    {
        return roleManager;
    }

    /**
     * @param roleManager
     *            the roleManager to set
     */
    public void setRoleManager(RoleManager roleManager)
    {
        this.roleManager = roleManager;
    }

    /**
     * @return the userManager
     */
    public UserManager getUserManager()
    {
        return userManager;
    }

    /**
     * @param userManager
     *            the userManager to set
     */
    public void setUserManager(UserManager userManager)
    {
        this.userManager = userManager;
    }

    /**
     * @return the orgManager
     */
    public OrgManager getOrgManager()
    {
        return orgManager;
    }

    /**
     * @param orgManager
     *            the orgManager to set
     */
    public void setOrgManager(OrgManager orgManager)
    {
        this.orgManager = orgManager;
    }

    /**
     * @return the departmentManager
     */
    public DepartmentManager getDepartmentManager()
    {
        return departmentManager;
    }

    /**
     * @param departmentManager
     *            the departmentManager to set
     */
    public void setDepartmentManager(DepartmentManager departmentManager)
    {
        this.departmentManager = departmentManager;
    }

    /**
     * @return the postManager
     */
    public PostManager getPostManager()
    {
        return postManager;
    }

    /**
     * @param postManager
     *            the postManager to set
     */
    public void setPostManager(PostManager postManager)
    {
        this.postManager = postManager;
    }
}
