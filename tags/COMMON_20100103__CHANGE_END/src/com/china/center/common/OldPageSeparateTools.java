/**
 * File Name: PageSeparateTools.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-3-4<br>
 * Grant: open source to everybody
 */
package com.china.center.common;


import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.china.center.jdbc.util.PageSeparate;
import com.china.center.tools.CommonTools;
import com.china.center.tools.StringTools;


/**
 * ���ݿ��ҳ����
 * 
 * @author ZHUZHU
 * @version 2008-3-4
 * @see
 * @since
 */
public class OldPageSeparateTools
{
    public static final String PAREXT = "parExt_";

    public static final String JUST = "just";

    public static final String FORWARD = "forward";

    public static final String TURN = "turn";

    public static final String MEMORY = "memory";

    public static final String PAGE = "page";

    public static final String PARAMETER_MAP = "pmap";

    public static final String PAGE_ATTRIBUTE_NAME = "A_page";

    public static final String CONDITION_ATTRIBUTE_NAME = "A_condtion";

    /**
     * ��ʼ����ҳ
     * 
     * @param condtion
     * @param page
     * @param request
     */
    public static void initPageSeparate(ConditionParse condition, PageSeparate page,
                                        HttpServletRequest request, String key)
    {
        initPageSeparate(condition, page, request, key, isFirstLoad(request));
    }

    /**
     * ��ʼ����ҳ
     * 
     * @param condtion
     * @param page
     * @param request
     */
    public static void initPageSeparate(ConditionParse condition, PageSeparate page,
                                        HttpServletRequest request, String key, boolean isFirst)
    {
        if (isFirst)
        {
            request.getSession().setAttribute(getPageAttributeNameInSession(request, key), page);

            request.getSession().setAttribute(PAGE_ATTRIBUTE_NAME, page);

            initParameterMap(request, key);

            request.getSession().setAttribute(getConditionAttributeNameInSession(request, key),
                condition);

            request.getSession().setAttribute(CONDITION_ATTRIBUTE_NAME, condition);

            request.setAttribute("next", page.hasNextPage());

            request.setAttribute("pre", page.hasPrevPage());

            request.getSession().setAttribute("TATOL", page.getRowCount());

            request.getSession().setAttribute("TOTAL", page.getRowCount());
        }
        else
        {
            processSeparate(request, key);
        }
    }

    /**
     * initParameterMap
     * 
     * @param request
     */
    private static void initParameterMap(HttpServletRequest request, String key)
    {
        Map<String, String> parameterMap = CommonTools.saveParamersToMap(request);

        request.getSession().setAttribute(key + "_" + PARAMETER_MAP, parameterMap);

        request.getSession().setAttribute(PARAMETER_MAP, parameterMap);

        Enumeration enums = request.getAttributeNames();

        while (enums.hasMoreElements())
        {
            String name = (String)enums.nextElement();

            if (name.startsWith(PAREXT))
            {
                Object ox = request.getAttribute(name);

                if (ox != null)
                {
                    parameterMap.put(name.substring(PAREXT.length()), ox.toString());
                }
            }
        }
    }

    private static void updateParameterMap(HttpServletRequest request, String key)
    {
        Map parameterMap = (Map)request.getSession().getAttribute(key + "_" + PARAMETER_MAP);

        if (parameterMap == null)
        {
            parameterMap = (Map)request.getSession().getAttribute(PARAMETER_MAP);
        }

        request.getSession().setAttribute(PARAMETER_MAP, parameterMap);
    }

    /**
     * �Ƿ��һ�μ���
     * 
     * @param request
     * @return
     */
    public static boolean isFirstLoad(HttpServletRequest request)
    {
        // һ������ʹ��firstLoad ����load
        String firstLoad = request.getParameter("firstLoad");

        String load = request.getParameter("load");

        // ��һ��action����һ��actionʹ��forward
        Object oo = request.getAttribute(FORWARD);

        String forward = null;

        if (oo != null)
        {
            forward = oo.toString();
        }

        // �߱�������
        String page = request.getParameter(PAGE);

        String turn = request.getParameter(TURN);

        String memory = request.getParameter(MEMORY);

        Object o1 = request.getAttribute(TURN);

        if (o1 != null)
        {
            return false;
        }

        o1 = request.getAttribute(MEMORY);

        if (o1 != null)
        {
            return false;
        }

        if ( !StringTools.isNullOrNone(page) || !StringTools.isNullOrNone(turn)
            || !StringTools.isNullOrNone(memory))
        {
            return false;
        }

        return !StringTools.isNullOrNone(firstLoad) || !StringTools.isNullOrNone(load)
               || !StringTools.isNullOrNone(forward);
    }

