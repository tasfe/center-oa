/**
 * File Name: OrgManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sourceforge.sannotations.annotation.Bean;

import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.china.center.annosql.constant.AnoConstant;
import com.china.center.common.MYException;
import com.china.center.oa.constant.PublicConstant;
import com.china.center.oa.publics.User;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.CommonDAO2;
import com.china.center.oa.publics.dao.OrgDAO;
import com.china.center.oa.publics.dao.PrincipalshipDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.vs.OrgBean;
import com.china.center.oa.publics.wrap.StafferOrgWrap;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;


/**
 * OrgManager
 * 
 * @author zhuzhu
 * @version 2008-11-17
 * @see OrgManager
 * @since 1.0
 */
@Exceptional
@Bean(name = "orgManager")
public class OrgManager
{
    private OrgDAO orgDAO = null;

    private PrincipalshipDAO principalshipDAO = null;

    private StafferDAO stafferDAO = null;

    private CommonDAO2 commonDAO2 = null;

    /**
     * default constructor
     */
    public OrgManager()
    {}

    /**
     * ��ѯ��һ���ĸ�λ
     * 
     * @param id
     * @return
     * @throws MYException
     */
    public List<PrincipalshipBean> querySubPrincipalship(String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(id);

        PrincipalshipBean bean = principalshipDAO.find(id);

        if (bean == null)
        {
            throw new MYException("��λ������");
        }

        List<PrincipalshipBean> result = new ArrayList<PrincipalshipBean>();

        result.add(bean);

        diguiPrin(bean, result);

        return result;
    }

    private void diguiPrin(PrincipalshipBean parentBean, List<PrincipalshipBean> result)
    {
        List<PrincipalshipBean> list = principalshipDAO.querySubPrincipalship(parentBean.getId());

        if (list.isEmpty())
        {
            return;
        }

        result.addAll(list);

        for (PrincipalshipBean principalshipBean : list)
        {
            diguiPrin(principalshipBean, result);
        }
    }

    @Transactional(rollbackFor = {MYException.class})
    public boolean addBean(User user, PrincipalshipBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        checkAddBean(bean);

        bean.setId(commonDAO2.getSquenceString());

        principalshipDAO.saveEntityBean(bean);

        List<OrgBean> parents = bean.getParentOrgList();

        for (OrgBean orgBean : parents)
        {
            orgBean.setSubId(bean.getId());

            if (orgDAO.countByUnique(orgBean.getSubId(), orgBean.getParentId()) == 0)
            {
                orgDAO.saveEntityBean(orgBean);
            }
        }

        return true;
    }

    @Transactional(rollbackFor = {MYException.class})
    public boolean updateBean(User user, PrincipalshipBean bean, boolean modfiyParent)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        checkUpdateBean(bean, modfiyParent);

        principalshipDAO.updateEntityBean(bean);

        if (modfiyParent)
        {
            orgDAO.deleteEntityBeansByFK(bean.getId(), AnoConstant.FK_FIRST);

            List<OrgBean> parents = bean.getParentOrgList();

            for (OrgBean orgBean : parents)
            {
                orgBean.setSubId(bean.getId());

                if (orgDAO.countByUnique(orgBean.getSubId(), orgBean.getParentId()) == 0)
                {
                    orgDAO.saveEntityBean(orgBean);
                }
            }
        }

