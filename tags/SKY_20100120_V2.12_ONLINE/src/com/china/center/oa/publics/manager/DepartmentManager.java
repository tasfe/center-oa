/**
 * File Name: RoleManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager;


import net.sourceforge.sannotations.annotation.Bean;

import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.china.center.common.MYException;
import com.china.center.oa.publics.User;
import com.china.center.oa.publics.bean.DepartmentBean;
import com.china.center.oa.publics.dao.DepartmentDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.tools.JudgeTools;


/**
 * RoleManager
 * 
 * @author zhuzhu
 * @version 2008-11-15
 * @see DepartmentManager
 * @since 1.0
 */
@Bean(name = "departmentManager")
public class DepartmentManager
{
    private DepartmentDAO departmentDAO = null;

    private StafferDAO stafferDAO = null;

    /**
     * default constructor
     */
    public DepartmentManager()
    {}

    /**
     * addBean
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public boolean addBean(User user, DepartmentBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        checkAddBean(bean);

        departmentDAO.saveEntityBean(bean);

        return true;
    }

    /**
     * ���²���
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public boolean updateBean(User user, DepartmentBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        checkUpdateBean(bean);

        departmentDAO.updateEntityBean(bean);

        return true;
    }

    /**
     * delBean
     * 
     * @param user
     * @param stafferId
     * @return
     * @throws MYException
     */
    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public boolean delBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        checkDelBean(id);

        departmentDAO.deleteEntityBean(id);

        return true;
    }

    /**
     * @param bean
     * @throws MYException
     */
    private void checkAddBean(DepartmentBean bean)
        throws MYException
    {
        if (departmentDAO.countByUnique(bean.getName()) > 0)
        {
            throw new MYException("��������[%s]�Ѿ�����", bean.getName());
        }
    }

    private void checkUpdateBean(DepartmentBean bean)
        throws MYException
    {
        DepartmentBean dep = departmentDAO.find(bean.getId());

        if (dep == null)
        {
            throw new MYException("����[%s]������", bean.getName());
        }

        if ( !dep.getName().equals(bean.getName()))
        {
            if (departmentDAO.countByUnique(bean.getName().trim()) > 0)
            {
                throw new MYException("��������[%s]�Ѿ�����", bean.getName());
            }
        }
    }

    /**
     * @param bean
     * @throws MYException
     */
    private void checkDelBean(String id)
        throws MYException
    {
        // ������Ա����user
        if (stafferDAO.countByDepartmentId(id) > 0)
        {
            throw new MYException("���ű���Ա���޷�ɾ��");
        }
    }

    /**
     * @return the departmentDAO
     */
    public DepartmentDAO getDepartmentDAO()
    {
        return departmentDAO;
    }

    /**
     * @param departmentDAO
     *            the departmentDAO to set
     */
    public void setDepartmentDAO(DepartmentDAO departmentDAO)
    {
        this.departmentDAO = departmentDAO;
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

}
