/**
 * File Name: CacheManager.java
 * CopyRight: Copyright by www.centerchina.com
 * Description:
 * Creater: zhuzhu
 * CreateTime: 2008-7-9
 * Grant: open source to everybody
 */
package com.china.center.cache;


import java.util.List;

import com.china.center.cache.bean.InnerMoreCacheBean;
import com.china.center.cache.bean.InnerSingleCacheBean;


/**
 * manage the cache<br>
 * in jdbc cache,we will cache two operations: find and query.<br>
 * In cache,the base cache object is bean or VO,and the cache contain two modes:<br>
 * 1:sigle bean 2:more beans<br>
 * Recomment use ehcache
 * 
 * @author zhuzhu
 */
public interface CacheManager
{
    String NOTIFY_INSERT = "0";

    String NOTIFY_UPDATE = "1";

    /**
     * ����find�Ļ���
     * 
     * @param <T>
     * @param claz
     * @param cache
     * @return
     */
    <T> boolean addSingleCache(Class<T> claz, InnerSingleCacheBean<T> cache);

    /**
     * ����query�Ļ���
     * 
     * @param <T>
     * @param claz
     * @param cache
     * @return
     */
    <T> boolean addMoreCache(Class<T> claz, InnerMoreCacheBean<T> cache);

    /**
     * ����ID��ȡ����
     * 
     * @param <T>
     * @param claz
     * @param cache
     * @return
     */
    <T> T findSingleCache(Class<T> claz, String key);

    /**
     * ����ID��ȡ����
     * 
     * @param <T>
     * @param claz
     * @param cache
     * @return
     */
    <T> List<T> findMoreCaches(Class<T> claz, String key, String condtion, Object... args);

    /**
     * ɾ�����建��
     * 
     * @param <T>
     * @param claz
     * @param cache
     * @return
     */
    void removeSingleCache(Class claz, String key);

    /**
     * ɾ������class�Ļ���
     * 
     * @param <T>
     * @param claz
     */
    void removeCache(Class claz);

    /**
     * ɾ��single bean�Ļ���
     * 
     * @param <T>
     * @param claz
     */
    void removeSingelCache(Class claz);

    /**
     * ɾ��more bean�Ļ���
     * 
     * @param <T>
     * @param claz
     */
    void removeMoreCache(Class claz);

    /**
     * ɾ����ѯ����
     * 
     * @param <T>
     * @param claz
     * @param cache
     * @return
     */
    void removeMoreCache(Class claz, String key);

    /**
     * ����֪ͨ
     * 
     * @param claz
     */
    void noteUpdate(Class claz);

    /**
     * ����֪ͨ
     * 
     * @param claz
     */
    void noteInsert(Class claz);

    /**
     * ɾ��֪ͨ
     * 
     * @param claz
     */
    void noteDelete(Class claz);

    /**
     * ����boot
     * 
     * @param manager
     */
    void setBootstrap(CacheBootstrap manager);

    /**
     * �Ƿ�Ⱥ
     * 
     * @param cluster
     */
    void setCluster(boolean cluster);

    /**
     * ����֪ͨ
     * 
     * @param notify
     */
    void noyify(String notify);

    /**
     * ��ʼ��
     */
    void init();
}
