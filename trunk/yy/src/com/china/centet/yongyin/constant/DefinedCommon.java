/**
 * File Name: DefinedCommon.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-3-2<br>
 * Grant: open source to everybody
 */
package com.china.centet.yongyin.constant;


import java.util.HashMap;
import java.util.Map;

import com.china.center.tools.TimeTools;


/**
 * �����Ķ����ʹ��
 * 
 * @author ZHUZHU
 * @version 2008-3-2
 * @see
 * @since
 */
public abstract class DefinedCommon
{
    private static Map<String, Object[]> definedMap = new HashMap<String, Object[]>();

    public static String webBeginTime = TimeTools.now();

    static
    {
        definedMap.put("outType", new Object[] {"�ɹ����", "����", "�̿�����", "��ӯ���", "����", "�˻������",
            "���ϳ���", "�ɹ��˻�", "����"});

        definedMap.put("reprotType", new Object[] {"<font color=blue>�޻ظ�</font>", "�����ջ�",
            "<font color=red>�쳣�ջ�</font>"});

        definedMap.put("memberGrade", new Object[] {"��ͨ", "����", "��", "����"});

        definedMap.put("memberType", new Object[] {"��ͨ��Ա", "���û�Ա"});

        definedMap.put("priceStatus", new Object[] {"����", "����"});

        definedMap.put("priceAskStatus", new Object[] {"��ʼ", colorationToBlue("ѯ����"),
            colorationToRed("����"), "����"});

        definedMap.put("priceAskInstancy", new Object[] {"һ��", "����", "�ǳ�����"});

        definedMap.put("stockItemPayStatus", new Object[] {colorationToRed("δ����"),
            colorationToBlue("�ѻ���")});

        definedMap.put("priceAskType", new Object[] {"�ڲ�ѯ��", colorationToBlue("����ѯ��"),
            colorationToBlue("������ѯ��")});

        definedMap.put("stockStatus", new Object[] {colorationToGold("����"), "�ύ",
            colorationToRed("����"), "������ͨ��", "�˼�Աͨ��", "�ɹ�����ͨ��", "�ɹ�����ͨ��", colorationToBlue("�ɹ���"),
            colorationToBlue("�ɹ�����")});

        definedMap.put("stockPay", new Object[] {colorationToRed("δ����"), colorationToBlue("�Ѹ���"),
            colorationToGold("��������"), colorationToRed("���벵��")});

        definedMap.put("stockExceptStatus", new Object[] {colorationToBlue("����"),
            colorationToRed("��Ʒ�۸����С"), colorationToGold("��Ʒ�۸��ܼƹ���")});

        definedMap.put("flowDefineStatus", new Object[] {"����", "����", colorationToRed("����")});

        definedMap.put("outCredit", new Object[] {"����", "��֧", "�۸�Ϊ0"});

        definedMap.put("tokenType", new Object[] {"��Ա����", "��ɫ����", "ȫ��", "ְԱ����", ""});
    }

    public static String getValue(String key, int index)
    {
        Object[] oo = definedMap.get(key);

        if (oo == null || oo.length == 0)
        {
            return "";
        }

        if (index < 0)
        {
            return "";
        }

        if (index >= oo.length)
        {
            return oo[oo.length - 1].toString();
        }

        return oo[index].toString();

    }

    /**
     * ҳ������Ⱦɫ�ɺ�ɫ
     * 
     * @param str
     * @return
     */
    public static String colorationToRed(String str)
    {
        return coloration(str, "red");
    }

    /**
     * ҳ������Ⱦɫ����ɫ
     * 
     * @param str
     * @return
     */
    public static String colorationToBlue(String str)
    {
        return coloration(str, "blue");
    }

    /**
     * #FFD700 ��ɫ
     * 
     * @param str
     * @return
     */
    public static String colorationToGold(String str)
    {
        return coloration(str, "gold");
    }

    /**
     * #00FFFF
     * 
     * @param str
     * @return
     */
    public static String colorationToCyan(String str)
    {
        return coloration(str, "cyan");
    }

    public static String coloration(String str, String color)
    {
        return "<font color=" + color + ">" + str + "</font>";
    }
}
