/**
 * File Name: EnumManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-9<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager;


import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.buffalo.request.RequestContext;
import net.sourceforge.sannotations.annotation.Bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.ex.annotation.Exceptional;

import com.china.center.oa.constant.ExamineConstant;
import com.china.center.oa.constant.StafferConstant;
import com.china.center.oa.publics.User;
import com.china.center.oa.publics.bean.AreaBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.AreaDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.dao.UserDAO;
import com.china.center.oa.publics.vo.UserVO;


/**
 * EnumManager
 * 
 * @author zhuzhu
 * @version 2008-11-9
 * @see CommonManager
 * @since 1.0
 */
@Exceptional
@Bean(name = "commonManager")
public class CommonManager
{
    private AreaDAO areaDAO = null;

    private StafferDAO stafferDAO = null;

    private UserDAO userDAO = null;

    private final Log badLog = LogFactory.getLog("bad");

    /**
     * default constructor
     */
    public CommonManager()
    {}

    public List<AreaBean> queryAreaByParentId(String parentId)
    {
        return areaDAO.queryEntityBeansByFK(parentId);
    }

    public List<StafferBean> queryStafferByLocationId(String locationId)
    {
        return stafferDAO.queryStafferByLocationId(locationId);
    }

    /**
     * ��ѯ�����µ���ҵ��Ա���˵�ְԱ
     * 
     * @param locationId
     * @param type
     * @param attType
     * @return
     */
    public List<StafferBean> queryStafferByLocationIdAndFiter(String locationId, int type,
                                                              int attType)
    {
        List<StafferBean> stafferList = stafferDAO.queryStafferByLocationId(locationId);

        // ���Ƿֹ�˾����
        if (attType == ExamineConstant.EXAMINE_ATTTYPE_LOCATION)
        {
            type = -1;
        }

        for (Iterator iterator = stafferList.iterator(); iterator.hasNext();)
        {
            StafferBean stafferBean = (StafferBean)iterator.next();

            if (type == -1)
            {
                if (stafferBean.getExamType() != StafferConstant.EXAMTYPE_EXPAND
                    && stafferBean.getExamType() != StafferConstant.EXAMTYPE_TERMINAL)
                {
                    iterator.remove();
                }
            }
            else
            {
                if (stafferBean.getExamType() != type)
                {
                    iterator.remove();
                }
            }
        }

        return stafferList;
    }

    /**
     * @param content
     */
    public boolean logD(String content)
    {
        RequestContext context = RequestContext.getContext();

        Map session = context.getSession();

        User user = (User)session.get("user");

        UserVO vo = userDAO.findVO(user.getId());

        if (vo == null)
        {
            return false;
        }

        badLog.info(vo.getStafferName() + " copy:" + content);

        return true;
    }

    /**
     * @return the areaDAO
     */
    public AreaDAO getAreaDAO()
    {
        return areaDAO;
    }

    /**
     * @param areaDAO
     *            the areaDAO to set
     */
    public void setAreaDAO(AreaDAO areaDAO)
    {
        this.areaDAO = areaDAO;
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
