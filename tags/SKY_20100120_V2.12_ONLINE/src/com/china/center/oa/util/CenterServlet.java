/*
 * �ļ�����CenterServlet.java
 * ��Ȩ��Copyright by www.center.china
 * ������
 * �����ˣ�zhu
 * ����ʱ�䣺2007-1-11
 */
package com.china.center.oa.util;


import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * <����>
 * 
 * @author zhuzhu
 * @version 2007-1-11
 * @see
 * @since
 */
public class CenterServlet extends HttpServlet
{
    public static String ROOTPATH = "";

    /**
     * ���web�ĸ�·��
     */
    public void init(ServletConfig config)
        throws ServletException
    {
        ROOTPATH = config.getServletContext().getRealPath("/");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {}
}
