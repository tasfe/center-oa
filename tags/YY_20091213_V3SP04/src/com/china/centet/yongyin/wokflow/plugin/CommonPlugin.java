/**
 *
 */
package com.china.centet.yongyin.wokflow.plugin;


import com.china.center.common.MYException;
import com.china.centet.yongyin.bean.FlowViewerBean;
import com.china.centet.yongyin.bean.FlowTokenBean;


/**
 * ��ɫ��Plgin
 * 
 * @author Administrator
 */
public interface CommonPlugin
{
    /**
     * ��������������
     * 
     * @param instanceId
     * @param token
     * @throws MYException
     */
    void processFlowInstanceBelong(String instanceId, FlowTokenBean token)
        throws MYException;

    /**
     * ��ô�����
     * 
     * @param processerId
     * @return
     */
    String getProcesserName(String processerId);

    /**
     * �������̵Ĳ�����
     * 
     * @param instanceId
     * @param viewer
     * @throws MYException
     */
    void processFlowInstanceViewer(String instanceId, FlowViewerBean viewer)
        throws MYException;
}
