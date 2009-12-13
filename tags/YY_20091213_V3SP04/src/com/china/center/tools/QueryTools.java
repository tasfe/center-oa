/**
 * File Name: QueryTools.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-10-2<br>
 * Grant: open source to everybody
 */
package com.china.center.tools;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.china.center.common.ConditionParse;
import com.china.center.common.PageSeparateTools;
import com.china.center.jdbc.inter.DAO;
import com.china.center.jdbc.util.PageSeparate;


/**
 * QueryTools
 * 
 * @author zhuzhu
 * @version 2008-10-2
 * @see
 * @since
 */
public abstract class QueryTools
{
    /**
     * ���ü����ҳ
     * 
     * @param request
     */
    public static void setMemoryQuery(HttpServletRequest request)
    {
        request.setAttribute(PageSeparateTools.TURN, 1);
    }

    public static void setForwardQuery(HttpServletRequest request)
    {
        request.setAttribute(PageSeparateTools.FORWARD, 1);
    }

    public static void setJustQuery(HttpServletRequest request)
    {
        request.setAttribute(PageSeparateTools.JUST, 1);
    }

    /**
     * ����ҳ�����
     * 
     * @param request
     * @param key
     * @param value
     */
    public static void setParMapAttribute(HttpServletRequest request, String key, Object value)
    {
        request.setAttribute(PageSeparateTools.PAREXT + key, value);
    }

    /**
     * �����Ĳ�ѯ����
     * 
     * @param request
     *            ����
     * @param list
     *            ��Ž�����б�
     * @param condtion
     *            ����
     * @param dao
     *            ʹ�õ�dao
     * @param pagesize
     *            ��ȡ������
     */
    public static void commonQueryVO(String key, HttpServletRequest request, List list,
                                     ConditionParse condtion, DAO dao, int... pagesize)
    {
        int size = 10;

        if (pagesize == null || pagesize.length == 0)
        {
            size = 10;
        }
        else
        {
            size = pagesize[0];
        }

        list.addAll(commonQueryVOInner(key, request, condtion, dao, size));
    }

    /**
     * �����Ĳ�ѯ����
     * 
     * @param request
     *            ����
     * @param list
     *            ��Ž�����б�
     * @param condtion
     *            ����
     * @param dao
     *            ʹ�õ�dao
     * @param pagesize
     *            ��ȡ������
     */
    private static List<?> commonQueryVOInner(String key, HttpServletRequest request,
                                              ConditionParse condtion, DAO dao, int pagesize)
    {
        int size = pagesize;

        if (PageSeparateTools.isFirstLoad(request))
        {
            int total = dao.countVOBycondition(condtion.toString());

            PageSeparate page = new PageSeparate(total, size);

            PageSeparateTools.initPageSeparate(condtion, page, request, key);
        }
        else if (PageSeparateTools.isMemory(request))
        {
            int total = dao.countVOBycondition(PageSeparateTools.getCondition(request, key).toString());

            PageSeparateTools.processMemory(total, request, key);
        }
        else
        {
            PageSeparateTools.processSeparate(request, key);
        }

        return dao.queryEntityVOsBycondition(PageSeparateTools.getCondition(request, key),
            PageSeparateTools.getPageSeparate(request, key));
    }

    public static void commonQueryBean(String key, HttpServletRequest request, List list,
                                       ConditionParse condtion, DAO dao, int... pagesize)
    {
        int size = 10;

        if (pagesize == null || pagesize.length == 0)
        {
            size = 10;
        }
        else
        {
            size = pagesize[0];
        }

        list.addAll(commonQueryBeanInner(key, request, condtion, dao, size));
    }

    private static List<?> commonQueryBeanInner(String key, HttpServletRequest request,
                                                ConditionParse condtion, DAO dao, int pagesize)
    {
        int size = pagesize;

        if (PageSeparateTools.isFirstLoad(request))
        {
            int total = dao.countBycondition(condtion.toString());

            PageSeparate page = new PageSeparate(total, size);

            PageSeparateTools.initPageSeparate(condtion, page, request, key);
        }
        else if (PageSeparateTools.isMemory(request))
        {
            int total = dao.countBycondition(PageSeparateTools.getCondition(request, key).toString());

            PageSeparateTools.processMemory(total, request, key);
        }
        else
        {
            PageSeparateTools.processSeparate(request, key);
        }

        return dao.queryEntityBeansBycondition(PageSeparateTools.getCondition(request, key),
            PageSeparateTools.getPageSeparate(request, key));
    }
}
