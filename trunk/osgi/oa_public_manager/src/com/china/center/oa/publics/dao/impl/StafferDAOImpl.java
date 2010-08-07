/**
 * File Name: StafferDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao.impl;


import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.vo.StafferVO;
import com.china.center.tools.ListTools;


/**
 * StafferDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-6-21
 * @see StafferDAOImpl
 * @since 1.0
 */
public class StafferDAOImpl extends BaseDAO<StafferBean, StafferVO> implements StafferDAO
{
    public int countByLocationId(String locationId)
    {
        return jdbcOperation.queryForInt("where locationId = ? ", this.claz, locationId);
    }

    public int countByCode(String code)
    {
        return jdbcOperation.queryForInt("where code = ? ", this.claz, code);
    }

    public boolean updatePwkey(String id, String pwkey)
    {
        this.jdbcOperation.updateField("pwkey", pwkey, id, this.claz);

        return true;
    }

    public int countByDepartmentId(String departmentId)
    {
        return jdbcOperation.queryForInt("where departmentId = ? ", this.claz, departmentId);
    }

    public int countByPostId(String postId)
    {
        return jdbcOperation.queryForInt("where postId = ? ", this.claz, postId);
    }

    /**
     * 查询区域下的职员
     * 
     * @param locationId
     * @return
     */
    public List<StafferBean> queryStafferByLocationId(String locationId)
    {
        return this.jdbcOperation.queryForList("where locationId = ? order by name", claz,
            locationId);
    }

    /**
     * 重载listEntityBeans
     */
    public List<StafferBean> listEntityBeans()
    {
        return jdbcOperation.queryForList("where 1= 1 order by name", claz);
    }

    /**
     * listCommonEntityBeans
     */
    public List<StafferBean> listCommonEntityBeans()
    {
        return jdbcOperation.queryForList("where status < ? order by name", claz,
            StafferConstant.STATUS_DROP);
    }

    /**
     * 根据岗位查询人员
     * 
     * @param locationId
     * @return
     */
    public List<StafferBean> queryStafferByPrincipalshipId(String principalshipId)
    {
        return this.jdbcOperation.queryForList("where principalshipId = ?", claz, principalshipId);
    }

    public StafferBean findyStafferByName(String name)
    {
        List<StafferBean> list = this.jdbcOperation.queryForList("where name = ?", claz, name);

        if (ListTools.isEmptyOrNull(list) || list.size() != 1)
        {
            return null;
        }

        return list.get(0);
    }
}
