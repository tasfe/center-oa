/**
 * File Name: PageSeparateTools.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-3-4<br>
 * Grant: open source to everybody
 */
package com.china.center.common;


import javax.servlet.http.HttpServletRequest;

import com.china.center.jdbc.util.PageSeparate;
import com.china.center.tools.StringTools;


/**
 * ���ݿ��ҳ����
 * 
 * @author zhuzhu
 * @version 2008-3-4
 * @see
 * @since
 */
public abstract class PageSeparateTools extends AbstractPage
{
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
        initPageSeparate(condition, page, request, key, isFirstLoad(request, key));
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

            request.getSession().setAttribute("TOTAL", page.getRowCount());
        }
        else
        {
            processSeparate(request, key);
        }
    }

    /**
     * �Ƿ��һ�μ���
     * 
     * @param request
     * @return
     */
    public static boolean isFirstLoad(HttpServletRequest request)
    {
        String load = request.getParameter(LOAD);

        // ��һ��action����һ��actionʹ��forward
        Object oo = request.getAttribute(FORWARD);

        String forward = null;

        if (oo != null)
        {
            forward = oo.toString();
        }

        // �߱�������
        String page = request.getParameter(PAGE);

        String memory = request.getParameter(MEMORY);

        Object o1 = request.getAttribute(MEMORY);

        if (o1 != null)
        {
            return false;
        }

        if ( !StringTools.isNullOrNone(page) || !StringTools.isNullOrNone(memory))
        {
            return false;
        }

        return !StringTools.isNullOrNone(load) || !StringTools.isNullOrNone(forward);
    }

    /**
     * �Ƿ��һ�μ���
     * 
     * @param request
     * @return
     */
    public static boolean isFirstLoad(HttpServletRequest request, String key)
    {
        // �߱����������
        String page = request.getParameter(PAGE);

        if ( !StringTools.isNullOrNone(page))
        {
            return false;
        }

        String memory = request.getParameter(MEMORY);

        // ����Ǽ��䷽ʽ�Ļ�,û�г�ʼ��Ҳ��Ϊ�ǵ�һ�ν���
        if ( !StringTools.isNullOrNone(memory))
        {
            if (PageSeparateTools.getPageSeparate(request, key) == null)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        // һ������ʹ��firstLoad ����load
        String load = request.getParameter(LOAD);

        // ��һ��action����һ��actionʹ��forward
        Object oo = request.getAttribute(FORWARD);

        String forward = null;

        if (oo != null)
        {
            forward = oo.toString();
        }

        return !StringTools.isNullOrNone(load) || !StringTools.isNullOrNone(forward);
    }

    /**
     * �Ƿ����
     * 
     * @param request
     * @return
     */
    public static boolean isMemory(HttpServletRequest request)
    {
        String memory = request.getParameter(MEMORY);

        Object o1 = request.getAttribute(MEMORY);

        if (o1 != null)
        {
            return true;
        }

        o1 = request.getAttribute(MEMORY);

        if (o1 != null)
        {
            return true;
        }

        if ( !StringTools.isNullOrNone(memory))
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
}
