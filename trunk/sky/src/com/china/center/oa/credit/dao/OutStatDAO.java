/**
 * File Name: OutStatDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2009-12-3<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.credit.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sourceforge.sannotations.annotation.Bean;

import org.springframework.jdbc.core.RowCallbackHandler;

import com.china.center.common.ConditionParse;
import com.china.center.jdbc.inter.impl.BaseDAO2;
import com.china.center.oa.constant.CreditConstant;
import com.china.center.tools.ListTools;
import com.china.center.tools.TimeTools;
import com.china.centet.yongyin.bean.OutBean;


/**
 * OutStatDAO
 * 
 * @author ZHUZHU
 * @version 2009-12-3
 * @see OutStatDAO
 * @since 1.0
 */
@Bean(name = "outStatDAO")
public class OutStatDAO extends BaseDAO2<OutBean, OutBean>
{
    /**
     * 2010-03-01��ʼʹ��
     */
    private static final String BEGINDATE = "2010-03-01";

    /**
     * ��ѯû�н���ͳ�Ƶ����۵�(2009-12-01��ʼͳ�Ƶ�)
     * 
     * @param cid
     * @param beginTime
     * @return
     */
    public List<OutBean> queryNoneStatByCid(String cid)
    {
        ConditionParse condtition = new ConditionParse();

        condtition.addWhereStr();

        String beginDate = BEGINDATE;

        // TODO ֻͳ��ǰ6���µ�����(���ﲻ����Խ������ȵĿ�ʼ)
        String fbeginDate = TimeTools.getDateShortString( -6 * 30);

        if (beginDate.compareTo(fbeginDate) < 0)
        {
            beginDate = fbeginDate;
        }

        condtition.addCondition("outTime", ">=", beginDate);

        // ���۵�
        condtition.addIntCondition("type", "=", 0);

        // ֻ�������۳���
        condtition.addIntCondition("outType", "=", 0);

        // δ���ô���ĵ���
        condtition.addIntCondition("reserve1", "=", CreditConstant.CREDIT_OUT_INIT);

        condtition.addCondition("customerId", "=", cid);

        condtition.addCondition("and status in (3, 4)");

        condtition.addCondition("order by id asc");

        return this.queryEntityBeansByCondition(condtition);
    }

    /**
     * listCustomerIdList
     * 
     * @return
     */
    public List<String> listCustomerIdList()
    {
        ConditionParse condtition = new ConditionParse();

        condtition.addWhereStr();

        String beginDate = BEGINDATE;

        // ֻͳ��ǰ13���µ�����
        String fbeginDate = TimeTools.getDateShortString( -13 * 30);

        if (beginDate.compareTo(fbeginDate) < 0)
        {
            beginDate = fbeginDate;
        }

        condtition.addCondition("outTime", ">=", beginDate);

        // ���۵�
        condtition.addIntCondition("type", "=", 0);

        // ֻ�������۳���
        condtition.addIntCondition("outType", "=", 0);

        final List<String> list = new LinkedList<String>();

        this.jdbcOperation.query("select distinct(t.customerId) from t_center_out t "
                                 + condtition.toString(), new RowCallbackHandler()
        {
            public void processRow(ResultSet rs)
                throws SQLException
            {
                String ccid = rs.getString(1);

                if ( !list.contains(ccid))
                {
                    list.add(ccid);
                }
            }
        });

        this.jdbcOperation.query("select distinct(t.cid) from t_center_vs_curcre t ",
            new RowCallbackHandler()
            {
                public void processRow(ResultSet rs)
                    throws SQLException
                {
                    String ccid = rs.getString(1);

                    if ( !list.contains(ccid))
                    {
                        list.add(ccid);
                    }
                }
            });

        return list;
    }

    /**
     * findNearestById
     * 
     * @param fullId
     * @param cid
     * @return
     */
    public OutBean findNearestById(String id, String cid)
    {
        // ��ѯ���۵��������һ��
        List<OutBean> list = this.jdbcOperation.queryObjects(
            "where 1= 1 and type = ? and outType = ? and customerId = ? and id < ? order by id desc",
            claz, 0, 0, cid, id).setMaxResults(1).list(claz);

        if (ListTools.isEmptyOrNull(list))
        {
            return null;
        }

        return list.get(0);
    }

    /**
     * updateReserve1ByFullId
     * 
     * @param fullId
     * @param reserve1
     * @param reserve4
     * @return
     */
    public boolean updateReserve1ByFullId(String fullId, int reserve1, String reserve4)
    {
        this.jdbcOperation.update("set reserve1 = ? , reserve4 = ? where fullid = ?", claz,
            reserve1, reserve4, fullId);

        return true;
    }

    public boolean updateReserve5ByFullId(String fullId, String reserve5)
    {
        this.jdbcOperation.update("set reserve5 = ? where fullid = ?", claz, reserve5, fullId);

        return true;
    }

    /**
     * queryMaxBusiness
     * 
     * @param cid
     * @param beginDate
     * @param endDate
     * @return
     */
    public double queryMaxBusiness(String cid, String beginDate, String endDate)
    {
        Map<String, String> paramterMap = new HashMap();

        paramterMap.put("customerId", cid);
        paramterMap.put("beginDate", beginDate);
        paramterMap.put("endDate", endDate);

        Object max = this.jdbcOperation.getIbatisDaoSupport().queryForObject(
            "OutStatDAO.queryMaxBusiness", paramterMap);

        if (max == null)
        {
            return 0.0d;
        }

        return (Double)max;
    }

    /**
     * sumBusiness(ͳ��һ��ʱ���ڵ����۶�)
     * 
     * @param cid
     * @param beginDate
     * @param endDate
     * @return
     */
    public double sumBusiness(String cid, String beginDate, String endDate)
    {
        Map<String, String> paramterMap = new HashMap();

        paramterMap.put("customerId", cid);
        paramterMap.put("beginDate", beginDate);
        paramterMap.put("endDate", endDate);

        Object max = this.jdbcOperation.getIbatisDaoSupport().queryForObject(
            "OutStatDAO.sumBusiness", paramterMap);

        if (max == null)
        {
            return 0.0d;
        }

        return (Double)max;
    }
}
