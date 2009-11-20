/**
 * File Name: DBOprTrigger.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-2-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.trigger;


import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.china.center.common.MYException;
import com.china.center.oa.constant.SysConfigConstant;
import com.china.center.oa.customer.dao.CustomerDAO;
import com.china.center.oa.examine.dao.ExamineDAO;
import com.china.center.oa.plan.dao.PlanDAO;
import com.china.center.oa.publics.dao.LocationVSCityDAO;
import com.china.center.oa.publics.dao.ParameterDAO;
import com.china.center.oa.publics.vs.LocationVSCityBean;
import com.china.center.oa.util.CenterServlet;
import com.china.center.tools.FileTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * ���ݿⱸ�ݵ���
 * 
 * @author zhuzhu
 * @version 2008-2-17
 * @see
 * @since
 */
public class DBOprTrigger
{
    private final Log _logger = LogFactory.getLog(getClass());

    private final Log logger1 = LogFactory.getLog("sec");
    
    private final Log triggerLog = LogFactory.getLog("trigger");

    private CustomerDAO customerDAO = null;

    private ParameterDAO parameterDAO = null;

    private ExamineDAO examineDAO = null;

    private PlanDAO planDAO = null;

    private LocationVSCityDAO locationVSCityDAO = null;

    /**
     * �ű�·��
     */
    private String script = "";

    /**
     * ����·��
     */
    private String db_bak = "";

    /**
     * mysql�İ�װ·��
     */
    private String mysql_root = "";

    /**
     * �û�
     */
    private String user = "";

    /**
     * ����
     */
    private String password = "";

    /**
     * ���ݿ�����
     */
    private String db_name = "";

    /**
     * default constructor
     */
    public DBOprTrigger()
    {}

    /**
     * ÿ6Сʱ����һ�����ݿ�
     */
    public void bakDB()
    {}

    /**
     * ÿ�춨ʱɾ����ʱ��ͼƬ
     */
    public void delTempPic()
    {
        String root = CenterServlet.ROOTPATH;

        if (StringTools.isNullOrNone(root))
        {
            return;
        }

        root = root + "temp/" + TimeTools.getSpecialDateString(0, "yyyy/MM");

        try
        {
            FileTools.delete(root);

            logger1.info("ɾ����ʱ���ݳɹ�:" + FileTools.formatPath(root));
        }
        catch (IOException e)
        {
            _logger.error(e, e);
        }
    }

    /**
     * ȫ��ͬ���ͻ�״̬<br>
     * һ���гɽ������Ͽͻ����նˣ�
     */
    @Transactional(rollbackFor = {MYException.class})
    public void autoUpdateCustomerStatus()
    {
        try
        {
            int count = customerDAO.autoUpdateCustomerStatus();

            logger1.info("ͬ���ͻ���״̬���¾������и�����" + count + "���ͻ����¾�����״̬");
        }
        catch (Exception e)
        {
            _logger.error(e, e);
        }

        try
        {
            // һ���гɽ������Ͽͻ�
            int count = customerDAO.updayeCustomerNewTypeInTer();

            logger1.info("�ն���һ���гɽ������Ͽͻ��и�����" + count + "���ͻ����¾�����״̬");
        }
        catch (Exception e)
        {
            _logger.error(e, e);
        }

        try
        {
            int count = planDAO.synchronizationPlanAndItemStatus();

            logger1.info("ͬ���ƻ��Ϳ������״̬�и�����" + count + "������״̬");
        }
        catch (Exception e)
        {
            _logger.error(e, e);
        }
    }

