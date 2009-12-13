package com.china.center.tools;


import com.china.center.common.MYException;


/**
 * �����жϹ���
 * 
 * @see JudgeTools
 * @since
 */

public class JudgeTools
{
    public final static int JUDGE_COMMON = 0;

    public final static int JUDGE_NUMBER = 1;

    public final static int JUDGE_NUMBER_OR_LETTER = 2;

    private JudgeTools()
    {}

    /**
     * Description: �ж�����Ƿ�Ϊ��(Ϊ���׳��쳣,���򷵻�true)<br>
     * 
     * @param oo
     * @return
     * @throws SPMException
     *             boolean
     */
    public static boolean judgeParameterIsNull(Object... oo)
        throws MYException
    {
        return judgeParameterIsNull(oo, false);
    }

    public static boolean judgeParameterIsNull(Object oo)
        throws MYException
    {
        return judgeParameterIsNull(oo, false);
    }

    public static boolean judgeParameterIsNull(Object[] oo, int judgeType)
        throws MYException
    {
        if (judgeType == JudgeTools.JUDGE_NUMBER)
        {
            judgeParameterIsNull(oo, true);
        }

        if (judgeType == JudgeTools.JUDGE_NUMBER_OR_LETTER)
        {
            judgeParameterIsNumberOrLetter(oo);
        }

        return judgeParameterIsNull(oo, false);
    }

    public static boolean judgeParameterIsNull(Object oo, int judgeType)
        throws MYException
    {
        return judgeParameterIsNull(new Object[] {oo}, judgeType);
    }

    public static boolean judgeParameterIsNull(Object oo, boolean regularNum)
        throws MYException
    {
        return judgeParameterIsNull(new Object[] {oo}, regularNum);
    }

    /**
     * Description: �ж�����Ƿ�Ϊ��(Ϊ���׳��쳣,���򷵻�true)<br>
     * ����string�жϿպ�null
     * 
     * @param oo
     * @param regularNum
     *            �Ƿ��string�жϴ�����
     * @return
     * @throws SPMException
     *             boolean
     */
    public static boolean judgeParameterIsNull(Object[] oo, boolean regularNum)
        throws MYException
    {
        if (oo == null)
        {
            throw new MYException("", "����Ϊ��");
        }

        for (int i = 0; i < oo.length; i++ )
        {
            if (oo[i] == null)
            {
                throw new MYException("", "����Ϊ��");
            }

            if (oo[i] instanceof String)
            {
                if (StringTools.isNullOrNone((String)oo[i]))
                {
                    throw new MYException("", "����Ϊ��");
                }

                if (regularNum)
                {
                    if ( !RegularExpress.isGuid((String)oo[i]))
                    {
                        throw new MYException("", "����ȫ����");
                    }
                }
            }
        }

        return false;
    }

    /**
     * Description: �ж�����Ƿ�Ϊ��(Ϊ���׳��쳣,���򷵻�true)<br>
     * ����string�жϿպ�null
     * 
     * @param oo
     * @param regularNum
     *            �Ƿ��string�жϴ�����
     * @return
     * @throws SPMException
     *             boolean
     */
    private static boolean judgeParameterIsNumberOrLetter(Object[] oo)
        throws MYException
    {
        if (oo == null)
        {
            throw new MYException("", "����Ϊ��");
        }

        for (int i = 0; i < oo.length; i++ )
        {
            if (oo[i] == null)
            {
                throw new MYException("", "����Ϊ��");
            }

            if (oo[i] instanceof String)
            {
                if (StringTools.isNullOrNone((String)oo[i]))
                {
                    throw new MYException("", "����Ϊ��");
                }

                if ( !RegularExpress.isNumberOrLetter((String)oo[i]))
                {
                    throw new MYException("", "�������������ֻ�����ĸ");
                }
            }
        }

        return false;
    }

}
