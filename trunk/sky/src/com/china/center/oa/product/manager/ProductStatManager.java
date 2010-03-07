/**
 * 
 */
package com.china.center.oa.product.manager;

import java.util.List;

import net.sourceforge.sannotations.annotation.Bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.china.center.common.ConditionParse;
import com.china.center.common.MYException;
import com.china.center.oa.constant.ProductConstant;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.bean.ProductStatBean;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.dao.ProductStatDAO;
import com.china.center.oa.publics.dao.CommonDAO2;
import com.china.center.tools.TimeTools;

/**
 * @author ZHUZHU
 *
 */
@Exceptional
@Bean(name = "productStatManager")
public class ProductStatManager
{
    private final Log _logger = LogFactory.getLog(getClass());
    
    private ProductStatDAO productStatDAO = null;
    
    private ProductDAO productDAO = null;
    
    private CommonDAO2 commonDAO2 = null;
    
    public ProductStatManager()
    {
    }
    
    /**
     * ��Ʒͳ��
     */
    @Transactional(rollbackFor = MYException.class)
    public void statProduct()
    {
        String logTime = TimeTools.now_short();
        
        productStatDAO.deleteEntityBeansByFK(logTime);
        
        List<ProductStatBean> statResultList = productStatDAO.queryStatProductByCondition();
        
        for (ProductStatBean productStatBean : statResultList)
        {
            String productId = productStatBean.getProductId();
            
            ProductBean productBean = productDAO.find(productId);
            
            if (productBean == null)
            {
                _logger.warn("warn in statProduct,product not found in table:"
                        + productId);
                
                continue;
            }
            
            productStatBean.setId(commonDAO2.getSquenceString20());
            
            productStatBean.setLogTime(logTime);
            
            productStatBean.setSailAvg(productStatBean.getSailAmount()
                    / ProductConstant.STAT_DAYS);
            
            if (productStatBean.getSailAvg() == 0
                    && productStatBean.getSailAmount() > (ProductConstant.STAT_DAYS / 2))
            {
                productStatBean.setSailAvg(1);
            }
            
            productStatBean.setDescription("�Զ�ͳ�ƽ��");
            
            //��ʼ���� �����Ʒ�Ŀ���� < (������+������)*��ƽ�������� + ��֪����������δ��������ʼԤ��
            
            int sumAmount = productStatDAO.sumProductAmountByProductId(productId);
            
            int notSail = productStatDAO.sumNotSailProduct(productId);
            
            productStatBean.setInventoryAmount(sumAmount);
            
            int cum = (productBean.getMakeDays() + productBean.getFlowDays())
                    * productStatBean.getSailAvg() + notSail;
            
            if (sumAmount < cum)
            {
                productStatBean.setStatus(ProductConstant.STAT_STATUS_ALERT);
                
                productStatBean.setSubtractAmount(cum - sumAmount);
                
                // TODO ���Ͷ��ź��ʼ�����Ӫ�ܼ�
            }
        }
        
        productStatDAO.saveAllEntityBeans(statResultList);
    }
    
    /**
     * ɾ����ʷ����
     */
    @Transactional(rollbackFor = MYException.class)
    public void deleteHistoryData()
    {
        //ɾ����ȥ6���µ�����
        String logTime = TimeTools.getDateFullString(-6 * 30,
                TimeTools.SHORT_FORMAT);
        
        ConditionParse condition = new ConditionParse();
        
        condition.addWhereStr();
        
        condition.addCondition("logTime", "<=", logTime);
        
        productStatDAO.deleteEntityBeansByCondition(condition);
    }
    
    public ProductStatDAO getProductStatDAO()
    {
        return productStatDAO;
    }
    
    public void setProductStatDAO(ProductStatDAO productStatDAO)
    {
        this.productStatDAO = productStatDAO;
    }
    
    public CommonDAO2 getCommonDAO2()
    {
        return commonDAO2;
    }
    
    public void setCommonDAO2(CommonDAO2 commonDAO2)
    {
        this.commonDAO2 = commonDAO2;
    }
    
    public ProductDAO getProductDAO()
    {
        return productDAO;
    }
    
    public void setProductDAO(ProductDAO productDAO)
    {
        this.productDAO = productDAO;
    }
}
