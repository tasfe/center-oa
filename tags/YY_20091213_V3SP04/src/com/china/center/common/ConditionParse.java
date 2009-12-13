package com.china.center.common;


import java.io.Serializable;

import com.china.center.tools.StringTools;


/**
 * @author zhuzhu
 * @version 2007-12-18
 * @see
 * @since
 */
public class ConditionParse implements Serializable
{
    private StringBuffer condition = new StringBuffer();

    /**
     *
     *
     */
    public ConditionParse()
    {}

    /**
     * ���������ֶΡ������������ֵƴ�ղ�ѯ����
     * 
     * @param fieldName
     *            �ֶ�����
     * @param oper
     *            ��ϵ�����
     * @param conditionValue
     *            ����Ĳ�ѯ����ֵ
     */
    public void addCondition(String fieldName, String oper, String conditionValue)
    {
        if ( ! (conditionValue == null || "".equals(conditionValue)))
        {
            String tempOper = oper.trim().toUpperCase();
            if (tempOper.indexOf("LIKE") != -1)
            {
                condition.append(" AND ").append(dbValueString(fieldName)).append(" ").append(
                    dbValueString(oper)).append(" ");
                condition.append("'%").append(dbValueString(conditionValue)).append("%'");
            }
            else
            {
                condition.append(" AND ").append(dbValueString(fieldName)).append(" ").append(
                    dbValueString(oper)).append(" ");
                condition.append("'").append(dbValueString(conditionValue)).append("'");
            }
        }
    }

    /**
     * Description: <br>
     * ָ������������� <br>
     * Implement: <br>
     * 
     * @param conditionValue
     */
    public void addCondition(String conditionValue)
    {
        condition.append(" ").append(conditionValue);
    }

    /**
     * @param fieldName
     * @param oper
     * @param conditionValue
     */
    public void addCondition(String fieldName, String oper, int conditionValue)
    {
        String tempValue = String.valueOf(conditionValue);
        if ( ! (tempValue == null || "".equals(tempValue)))
        {
            condition.append(" AND ").append(dbValueString(fieldName)).append(" ").append(
                dbValueString(oper)).append(" ").append(dbValueString(tempValue)).append(" ");
        }
    }

    /**
     * Description:�������������ֶ� <br>
     * [�����б�˵��ÿ��������;]
     * 
     * @param fieldName
     * @param oper
     * @param conditionValue
     *            void
     */
    public void addIntCondition(String fieldName, String oper, String conditionValue)
    {
        if ( ! (conditionValue == null || "".equals(conditionValue)))
        {
            int tempValue = Integer.parseInt(conditionValue);
            condition.append(" AND ").append(dbValueString(fieldName)).append(" ").append(
                dbValueString(oper)).append(tempValue);
        }

    }

    public void clear()
    {
        condition.delete(0, condition.length());
    }

    public void addIntCondition(String fieldName, String oper, int conditionValue)
    {
        condition.append(" AND ").append(dbValueString(fieldName)).append(" ").append(
            dbValueString(oper)).append(conditionValue);
    }

    public void addCommonCondition(String fieldName, String oper, String conditionValue)
    {
        if ( ! (conditionValue == null || "".equals(conditionValue)))
        {
            condition.append(" AND ").append(dbValueString(fieldName)).append(" ").append(
                dbValueString(oper)).append(conditionValue);
        }
    }

    /**
     * ����WHERE�ַ�����ǰ�棬��Щ�����ԭ��ѯ���û��������䣬 ��Ҫ���������ǰ����"WHERE"��ͷ��
     */
    public void addWhereStr()
    {
        if (toString().toUpperCase().indexOf("WHERE") == -1)
        {
            condition.insert(0, " WHERE 1=1 ");
        }
    }

    public void removeWhereStr()
    {
        if (toString().toUpperCase().indexOf("WHERE") != -1)
        {
            String newConstiton = condition.toString().trim();

            condition.delete(0, condition.length());

            condition.append(newConstiton);

            condition.delete(0, "WHERE".length());
        }
    }

    public String getCondition()
    {
        return condition.toString();
    }

    public String toString()
    {
        return getCondition();
    }

    public void setCondition(String condition)
    {
        this.condition.setLength(0);
        this.condition.append(condition);
    }

    /**
     * ��'��� ''
     * 
     * @param src
     * @return
     */
    private String dbValueString(String src)
    {
        if (StringTools.isNullOrNone(src))
        {
            return "";
        }

        return src.replaceAll("'", "''");
    }
}
