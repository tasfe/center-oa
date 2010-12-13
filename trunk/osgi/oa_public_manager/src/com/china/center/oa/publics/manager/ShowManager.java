/**
 * File Name: LocationManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager;


import com.center.china.osgi.publics.ListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.publics.bean.ShowBean;
import com.china.center.oa.publics.listener.ShowListener;


/**
 * LocationManager
 * 
 * @author ZHUZHU
 * @version 2010-6-21
 * @see ShowManager
 * @since 1.0
 */
public interface ShowManager extends ListenerManager<ShowListener>
{
    boolean addBean(User user, ShowBean bean)
        throws MYException;

    boolean updateBean(User user, ShowBean bean)
        throws MYException;

    boolean deleteBean(User user, String id)
        throws MYException;
}
