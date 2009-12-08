/**
 * File Name: OATools.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2009-12-8<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tools;


import java.util.Calendar;

import com.china.center.tools.TimeTools;


/**
 * OATools
 * 
 * @author ZHUZHU
 * @version 2009-12-8
 * @see OATools
 * @since 1.0
 */
public abstract class OATools
{
    /**
     * ��ò�����ȵĿ�ʼ����
     * 
     * @return
     */
    public static String getFinanceBeginDate()
    {
        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);

        return year + "-03-01";
    }

    /**
     * ��ò�����ȵĽ�������
     * 
     * @return
     */
    public static String getFinanceEndDate()
    {
        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR) + 1;

        int daysOfMonth = TimeTools.getDaysOfMonth(year, 2);

        return year + "-02-" + daysOfMonth;
    }
}