    /**
     * �Ƿ����
     * 
     * @param request
     * @return
     */
    public static boolean isMemory(HttpServletRequest request)
    {
        String turn = request.getParameter(TURN);

        String memory = request.getParameter(MEMORY);

        Object o1 = request.getAttribute(TURN);

        if (o1 != null)
        {
            return true;
        }

        o1 = request.getAttribute(MEMORY);

        if (o1 != null)
        {
            return true;
        }

        if ( !StringTools.isNullOrNone(turn) || !StringTools.isNullOrNone(memory))
        {
            return true;
        }

        return false;
    }

    /**
     * �Ƿ����
     * 
     * @param request
     * @return
     */
    public static boolean isJust(HttpServletRequest request)
    {
        String turn = request.getParameter(JUST);

        String memory = request.getParameter(JUST);

        Object o1 = request.getAttribute(JUST);

        if (o1 != null)
        {
            return true;
        }

        o1 = request.getAttribute(JUST);

        if (o1 != null)
        {
            return true;
        }

        if ( !StringTools.isNullOrNone(turn) || !StringTools.isNullOrNone(memory))
        {
            return true;
        }

        return false;
    }

    /**
     * ��ʼ�������ҳ
     * 
     * @param condtion
     * @param page
     * @param request
     */
    public static void processMemory(int total, HttpServletRequest request, String key)
    {
        updateParameterMap(request, key);

        PageSeparate page = getPageSeparate(request, key);

        int nowPage = page.getNowPage();

        int pageSize = page.getPageSize();

        page.reset(total, pageSize);

        page.setNowPage(nowPage);
    }

    /**
     * ����just
     * 
     * @param condtion
     * @param page
     * @param request
     */
    public static void processJust(HttpServletRequest request, String key)
    {
        updateParameterMap(request, key);
    }

    /**
     * �����ҳ
     * 
     * @param request
     * @return
     */
    public static boolean processSeparate(HttpServletRequest request, String key)
    {
        updateParameterMap(request, key);

        PageSeparate pageS = (PageSeparate)request.getSession().getAttribute(
            getPageAttributeNameInSession(request, key));

        request.getSession().setAttribute(PAGE_ATTRIBUTE_NAME, pageS);

        if (pageS == null)
        {
            return false;
        }

        String page = request.getParameter(PAGE);

        if ("next".equals(page))
        {
            pageS.nextPage();
            request.setAttribute("next", pageS.hasNextPage());
            request.setAttribute("pre", pageS.hasPrevPage());
            return true;
        }
        else if ("previous".equals(page))
        {
            pageS.prevPage();

            request.setAttribute("next", pageS.hasNextPage());
            request.setAttribute("pre", pageS.hasPrevPage());
            return true;
        }

        return false;
    }

    public static String getPageAttributeNameInSession(HttpServletRequest request, String key)
    {
        if (StringTools.isNullOrNone(key))
        {
            return PAGE_ATTRIBUTE_NAME;
        }

        return PAGE_ATTRIBUTE_NAME + "_" + key;
    }

    public static String getConditionAttributeNameInSession(HttpServletRequest request, String key)
    {
        if (StringTools.isNullOrNone(key))
        {
            return CONDITION_ATTRIBUTE_NAME;
        }

        return CONDITION_ATTRIBUTE_NAME + "_" + key;
    }

    public static PageSeparate getPageSeparate(HttpServletRequest request, String key)
    {
        PageSeparate result = (PageSeparate)request.getSession().getAttribute(
            getPageAttributeNameInSession(request, key));

        if (result == null)
        {
            result = (PageSeparate)request.getSession().getAttribute(PAGE_ATTRIBUTE_NAME);

            if (result != null)
            {
                request.getSession().setAttribute(getPageAttributeNameInSession(request, key),
                    result);
            }
        }

        request.getSession().setAttribute(PAGE_ATTRIBUTE_NAME, result);

        return result;
    }

    public static ConditionParse getCondition(HttpServletRequest request, String key)
    {
        ConditionParse result = (ConditionParse)request.getSession().getAttribute(
            getConditionAttributeNameInSession(request, key));

        if (result == null)
        {
            result = (ConditionParse)request.getSession().getAttribute(CONDITION_ATTRIBUTE_NAME);

            if (result != null)
            {
                request.getSession().setAttribute(
                    getConditionAttributeNameInSession(request, key), result);
            }
        }

        request.getSession().setAttribute(CONDITION_ATTRIBUTE_NAME, result);

        return result;
    }

}
