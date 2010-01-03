/**
 * File Name: SequenceTools.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-2-14<br>
 * Grant: open source to everybody
 */
package com.china.center.tools;

/**
 * Ψһ���еĹ�����
 * 
 * @author zhuzhu
 * @version 2008-2-14
 * @see
 * @since
 */
public abstract class SequenceTools
{
    private static long sequence = 0L;

    /**
     * ���ȫ��Ψһ������
     * 
     * @param pre
     * @return
     */
    public synchronized static String getSequence(String pre)
    {
        return pre + TimeTools.now("yyyyMMddHHmmss") + ( ++sequence);
    }

    public synchronized static long getCurrentSequence()
    {
        return ( ++sequence);
    }

    /**
     * ���ȫ��Ψһ������
     * 
     * @param pre
     * @return
     */
    public synchronized static String getSequence()
    {
        return TimeTools.now("yyyyMMddHHmmss") + ( ++sequence);
    }

    /**
     * ���ȫ��Ψһ������
     * 
     * @param pre
     * @return
     */
    public synchronized static String getSequence(int n)
    {
        String s = "";
        for (int i = 0; i < n; i++ )
        {
            s = "0" + s;
        }

        s += ( ++sequence);

        return TimeTools.now("yyyyMMddHHmmss") + s.substring(s.length() - n, s.length());
    }

    /**
     * ���ȫ��Ψһ������
     * 
     * @param pre
     * @return
     */
    public synchronized static String getSequence(String pre, int n)
    {
        return pre + getSequence(n);
    }
}
