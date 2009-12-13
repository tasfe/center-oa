/*
 * File Name: OutBeanHelper.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-8-14
 * Grant: open source to everybody
 */
package com.china.centet.yongyin.bean.helper;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.china.center.eltools.ElTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.centet.yongyin.bean.BaseBean;
import com.china.centet.yongyin.bean.OutBean;


/**
 * @author zhuzhu
 * @version 2007-8-14
 * @see
 * @since
 */
public abstract class OutBeanHelper
{
    public static String createTable(List<BaseBean> list, double tatol)
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append("<table width='100%' border='0' cellspacing='1'>");
        buffer.append("<tr align='center' class='content0'>");
        buffer.append("<td width='20%' align='center'>Ʒ��</td>");
        buffer.append("<td width='5%' align='center'>��λ</td>");
        buffer.append("<td width='10%' align='center'>����</td>");
        buffer.append("<td width='15%' align='center'>����</td>");
        buffer.append("<td width='20%' align='left'>���(�ܼ�:<span id='total'>"
                      + ElTools.formatNum(tatol) + "</span>)</td>");
        buffer.append("<td width='25%' align='center'>�ɱ�</td></tr>");

        int index = 0;
        String cls = null;
        for (BaseBean bean : list)
        {
            if (index % 2 == 0)
            {
                cls = "content1";
            }
            else
            {
                cls = "content2";
            }

            buffer.append("<tr class='" + cls + "'>");

            buffer.append("<td width='20%' align='center'>" + bean.getProductName() + "</td>");
            buffer.append("<td width='5%' align='center'>" + bean.getUnit() + "</td>");
            buffer.append("<td width='10%' align='center'>" + bean.getAmount() + "</td>");
            buffer.append(" <td width='15%' align='center'>" + ElTools.formatNum(bean.getPrice())
                          + "</td>");
            buffer.append("<td width='15%' align='center'>" + ElTools.formatNum(bean.getValue())
                          + "</td>");
            buffer.append("<td width='25%' align='center'>"
                          + StringTools.print(bean.getDescription()) + "</td>");
            index++ ;
        }

        buffer.append("</table>");

        return buffer.toString();
    }

    public static void getBean(ResultSet rst, OutBean outBean)
        throws SQLException
    {
        outBean.setId(rst.getString("id"));
        outBean.setFullId(rst.getString("fullId"));
        outBean.setStafferName(rst.getString("stafferName"));
        outBean.setCustomerName(rst.getString("customerName"));
        outBean.setStatus(rst.getInt("status"));
        outBean.setOutType(rst.getInt("outType"));

        outBean.setOutTime(TimeTools.getStringBySqlDate(rst.getDate("outTime")));

        outBean.setDepartment(rst.getString("department"));
        outBean.setDescription(rst.getString("description"));
        outBean.setTotal(rst.getDouble("total"));
        outBean.setPhone(rst.getString("phone"));
        outBean.setConnector(rst.getString("connector"));
        outBean.setCustomerId(rst.getString("customerId"));
        outBean.setType(rst.getInt("type"));
        outBean.setMarks(rst.getInt("mark"));
        outBean.setPay(rst.getInt("pay"));
        outBean.setChecks(rst.getString("checks"));

        String kk = rst.getString("reday");

        outBean.setReday(MathTools.parseInt(kk));

        outBean.setRedate(rst.getString("redate"));

        outBean.setTempType(rst.getInt("tempType"));

        outBean.setHadPay(rst.getString("hadPay"));
    }

    public static String getStatus(int i)
    {
        return getStatus(i, true);
    }

    public static String getStatus(int i, boolean color)
    {
        if (i == 0)
        {
            return "����";
        }

        if (i == 1)
        {
            return "�ύ";
        }

        if (i == 2)
        {
            if (color)
            {
                return "<font color=red>����</font>";
            }
            else
            {
                return "����";
            }
        }

        if (i == 3)
        {
            return "����";
        }

        if (i == 4)
        {
            return "����";
        }

        if (i == 6)
        {
            return "����ͨ��";
        }

        if (i == 7)
        {
            return "����ͨ��";
        }

        return "";
    }
}
