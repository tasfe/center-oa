/**
 *
 */
package com.china.center.cache;


import java.io.InputStream;
import java.util.List;
import java.util.Set;

import com.china.center.cache.bean.StatEfficiency;


/**
 * cache root manager
 * 
 * @author Administrator
 */
public interface CacheBootstrap<T>
{
    /**
     * ��������
     * 
     * @param in
     */
    void bootstrap(InputStream in);

    /**
     * ע�Ỻ��
     * 
     * @param claz
     */
    boolean registerCache(Class claz);

    /**
     * ע�Ỻ��
     * 
     * @param claz
     */
    void logOutCahce(Class claz);

    /**
     * �����ص�class
     * 
     * @param claz
     * @return
     */
    List<Class> refClass(Class claz);

    /**
     * ����class��ѯ���建��
     * 
     * @param claz
     * @return
     */
    T findSingleCache(Class claz);

    /**
     * ����class��ѯquery����
     * 
     * @param claz
     * @return
     */
    T findMoreCache(Class claz);

    /**
     * ����Ч��
     * 
     * @param claz
     * @return
     */
    StatEfficiency singleCacheEfficiency(Class claz);

    /**
     * ����Ч��
     * 
     * @param claz
     * @return
     */
    StatEfficiency moreCacheEfficiency(Class claz);

    /**
     * ���ù�����
     * 
     * @param filtrate
     */
    void setFiltrate(String filtrate);

    /**
     * �Ƿ����ʹ��
     * 
     * @return
     */
    boolean enable();

    /**
     * key set
     * 
     * @return
     */
    Set<Class> cacheKeySet();

    /**
     * �����Ķ�Ӧ����
     * 
     * @param claz
     * @return
     */
    Class getClassByTableName(String tableName);
}