    /**
     * 03-01ִ��<br>
     * һ��һ�ȵ�ͬ����չ�û�������״̬<br>
     * ���¾���ȵĿ���
     * 
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public void synCustomerNewTypeYear()
        throws MYException
    {
        String dataOfYear = parameterDAO.getString(SysConfigConstant.DATE_OF_YEAR);

        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);

        String begin = (year - 1) + "-" + dataOfYear;

        String end = year + "-" + dataOfYear;

        try
        {
            int count = customerDAO.synCustomerNewTypeYear(begin, end);

            triggerLog.info("һ��һ�ȵ�ͬ����չ�û�������״̬������" + count + "������״̬");
        }
        catch (Exception e)
        {
            _logger.error(e, e);

            throw new MYException("ͬ��ʧ��");
        }
    }

    /**
     * 04-01ִ��<br>
     * һ��һ�ȵĸ��¿���״̬<br>
     * ������ȵĿ���
     * 
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public void updateExamineStatusYear()
        throws MYException
    {
        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);

        try
        {
            int count = examineDAO.updateExamineStatusToEnd(year - 1);

            triggerLog.info("һ��һ�ȵĸ��¿���״̬������" + count + "������״̬");
        }
        catch (Exception e)
        {
            _logger.error(e, e);

            throw new MYException("����ʧ��");
        }
    }

    /**
     * ȫ��ͬ���ͻ�״̬
     */
    @Transactional(rollbackFor = {MYException.class})
    public void synchronizationAllCustomerLocation()
        throws MYException
    {
        try
        {
            List<LocationVSCityBean> list = locationVSCityDAO.listEntityBeans();

            customerDAO.initCustomerLocation();

            for (LocationVSCityBean locationVSCityBean : list)
            {
                customerDAO.updateCustomerLocationByCity(locationVSCityBean.getCityId(),
                    locationVSCityBean.getLocationId());
            }
        }
        catch (Exception e)
        {
            _logger.error(e, e);

            throw new MYException("ͬ��ʧ��");
        }
    }

    /**
     * @return the script
     */
    public String getScript()
    {
        return script;
    }

    /**
     * @return the db_bak
     */
    public String getDb_bak()
    {
        return db_bak;
    }

    /**
     * @return the mysql_root
     */
    public String getMysql_root()
    {
        return mysql_root;
    }

    /**
     * @return the user
     */
    public String getUser()
    {
        return user;
    }

    /**
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * @return the db_name
     */
    public String getDb_name()
    {
        return db_name;
    }

    /**
     * @param script
     *            the script to set
     */
    public void setScript(String script)
    {
        this.script = script;
    }

    /**
     * @param db_bak
     *            the db_bak to set
     */
    public void setDb_bak(String db_bak)
    {
        this.db_bak = db_bak;
    }

    /**
     * @param mysql_root
     *            the mysql_root to set
     */
    public void setMysql_root(String mysql_root)
    {
        this.mysql_root = mysql_root;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(String user)
    {
        this.user = user;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * @param db_name
     *            the db_name to set
     */
    public void setDb_name(String db_name)
    {
        this.db_name = db_name;
    }

    /**
     * @return the customerDAO
     */
    public CustomerDAO getCustomerDAO()
    {
        return customerDAO;
    }

    /**
     * @param customerDAO
     *            the customerDAO to set
     */
    public void setCustomerDAO(CustomerDAO customerDAO)
    {
        this.customerDAO = customerDAO;
    }

    /**
     * @return the locationVSCityDAO
     */
    public LocationVSCityDAO getLocationVSCityDAO()
    {
        return locationVSCityDAO;
    }

    /**
     * @param locationVSCityDAO
     *            the locationVSCityDAO to set
     */
    public void setLocationVSCityDAO(LocationVSCityDAO locationVSCityDAO)
    {
        this.locationVSCityDAO = locationVSCityDAO;
    }

    /**
     * @return the planDAO
     */
    public PlanDAO getPlanDAO()
    {
        return planDAO;
    }

    /**
     * @param planDAO
     *            the planDAO to set
     */
    public void setPlanDAO(PlanDAO planDAO)
    {
        this.planDAO = planDAO;
    }

    /**
     * @return the parameterDAO
     */
    public ParameterDAO getParameterDAO()
    {
        return parameterDAO;
    }

    /**
     * @param parameterDAO
     *            the parameterDAO to set
     */
    public void setParameterDAO(ParameterDAO parameterDAO)
    {
        this.parameterDAO = parameterDAO;
    }

    /**
     * @return the examineDAO
     */
    public ExamineDAO getExamineDAO()
    {
        return examineDAO;
    }

    /**
     * @param examineDAO
     *            the examineDAO to set
     */
    public void setExamineDAO(ExamineDAO examineDAO)
    {
        this.examineDAO = examineDAO;
    }
}
