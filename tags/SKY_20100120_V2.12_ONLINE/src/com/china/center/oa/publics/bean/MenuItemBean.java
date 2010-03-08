/*
 * File Name: MenuItemBean.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-12-15
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.bean;

import java.io.Serializable;

import com.china.center.annotation.Entity;
import com.china.center.annotation.Id;
import com.china.center.annotation.Table;

/**
 * MenuItemBean
 *
 * @author zhuzhu
 * @version 2007-12-15
 * @see
 * @since
 */
@Entity(cache = false)
@Table(name = "T_CENTER_OAMENUITEM")
public class MenuItemBean implements Serializable
{
	@Id
	private String id = "";

	private String parentId = "";

	private int bottomFlag = 0;

	private String menuItemName = "";

	private String url = "";

	private String auth = "";

	private int indexPos = 0;

	/**
	 * default constructor
	 */
	public MenuItemBean()
	{
	}

	public boolean equals(Object oo)
	{
		if (this == oo)
		{
			return true;
		}

		if (oo == null)
		{
			return false;
		}

		if (oo instanceof MenuItemBean)
		{
			return this.id.equals(((MenuItemBean) oo).getId());
		}

		return false;
	}

	public String toString()
	{
		return this.id + '~' + this.menuItemName;
	}

	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @return the menuItemName
	 */
	public String getMenuItemName()
	{
		return menuItemName;
	}

	/**
	 * @return the url
	 */
	public String getUrl()
	{
		return url;
	}

	/**
	 * @return the auth
	 */
	public String getAuth()
	{
		return auth;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @param menuItemName
	 *            the menuItemName to set
	 */
	public void setMenuItemName(String menuItemName)
	{
		this.menuItemName = menuItemName;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url)
	{
		this.url = url;
	}

	/**
	 * @param auth
	 *            the auth to set
	 */
	public void setAuth(String auth)
	{
		this.auth = auth;
	}

	/**
	 * @return the parentId
	 */
	public String getParentId()
	{
		return parentId;
	}

	/**
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(String parentId)
	{
		this.parentId = parentId;
	}

	/**
	 * @return the bottomFlag
	 */
	public int getBottomFlag()
	{
		return bottomFlag;
	}

	/**
	 * @param bottomFlag
	 *            the bottomFlag to set
	 */
	public void setBottomFlag(int bottomFlag)
	{
		this.bottomFlag = bottomFlag;
	}

	/**
	 * @return the indexPos
	 */
	public int getIndexPos()
	{
		return indexPos;
	}

	/**
	 * @param indexPos the indexPos to set
	 */
	public void setIndexPos(int indexPos)
	{
		this.indexPos = indexPos;
	}
}