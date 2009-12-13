/**
 * File Name: LocationAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.action;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.china.center.common.ConditionParse;
import com.china.center.common.KeyConstant;
import com.china.center.common.MYException;
import com.china.center.oa.facade.PublicFacade;
import com.china.center.oa.helper.Helper;
import com.china.center.oa.publics.User;
import com.china.center.oa.publics.bean.CityBean;
import com.china.center.oa.publics.bean.LocationBean;
import com.china.center.oa.publics.bean.ProvinceBean;
import com.china.center.oa.publics.dao.CityDAO;
import com.china.center.oa.publics.dao.LocationDAO;
import com.china.center.oa.publics.dao.ProvinceDAO;
import com.china.center.oa.publics.manager.LocationManager;
import com.china.center.oa.publics.vo.LocationVO;
import com.china.center.oa.publics.vo.LocationVSCityVO;
import com.china.center.oa.publics.vs.LocationVSCityBean;
import com.china.center.tools.ActionTools;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.JSONTools;


public class LocationAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private LocationDAO locationDAO = null;

    private LocationManager locationManager = null;

    private PublicFacade publicFacade = null;

    private CityDAO cityDAO = null;

    private ProvinceDAO provinceDAO = null;

    /**
     * default constructor
     */
    public LocationAction()
    {}

    public ActionForward queryLocation(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        String jsonstr = ActionTools.queryBeanByJSONAndToString("queryLocation", request,
            condtion, this.locationDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * addLocation
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addLocation(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        LocationBean bean = new LocationBean();

        try
        {
            BeanUtil.getBean(bean, request);

            User user = Helper.getUser(request);

            publicFacade.addLocationBean(user, bean);

            request.setAttribute(KeyConstant.MESSAGE, "�ɹ����ӷֹ�˾:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "����ʧ��:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryLocation");
    }

    /**
     * addLocationVSCity
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addLocationVSCity(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String locationId = request.getParameter("locationId");

        User user = Helper.getUser(request);

        try
        {
            String[] citys = request.getParameterValues("city");
            
            if (citys == null)
            {
                citys = new String[0];
            }

            List<LocationVSCityBean> list = new ArrayList<LocationVSCityBean>(citys.length);

            for (String cityId : citys)
            {
                LocationVSCityBean vs = new LocationVSCityBean();

                vs.setLocationId(locationId);

                vs.setCityId(cityId);

                list.add(vs);
            }

            publicFacade.addLocationVSCity(user, locationId, list);

            request.setAttribute(KeyConstant.MESSAGE, "�����ɹ�");
        }
        catch (MYException e)
        {
            _logger.warn(e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "����ʧ��:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryLocation");
    }

    /**
     * delLocation
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward delLocation(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        try
        {
            String locationId = request.getParameter("locationId");

            User user = Helper.getUser(request);

            publicFacade.delLocationBean(user, locationId);

            request.setAttribute(KeyConstant.MESSAGE, "�ɹ�ɾ��ָ���ֹ�˾");
        }
        catch (MYException e)
        {
            _logger.warn(e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "ɾ��ʧ��:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryLocation");
    }

    /**
     * findLocation
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findLocation(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String locationId = request.getParameter("locationId");

        LocationVO vo = null;
        try
        {
            vo = locationManager.findVO(locationId);

            if (vo == null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "��ѯʧ�ֹܷ�˾������");

                return mapping.findForward("queryLocation");
            }

            request.setAttribute("bean", vo);
        }
        catch (MYException e)
        {
            _logger.warn(e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "��ѯʧ��:" + e.getMessage());
        }

        String opr = request.getParameter("opr");

        // �޸ķֹ�˾�Ĺ�Ͻ��Χ
        if ("1".equals(opr))
        {
            List<LocationVSCityVO> citys = vo.getCityVOs();

            Map<String, Integer> map = new HashMap<String, Integer>();

            for (LocationVSCityVO locationVSCityVO : citys)
            {
                map.put(locationVSCityVO.getCityId(), 1);
            }

            request.setAttribute("cityMap", map);

            Map<String, List<CityBean>> areaMap = new HashMap<String, List<CityBean>>();

            List<ProvinceBean> allProvinceList = provinceDAO.listEntityBeans();

            List<ProvinceBean> dispalyList = new ArrayList<ProvinceBean>();

            for (ProvinceBean provinceBean : allProvinceList)
            {
                List<CityBean> cityList = cityDAO.queryCanAssignCity(provinceBean.getId(),
                    locationId);

                if (cityList.size() > 0)
                {
                    areaMap.put(provinceBean.getId(), cityList);

                    dispalyList.add(provinceBean);
                }
            }

            request.setAttribute("areaMap", areaMap);

            request.setAttribute("provinceList", dispalyList);

            if (dispalyList.size() == 0)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "��ʱû��������Է���");

                return mapping.findForward("queryLocation");
            }

            return mapping.findForward("oprLocationVSCity");
        }

        return mapping.findForward("detailLocation");
    }

    /**
     * @return the locationDAO
     */
    public LocationDAO getLocationDAO()
    {
        return locationDAO;
    }

    /**
     * @param locationDAO
     *            the locationDAO to set
     */
    public void setLocationDAO(LocationDAO locationDAO)
    {
        this.locationDAO = locationDAO;
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
     * @return the publicFacade
     */
    public PublicFacade getPublicFacade()
    {
        return publicFacade;
    }

    /**
     * @param publicFacade
     *            the publicFacade to set
     */
    public void setPublicFacade(PublicFacade publicFacade)
    {
        this.publicFacade = publicFacade;
    }

    /**
     * @return the cityDAO
     */
    public CityDAO getCityDAO()
    {
        return cityDAO;
    }

    /**
     * @param cityDAO
     *            the cityDAO to set
     */
    public void setCityDAO(CityDAO cityDAO)
    {
        this.cityDAO = cityDAO;
    }

    /**
     * @return the provinceDAO
     */
    public ProvinceDAO getProvinceDAO()
    {
        return provinceDAO;
    }

    /**
     * @param provinceDAO
     *            the provinceDAO to set
     */
    public void setProvinceDAO(ProvinceDAO provinceDAO)
    {
        this.provinceDAO = provinceDAO;
    }
}
