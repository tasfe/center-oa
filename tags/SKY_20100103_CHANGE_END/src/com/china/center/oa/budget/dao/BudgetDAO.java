/**
 * File Name: BudgetDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-12-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.budget.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.sannotations.annotation.Bean;

import org.springframework.jdbc.core.RowCallbackHandler;

import com.china.center.jdbc.inter.impl.BaseDAO2;
import com.china.center.oa.budget.bean.BudgetBean;
import com.china.center.oa.budget.vo.BudgetVO;
import com.china.center.oa.constant.BudgetConstant;
import com.china.center.tools.ListTools;
import com.china.center.tools.TimeTools;


/**
 * BudgetDAO
 * 
 * @author zhuzhu
 * @version 2008-12-2
 * @see BudgetDAO
 * @since 1.0
 */
@Bean(name = "budgetDAO")
public class BudgetDAO extends BaseDAO2<BudgetBean, BudgetVO>
{
    public boolean updateStatus(String id, int status)
    {
        this.jdbcOperation.updateField("status", status, id, claz);

        return true;
    }

    public boolean updateCarryStatus(String id, String carryStatus)
    {
        this.jdbcOperation.updateField("carryStatus", carryStatus, id, claz);

        return true;
    }

    /**
     * querySubmitBudgetByParentId
     * 
     * @param parentId
     * @return
     */
    public List<BudgetBean> querySubmitBudgetByParentId(String parentId)
    {
        return this.queryEntityBeansByCondition("where parentId = ? and status >= ?", parentId,
            BudgetConstant.BUDGET_STATUS_SUBMIT);
    }

    /**
     * queryCurrentRunBudget
     * 
     * @return
     */
    public List<BudgetVO> queryCurrentRunBudget()
    {
        return this.queryEntityVOsByCondition(
            "where BudgetBean.level = ? and BudgetBean.status = ? and BudgetBean.beginDate <= ? and BudgetBean.endDate >= ?",
            BudgetConstant.BUDGET_LEVEL_MONTH, BudgetConstant.BUDGET_STATUS_PASS,
            TimeTools.now_short(), TimeTools.now_short());
    }

    /**
     * countByYearAndType
     * 
     * @param year
     * @param type
     * @return
     */
    public int countByYearAndType(String year, int type)
    {
        return super.countByCondition("where year = ? and type = ?", year, type);
    }

    public boolean updateTotal(String id, double total)
    {
        this.jdbcOperation.updateField("total", total, id, claz);

        return true;
    }

    public boolean updateRealMoney(String id, double realMoney)
    {
        this.jdbcOperation.updateField("realMonery", realMoney, id, claz);

        return true;
    }

    /**
     * ͳ�Ƹ���Ԥ���������Ԥ���Ѿ�ռ�õĽ��
     * 
     * @param parentId
     * @return
     */
    public double countParentBudgetTotal(String parentId)
    {
        final List<Double> ruslt = new ArrayList<Double>();
        jdbcOperation.query(
            "select sum(total) as rst from T_CENTER_BUDGET where parentId = ? and status = ?",
            new Object[] {parentId, BudgetConstant.BUDGET_STATUS_PASS}, new RowCallbackHandler()
            {
                public void processRow(ResultSet rst)
                    throws SQLException
                {
                    ruslt.add(rst.getDouble("rst"));
                }
            });

        if (ListTools.isEmptyOrNull(ruslt))
        {
            return 0.0;
        }

        return ruslt.get(0);
    }
}
