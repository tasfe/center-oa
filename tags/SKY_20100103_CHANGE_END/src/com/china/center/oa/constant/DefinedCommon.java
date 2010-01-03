/**
 * File Name: DefinedCommon.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-3-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.constant;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.china.center.annotation.tools.DefinedTools;
import com.china.center.common.taglib.MapBean;
import com.china.center.common.taglib.PageSelectOption;
import com.china.center.oa.note.bean.ShortMessageConstant;
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
    private static Map<String, List<MapBean>> definedMap = PageSelectOption.optionMap;

    public static String webBeginTime = TimeTools.now();

    static
    {
        List<MapBean> customerOprlist = new ArrayList<MapBean>();
        definedMap.put("customerOpr", customerOprlist);

        customerOprlist.add(new MapBean(CustomerConstant.OPR_ADD, "����"));
        customerOprlist.add(new MapBean(CustomerConstant.OPR_UPDATE, "����"));
        customerOprlist.add(new MapBean(CustomerConstant.OPR_DEL, "ɾ��"));
        customerOprlist.add(new MapBean(CustomerConstant.OPR_UPATE_CREDIT, "���¿ͻ�����"));

        List<MapBean> customerStatus = new ArrayList<MapBean>();
        definedMap.put("customerStatus", customerStatus);

        customerStatus.add(new MapBean(CustomerConstant.STATUS_OK, "����"));
        customerStatus.add(new MapBean(CustomerConstant.STATUS_APPLY, "����"));
        customerStatus.add(new MapBean(CustomerConstant.STATUS_REJECT, "����"));
        customerStatus.add(new MapBean(CustomerConstant.STATUS_WAIT_CODE, "�ȴ��������"));

        List<MapBean> realCustomerStatus = new ArrayList<MapBean>();
        definedMap.put("realCustomerStatus", realCustomerStatus);

        realCustomerStatus.add(new MapBean(CustomerConstant.REAL_STATUS_IDLE, "����"));
        realCustomerStatus.add(new MapBean(CustomerConstant.REAL_STATUS_USED, "ʹ��"));
        realCustomerStatus.add(new MapBean(CustomerConstant.REAL_STATUS_APPLY, "������"));

        List<MapBean> userStatus = new ArrayList<MapBean>();
        definedMap.put("userStatus", userStatus);

        userStatus.add(new MapBean(PublicConstant.LOGIN_STATUS_COMMON, "����"));
        userStatus.add(new MapBean(PublicConstant.LOGIN_STATUS_LOCK, "����"));

        List<MapBean> blog = new ArrayList<MapBean>();
        definedMap.put("blog", blog);

        blog.add(new MapBean(CustomerConstant.BLOG_NO, "����ʷ�ɽ�"));
        blog.add(new MapBean(CustomerConstant.BLOG_YES, "����ʷ�ɽ�"));

        List<MapBean> card = new ArrayList<MapBean>();
        definedMap.put("card", card);

        card.add(new MapBean(CustomerConstant.CARD_NO, "����Ƭ"));
        card.add(new MapBean(CustomerConstant.CARD_YES, "����Ƭ"));

        DefinedTools.parserConstant(definedMap, BudgetConstant.class);

        DefinedTools.parserConstant(definedMap, CustomerConstant.class);

        DefinedTools.parserConstant(definedMap, ExamineConstant.class);

        DefinedTools.parserConstant(definedMap, PlanConstant.class);

        DefinedTools.parserConstant(definedMap, StafferConstant.class);

        DefinedTools.parserConstant(definedMap, WorkLogConstant.class);

        DefinedTools.parserConstant(definedMap, OperationConstant.class);

        DefinedTools.parserConstant(definedMap, CommonConstant.class);

        DefinedTools.parserConstant(definedMap, GroupConstant.class);

        DefinedTools.parserConstant(definedMap, MailConstant.class);

        DefinedTools.parserConstant(definedMap, FlowConstant.class);

        DefinedTools.parserConstant(definedMap, ShortMessageConstant.class);

        DefinedTools.parserConstant(definedMap, MakeConstant.class);

        DefinedTools.parserConstant(definedMap, CreditConstant.class);
    }

    public static String getValue(String key, int index)
    {
        List<MapBean> oo = definedMap.get(key);

        if (oo == null)
        {
            return "";
        }

        if (index < 0)
        {
            return "";
        }

        for (MapBean mapBean : oo)
        {
            if (mapBean.getKey().equals(String.valueOf(index)))
            {
                return mapBean.getValue();
            }
        }

        if (index >= oo.size())
        {
            return oo.get(oo.size() - 1).getValue();
        }

        return oo.get(index).getValue();

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
