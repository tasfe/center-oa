/**
 * �� �� ��: UserVO.java <br>
 * �� Ȩ: centerchina Technologies Co., Ltd. Copyright YYYY-YYYY, All rights reserved
 * <br>
 * �� ��: <����> <br>
 * �� �� ��: admin <br>
 * �޸�ʱ��: 2008-1-6 <br>
 * ���ٵ���: <���ٵ���> <br>
 * �޸ĵ���: <�޸ĵ���> <br>
 * �޸�����: <�޸�����> <br>
 */
package com.china.centet.yongyin.vo;


import com.china.center.annotation.Entity;
import com.china.centet.yongyin.bean.User;


/**
 * <һ�仰���ܼ���> <������ϸ����>
 * 
 * @author admin
 * @version [�汾��, 2008-1-6]
 * @see [�����/����]
 * @since [��Ʒ/ģ��汾]
 */
@Entity(inherit = true, cache = true)
public class UserVO extends User
{
    private String locationName = "";

    public UserVO()
    {}

    /**
     * @return ���� locationName
     */
    public String getLocationName()
    {
        return locationName;
    }

    /**
     * @param ��locationName���и�ֵ
     */
    public void setLocationName(String locationName)
    {
        this.locationName = locationName;
    }

}
