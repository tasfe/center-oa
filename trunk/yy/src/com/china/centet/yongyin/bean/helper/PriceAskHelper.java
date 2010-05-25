/*
 * File Name: OutBeanHelper.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-8-14
 * Grant: open source to everybody
 */
package com.china.centet.yongyin.bean.helper;


import java.util.List;

import com.china.centet.yongyin.bean.Role;
import com.china.centet.yongyin.bean.User;
import com.china.centet.yongyin.constant.PriceConstant;
import com.china.centet.yongyin.vo.PriceAskProviderBeanVO;


/**
 * @author zhuzhu
 * @version 2007-8-14
 * @see
 * @since
 */
public abstract class PriceAskHelper
{
    public static String createTable(List<PriceAskProviderBeanVO> list, User user)
    {
        StringBuffer buffer = new StringBuffer();

        String display = "";

        if (user.getRole() == Role.PRICE)
        {
            display = "����Ա";
        }
        else
        {
            display = "��Ӧ��";
        }

        buffer.append("<table width='100%' border='0' cellspacing='1'>");
        buffer.append("<tr align='center' class='content0'>");
        buffer.append("<td width='30%' align='center'>" + display + "</td>");
        buffer.append("<td width='15%' align='center'>�۸�</td>");
        buffer.append("<td width='5%' align='center'>����</td>");
        buffer.append("<td width='15%' align='center'>��������</td>");
        buffer.append("<td width='20%' align='center'>��ע</td>");
        buffer.append("<td width='30%' align='center'>ʱ��</td>");
        buffer.append("</tr>");

        int index = 0;
        String cls = null;
        for (PriceAskProviderBeanVO bean : list)
        {
            if (index % 2 == 0)
            {
                cls = "content1";
            }
            else
            {
                cls = "content2";
            }

            String str = (bean.getHasAmount() == PriceConstant.HASAMOUNT_OK) ? "����" : "<font color=red>������</font>";

            buffer.append("<tr class='" + cls + "'>");

            String displayName = "";

            if (user.getRole() == Role.PRICE)
            {
                displayName = bean.getUserName();
            }
            else
            {
                displayName = bean.getProviderName();
            }

            buffer.append("<td  align='center'>" + displayName + "</td>");
            buffer.append("<td  align='center'>" + bean.getPrice() + "</td>");
            buffer.append("<td  align='center'>" + bean.getSupportAmount() + "</td>");
            buffer.append("<td  align='center'>" + str + "</td>");

            String des = bean.getDescription().replaceAll("\r\n", "");

            buffer.append("<td  align='center'>" + des + "</td>");

            buffer.append("<td  align='center'>" + bean.getLogTime() + "</td>");

            buffer.append("</tr>");

            index++ ;
        }

        buffer.append("</table>");

        return buffer.toString();
    }
}
