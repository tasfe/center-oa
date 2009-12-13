/**
 * File Name: CarryPlan.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-1-11<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.plan.manager;


import com.china.center.oa.plan.bean.PlanBean;


/**
 * ִ�мƻ�
 * 
 * @author zhuzhu
 * @version 2009-1-11
 * @see CarryPlan
 * @since 1.0
 */
public interface CarryPlan
{
    /**
     * ִ�мƻ�
     * 
     * @param plan
     *            �ƻ�
     * @param end
     *            �Ƿ������һ��ִ��
     * @return
     */
    boolean carryPlan(PlanBean plan, boolean end);

    /**
     * ��üƻ�����
     * 
     * @return
     */
    int getPlanType();
}
