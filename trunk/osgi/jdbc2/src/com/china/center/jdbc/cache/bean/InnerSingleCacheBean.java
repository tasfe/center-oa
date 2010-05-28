/**
 *
 */
package com.china.center.jdbc.cache.bean;


import java.io.Serializable;

import com.china.center.jdbc.annosql.tools.BaseTools;
import com.china.center.jdbc.clone.DataClone;


/**
 * 实际存放在缓存里面的对象
 * 
 * @author Administrator
 */
public class InnerSingleCacheBean<T> implements DataClone<InnerSingleCacheBean>, Serializable
{
    private String key = "";

    /**
     * 查询的sql
     */
    private String sql = "";

    /**
     * 查询的参数
     */
    private Object[] parameters = null;

    /**
     * 返回的结果
     */
    private T result = null;

    /**
     * Copy Constructor
     * 
     * @param innerSingleCacheBean
     *            a <code>InnerSingleCacheBean</code> object
     */
    public InnerSingleCacheBean(InnerSingleCacheBean innerSingleCacheBean)
    {
        this.key = innerSingleCacheBean.key;
        this.sql = innerSingleCacheBean.sql;
        this.parameters = innerSingleCacheBean.parameters;
        this.result = (T)BaseTools.deepCopy(innerSingleCacheBean.result);
    }

    /**
     *
     */
    public InnerSingleCacheBean()
    {}

    /**
     * @return the key
     */
    public String getKey()
    {
        return key;
    }

    /**
     * @param key
     *            the key to set
     */
    public void setKey(String key)
    {
        this.key = key;
    }

    /**
     * @return the sql
     */
    public String getSql()
    {
        return sql;
    }

    /**
     * @param sql
     *            the sql to set
     */
    public void setSql(String sql)
    {
        this.sql = sql;
    }

    /**
     * @return the parameters
     */
    public Object[] getParameters()
    {
        return parameters;
    }

    /**
     * @param parameters
     *            the parameters to set
     */
    public void setParameters(Object[] parameters)
    {
        this.parameters = parameters;
    }

    /**
     * @return the result
     */
    public T getResult()
    {
        return result;
    }

    /**
     * @param result
     *            the result to set
     */
    public void setResult(T result)
    {
        this.result = result;
    }

    public InnerSingleCacheBean clones()
    {
        return new InnerSingleCacheBean(this);
    }
}
