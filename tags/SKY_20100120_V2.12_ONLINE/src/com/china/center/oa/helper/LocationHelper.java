/**
 * File Name: LocationHelper.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-11-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.helper;


import com.china.center.oa.constant.PublicConstant;


/**
 * LocationHelper
 * 
 * @author zhuzhu
 * @version 2008-11-15
 * @see LocationHelper
 * @since 1.0
 */
public abstract class LocationHelper
{
    /**
     * �Ƿ����ܲ�����
     * 
     * @param locationId
     * @return
     */
    public static boolean isSystemLocation(String locationId)
    {
        if (PublicConstant.CENTER_LOCATION.equals(locationId))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * �Ƿ�����������
     * 
     * @param locationId
     * @return
     */
    public static boolean isVirtualLocation(String locationId)
    {
        if (PublicConstant.VIRTUAL_LOCATION.equals(locationId))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