        return true;
    }

    @Transactional(rollbackFor = {MYException.class})
    public boolean delBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        checkDelBean(id);

        principalshipDAO.deleteEntityBean(id);

        orgDAO.deleteEntityBeansByFK(id, AnoConstant.FK_FIRST);

        return true;
    }

    /**
     * �ݹ��ѯ�������е�ְԱ
     * 
     * @param prinRootId
     * @return
     * @throws MYException
     */
    public List<StafferOrgWrap> queryAllSubStaffer(String prinRootId)
        throws MYException
    {
        List<PrincipalshipBean> plist = this.querySubPrincipalship(prinRootId);

        Set<StafferOrgWrap> wraps = new HashSet<StafferOrgWrap>();

        // ѭ����֯
        for (PrincipalshipBean principalshipBean : plist)
        {
            // ��ѯ��һ����λ
            List<PrincipalshipBean> vos = principalshipDAO.querySubPrincipalship(principalshipBean.getId());

            for (PrincipalshipBean orgBean : vos)
            {
                // ��ְ���µ���Ա
                List<StafferBean> pplist = stafferDAO.queryStafferByPrincipalshipId(orgBean.getId());

                if ( !pplist.isEmpty())
                {
                    for (StafferBean stafferBean2 : pplist)
                    {
                        StafferOrgWrap wrap = new StafferOrgWrap();

                        wrap.setStafferId(stafferBean2.getId());
                        
                        wrap.setId(stafferBean2.getId());

                        wrap.setStafferName(stafferBean2.getName());
                        
                        wrap.setName(stafferBean2.getName());

                        wrap.setPrincipalshipId(orgBean.getId());

                        wrap.setPrincipalshipName(orgBean.getName());

                        wraps.add(wrap);
                    }
                }
            }
        }
        
        List<StafferOrgWrap> list = new ArrayList<StafferOrgWrap>();
        
        for (StafferOrgWrap stafferOrgWrap : wraps)
        {
            list.add(stafferOrgWrap);
        }
        
        wraps.clear();
        
        //sort by name
        Collections.sort(list, new Comparator<StafferOrgWrap>(){

            public int compare(StafferOrgWrap o1, StafferOrgWrap o2)
            {
                return o1.getName().compareTo(o2.getName());
            }});

        return list;
    }

    private void checkDelBean(String id)
        throws MYException
    {
        PrincipalshipBean bean = principalshipDAO.find(id);

        if (bean == null)
        {
            throw new MYException("ְ�񲻴���");
        }

        if (orgDAO.countByFK(id) > 0)
        {
            throw new MYException("ְ���´����¼���֯�ṹ,����ɾ��");
        }
    }

    /**
     * checkAddBean(һ�������Ʋ����ظ�)
     * 
     * @param bean
     * @throws MYException
     */
    private void checkAddBean(PrincipalshipBean bean)
        throws MYException
    {
        List<OrgBean> parents = bean.getParentOrgList();

        if (ListTools.isEmptyOrNull(parents))
        {
            throw new MYException("ְ��[%s]û���ϼ�����", bean.getName());
        }

        for (OrgBean orgBean : parents)
        {
            PrincipalshipBean ship = principalshipDAO.find(orgBean.getParentId());

            if (ship == null)
            {
                throw new MYException("�ϼ�����ְ�񲻴���");
            }

            if (bean.getLevel() <= ship.getLevel())
            {
                throw new MYException("ְ�񼶱��ܵ����ϼ�");
            }

            if (bean.getName().equalsIgnoreCase(ship.getName()))
            {
                throw new MYException("һ�����ϵ�ְ�����Ʋ����ظ�,�����²���");
            }

            checkParentNameDuplicate(ship.getId(), bean.getName());
        }
    }

    /**
     * �ݹ��鸸�ڵ�������Ƿ��ظ�
     */
    private void checkParentNameDuplicate(String itemId, String name)
        throws MYException
    {
        if (String.valueOf(PublicConstant.ORG_ROOT).equals(itemId))
        {
            return;
        }

        List<OrgBean> parentList = orgDAO.queryEntityBeansByFK(itemId, AnoConstant.FK_FIRST);

        for (OrgBean orgBean : parentList)
        {
            PrincipalshipBean pbean = principalshipDAO.find(orgBean.getParentId());

            if (pbean == null)
            {
                throw new MYException("���ݴ���,ְ�񲻴���,�����²���");
            }

            if (pbean.getName().equalsIgnoreCase(name))
            {
                throw new MYException("һ�����ϵ�ְ�����Ʋ����ظ�,�����²���");
            }

            checkParentNameDuplicate(pbean.getId(), name);
        }
    }

    /**
     * �ݹ����ӽڵ�������Ƿ��ظ�
     */
    private void checkSubNameDuplicate(String itemId, String name)
        throws MYException
    {
        List<OrgBean> subList = orgDAO.queryEntityBeansByFK(itemId);

        if (ListTools.isEmptyOrNull(subList))
        {
            return;
        }

        for (OrgBean orgBean : subList)
        {
            PrincipalshipBean pbean = principalshipDAO.find(orgBean.getSubId());

            if (pbean == null)
            {
                throw new MYException("���ݴ���,ְ�񲻴���,�����²���");
            }

            if (pbean.getName().equalsIgnoreCase(name))
            {
                throw new MYException("һ�����ϵ�ְ�����Ʋ����ظ�,�����²���");
            }

            checkSubNameDuplicate(pbean.getId(), name);
        }
    }

    /**
     * checkUpdateBean
     * 
     * @param bean
     * @throws MYException
     */
    private void checkUpdateBean(PrincipalshipBean bean, boolean modfiyParent)
        throws MYException
    {
        PrincipalshipBean old = principalshipDAO.find(bean.getId());

        List<OrgBean> parents = null;

        if (modfiyParent)
        {
            parents = bean.getParentOrgList();
        }
        else
        {
            parents = orgDAO.queryEntityBeansByFK(bean.getId(), AnoConstant.FK_FIRST);
        }

        if (ListTools.isEmptyOrNull(parents))
        {
            throw new MYException("ְ��[%s]û���ϼ�����", bean.getName());
        }

        for (OrgBean orgBean : parents)
        {
            PrincipalshipBean ship = principalshipDAO.find(orgBean.getParentId());

            if (ship == null)
            {
                throw new MYException("�ϼ�����ְ�񲻴���");
            }

            if (bean.getLevel() <= ship.getLevel())
            {
                throw new MYException("ְ�񼶱��ܵ����ϼ�");
            }
        }

        // �޸ļ����ʱ��ע���¼��Ĳ��ܸ����ϼ�
        List<OrgBean> subs = orgDAO.queryEntityBeansByFK(bean.getId());

        for (OrgBean item : subs)
        {
            PrincipalshipBean ship = principalshipDAO.find(item.getSubId());

            if (ship == null)
            {
                throw new MYException("�¼�����ְ�񲻴���");
            }

            if (bean.getLevel() >= ship.getLevel())
            {
                throw new MYException("ְ�񼶱��ܸ����¼�");
            }
        }

        if (modfiyParent)
        {
            for (OrgBean orgBean2 : parents)
            {
                PrincipalshipBean pp = principalshipDAO.find(orgBean2.getParentId());

                if (pp == null)
                {
                    throw new MYException("���ݴ���,ְ�񲻴���,�����²���");
                }

                if (pp.getName().equalsIgnoreCase(bean.getName()))
                {
                    throw new MYException("һ�����ϵ�ְ�����Ʋ����ظ�,�����²���");
                }

                checkParentNameDuplicate(orgBean2.getParentId(), bean.getName());
            }
        }

        // �����ظ�
        if ( !old.getName().equalsIgnoreCase(bean.getName()))
        {
            if (modfiyParent)
            {
                checkSubNameDuplicate(bean.getId(), bean.getName());
            }
            else
            {
                checkParentNameDuplicate(bean.getId(), bean.getName());

                checkSubNameDuplicate(bean.getId(), bean.getName());
            }
        }

    }

    /**
     * @return the orgDAO
     */
    public OrgDAO getOrgDAO()
    {
        return orgDAO;
    }

    /**
     * @param orgDAO
     *            the orgDAO to set
     */
    public void setOrgDAO(OrgDAO orgDAO)
    {
        this.orgDAO = orgDAO;
    }

    /**
     * @return the principalshipDAO
     */
    public PrincipalshipDAO getPrincipalshipDAO()
    {
        return principalshipDAO;
    }

    /**
     * @param principalshipDAO
     *            the principalshipDAO to set
     */
    public void setPrincipalshipDAO(PrincipalshipDAO principalshipDAO)
    {
        this.principalshipDAO = principalshipDAO;
    }

    /**
     * @return the commonDAO2
     */
    public CommonDAO2 getCommonDAO2()
    {
        return commonDAO2;
    }

    /**
     * @param commonDAO2
     *            the commonDAO2 to set
     */
    public void setCommonDAO2(CommonDAO2 commonDAO2)
    {
        this.commonDAO2 = commonDAO2;
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
