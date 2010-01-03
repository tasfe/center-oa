package com.china.center.jdbc.util;


import java.io.Serializable;

import com.china.centet.yongyin.constant.Constant;


/**
 * page separate
 * 
 * @author zhuzhu
 * @version 2008-3-7
 * @see
 * @since
 */
public class PageSeparate implements Serializable
{
    private static final int PAGE_SIZE = 20;

    // ����������
    private int rowCount = 0;

    // ÿҳ��ʾ������
    private int pageSize = Constant.PAGE_COMMON_SIZE;

    // ������ҳ��
    private int pageCount = 0;

    // ��ǰΪ�ڼ�ҳ
    // ��ֵ�����๹��ʱ���г�ʼ��Ϊ 1
    private int nowPage = 0;

    /**
     * Ĭ�Ϲ�����
     */
    public PageSeparate()
    {}

    public void reset(int rowCount, int pageSize)
    {
        init(rowCount, pageSize);
    }

    public PageSeparate(int rowCount, int pageSize)
    {
        init(rowCount, pageSize);
    }

    /**
     * @param rowCount
     * @param pageSize
     */
    private void init(int rowCount, int pageSize)
    {
        if ( (rowCount < 0) || (pageSize < 1))
        {
            this.rowCount = 0;
            this.pageSize = PAGE_SIZE;
        }
        else
        {
            this.rowCount = rowCount;
            this.pageSize = pageSize;
        }

        // ������ҳ�����������0����¼����rowCountΪ0����pageCount��ֵҲΪ0
        pageCount = (this.rowCount + this.pageSize - 1) / this.pageSize;

        // ����ǰҳ������Ϊ��һҳ(��������ݣ�������Ϊ1��û����������Ϊ0)
        nowPage = (pageCount > 0) ? 1 : 0;
    }

    /**
     * Description: <br>
     * ������������ͬʱҪ�����µ�pageCount <br>
     * ��@return rowCount ������
     * 
     * @param rowCount
     */
    public void setRowCount(int rowCount)
    {
        this.rowCount = rowCount;
        this.pageCount = (rowCount + pageSize - 1) / this.pageSize;
        // add by pengyi �޸�ɾ�����һҳ����ʱ������nowpageΪ����һҳ
        if (nowPage > pageCount)
        {
            nowPage = pageCount;
        }

        // �����ʼΪ0����˵���Ǵ��޵��У�Ӧ������Ϊ1
        if ( (nowPage == 0) && (pageCount != 0))
        {
            nowPage = 1;
        }
    }

    /**
     * Description: <br>
     * ��������� <br>
     * ��@return rowCount ������
     */
    public int getRowCount()
    {
        return rowCount;
    }

    /**
     * Description: <br>
     * ����ÿҳ��ʾ������ <br>
     * [�����б�˵��ÿ��������;]
     * 
     * @param pageSize
     *            ÿҳ��ʾ������ ��@return void
     */
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    /**
     * Description: <br>
     * ���ÿҳ��ʾ������ <br>
     * ��@return ÿҳ��ʾ������
     */
    public int getPageSize()
    {
        return pageSize;
    }

    /**
     * Description: <br>
     * �����ҳ�� <br>
     * ��@return ��ҳ��
     */
    public int getPageCount()
    {
        return pageCount;
    }

    /**
     * Description: <br>
     * �жϵ�ǰҳ�Ƿ�����һҳ <br>
     * Implement: <br>
     * ͨ���Ƚϵ�ǰ��ҳ������ҳ�����ж� <br>
     * [�����б�˵��ÿ��������;] ��@return �������һҳ������true; ���û����һҳ������false
     */
    public boolean hasNextPage()
    {
        return (nowPage < pageCount) ? true : false;
    }

    /**
     * Description: <br>
     * �жϵ�ǰҳ�Ƿ�����һҳ <br>
     * Implement: <br>
     * ͨ���Ƚϵ�ǰҳ�Ƿ��ǵ�һҳ <br>
     * [�����б�˵��ÿ��������;] ��@return �������һҳ������true; ���û����һҳ������false
     */
    public boolean hasPrevPage()
    {
        return (nowPage > 1) ? true : false;
    }

    /**
     * Description: <br>
     * ���·�ҳ <br>
     * Implement: <br>
     * 1. �жϵ�ǰҳ�Ƿ�����ҳ <br>
     * 2. �������ҳ����ǰҳ������1 <br>
     * [�����б�˵��ÿ��������;] ��@return void
     */
    public boolean nextPage()
    {
        if (hasNextPage())
        {
            nowPage++ ;

            return true;
        }

        // �����ʾ���Ѿ������һҳ
        return false;
    }

    /**
     * Description: <br>
     * ���Ϸ�ҳ <br>
     * Implement: <br>
     * 1. �жϵ�ǰҳ�Ƿ�����ҳ <br>
     * 2. �������ҳ����ǰҳ������1 <br>
     * [�����б�˵��ÿ��������;] ��@return void
     */
    public boolean prevPage()
    {
        if (hasPrevPage())
        {
            nowPage-- ;
            return true;
        }

        // �����ʾ���Ѿ��ǵ�һҳ
        return false;

    }

    /**
     * Description: <br>
     * �жϵ�ǰҳ�Ƿ��ǵ�һҳ <br>
     * Implement: <br>
     * 1. ��ǰҳ������1�Ƚ� <br>
     * [�����б�˵��ÿ��������;] ��@return ����ǵ�һҳ������true�����򷵻�false
     */
    public boolean isFirstPage()
    {
        return (nowPage == 1) ? true : false;
    }

    /**
     * Description: <br>
     * �жϵ�ǰҳ�Ƿ������һҳ <br>
     * Implement: <br>
     * 1. ��ǰҳ��������ҳ���Ƚϣ������ж���ҳ���Ƿ�Ϊ0 <br>
     * [�����б�˵��ÿ��������;] ��@return ��������һҳ������true�����򷵻�false
     */
    public boolean isLastPage()
    {
        return ( (pageCount == 0) || (nowPage == pageCount)) ? true : false;
    }

    /**
     * Description: <br>
     * ��õ�ǰҳ��һ�����ܼ�¼�е����� <br>
     * Implement: <br>
     * 1. ��ǰҳ������1Ȼ�����ÿҳ��ʾ������ <br>
     * [�����б�˵��ÿ��������;] ��@return ��ǰҳ��һ�����ܼ�¼�е�����
     */
    public int getSectionFoot()
    {

        return (pageCount == 0) ? 0 : (nowPage - 1) * pageSize;
    }

    /**
     * Description: <br>
     * ��õ�ǰҳ���һ�����ܼ�¼�е����� <br>
     * Implement: <br>
     * 1. ��ǰҳ���һ�е���������ÿҳ��ʾ������ <br>
     * [�����б�˵��ÿ��������;] ��@return ��ǰҳ���һ�����ܼ�¼�е�����
     */
    public int getSectionTop()
    {
        return (isLastPage()) ? rowCount : (getSectionFoot() + pageSize);
    }

    /**
     * Description: <br>
     * ���õ�ǰҳ <br>
     * [�����б�˵��ÿ��������;] ��@return ����û������˷Ƿ���ֵ������false�����򽫵�ǰҳ������Ϊ�û������ֵ��������true
     * 
     * @param nowPage
     */
    public boolean setNowPage(int nowPage)
    {
        // ����û������ֵΪ0���߸��������ߴ�����ҳ����ֵ���򷵻�false
        if ( (nowPage < 1))
        {
            this.nowPage = 1;

            return false;
        }

        if (nowPage > pageCount)
        {
            this.nowPage = pageCount;

            return false;
        }

        this.nowPage = nowPage;

        return true;
    }

    /**
     * Description: <br>
     * ��õ�ǰҳ�� <br>
     * ��@return ��ǰ��
     */
    public int getNowPage()
    {
        return nowPage;
    }
}
