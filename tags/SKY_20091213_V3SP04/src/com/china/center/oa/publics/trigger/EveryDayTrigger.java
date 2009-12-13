/**
 * File Name: EveryDayTrigger.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2009-2-22<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.trigger;


import java.io.File;

import net.sourceforge.sannotations.annotation.Bean;
import net.sourceforge.sannotations.annotation.Property;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.china.center.common.MYException;
import com.china.center.oa.constant.SysConfigConstant;
import com.china.center.oa.customer.manager.CustomerCheckManager;
import com.china.center.oa.examine.manager.ProductExamineManager;
import com.china.center.oa.job.dao.SnapshotDAO;
import com.china.center.oa.note.manager.NoteManager;
import com.china.center.oa.publics.dao.ParameterDAO;
import com.china.center.tools.FileTools;
import com.china.center.tools.RegularExpress;
import com.china.center.tools.TimeTools;


/**
 * EveryDayTrigger
 * 
 * @author ZHUZHU
 * @version 2009-2-22
 * @see EveryDayTrigger
 * @since 1.0
 */
@Bean(name = "everyDayTrigger")
public class EveryDayTrigger
{
    private final Log _logger = LogFactory.getLog(getClass());

    private final Log triggerLog = LogFactory.getLog("trigger");

    private final static String SNAPSHOT = "snapshot";

    private ProductExamineManager productExamineManager = null;

    private CustomerCheckManager customerCheckManager = null;

    private NoteManager noteManager = null;

    private SnapshotDAO snapshotDAO = null;

    private ParameterDAO parameterDAO = null;

    @Property(value = "${froot}")
    private String froot = "";

    /**
     * ÿ���賿1��ִ�� <br>
     * ������Ŀ�Ķ���֤
     */
    public void everyDayCarryWithOutTransactional()
    {
        try
        {
            // ���²�Ʒ�Ŀ���״̬
            int count = productExamineManager.updateAllStatus();

            triggerLog.info("���²�Ʒ�Ŀ���״̬�и�����" + count + "��״̬");
        }
        catch (Throwable e)
        {
            _logger.error(e, e);
        }

        try
        {
            noteManager.moveTimeoutData();
        }
        catch (Throwable e)
        {
            _logger.error(e, e);
        }

        // carry snapshot
        carrySnapshot();
    }

    /**
     * carrySnapshot
     */
    private void carrySnapshot()
    {
        try
        {
            String rootPath = mkdir();

            String filePath = rootPath + '/' + TimeTools.now_short() + ".xls";

            String filePathCenter = rootPath + '/' + TimeTools.now_short() + ".wl";

            // first delete file
            File temp = new File(filePath);

            temp.delete();

            temp = new File(filePathCenter);

            temp.delete();

            snapshotDAO.snapshotProductNumber(filePath, filePathCenter);

            triggerLog.info("�ɹ��������ݿ���:" + filePath);

            triggerLog.info("�ɹ��������ݿ���:" + filePathCenter);

            // ɾ�����ݿ���
            File file = new File(rootPath);

            File[] listFiles = file.listFiles();

            for (File eachfile : listFiles)
            {
                String filaName = FileTools.getFileName(eachfile);

                if (filaName.indexOf(".") == -1)
                {
                    deleteFile(eachfile);

                    continue;
                }

                String[] split = filaName.split("\\.");

                if ( !RegularExpress.isShortDate(split[0]))
                {
                    deleteFile(eachfile);

                    continue;
                }

                int cdate = TimeTools.cdate(TimeTools.now_short(), split[0]);

                // delete last 5 days before
                if (cdate >= parameterDAO.getInt(SysConfigConstant.SNAPSHO_DAYS))
                {
                    eachfile.delete();

                    triggerLog.warn("���ճ�ʱɾ��:" + eachfile.getAbsolutePath());
                }
            }
        }
        catch (Throwable e)
        {
            _logger.error(e, e);
        }
    }

    /**
     * deleteFile
     * 
     * @param eachfile
     */
    private void deleteFile(File eachfile)
    {
        eachfile.delete();

        triggerLog.warn("�쳣ɾ��:" + eachfile.getAbsolutePath());
    }

    private String mkdir()
    {
        String path = FileTools.formatPath(this.froot) + SNAPSHOT + '/' + "product";

        FileTools.mkdirs(path);

        return path;
    }

    /**
     * ÿ���賿1��10��ִ�� <br>
     * ÿ��ִ��,������EveryDayTrigger��֤
     */
    @Transactional(rollbackFor = {MYException.class})
    public void everyDayCarryWithTransactional()
    {

    }

    /**
     * ÿСʱִ��һ�� <br>
     * ������Ŀ�Ķ���֤
     */
    public void everyHourCarryWithOutTransactional()
    {
        try
        {
            int count = customerCheckManager.autoChangeStatus();

            triggerLog.info("���¿ͻ���Ϣ�˶�����״̬�и�����" + count + "��״̬");
        }
        catch (Throwable e)
        {
            _logger.error(e, e);
        }
    }

    /**
     * @return the productExamineManager
     */
    public ProductExamineManager getProductExamineManager()
    {
        return productExamineManager;
    }

    /**
     * @param productExamineManager
     *            the productExamineManager to set
     */
    public void setProductExamineManager(ProductExamineManager productExamineManager)
    {
        this.productExamineManager = productExamineManager;
    }

    /**
     * @return the customerCheckManager
     */
    public CustomerCheckManager getCustomerCheckManager()
    {
        return customerCheckManager;
    }

    /**
     * @param customerCheckManager
     *            the customerCheckManager to set
     */
    public void setCustomerCheckManager(CustomerCheckManager customerCheckManager)
    {
        this.customerCheckManager = customerCheckManager;
    }

    /**
     * @return the noteManager
     */
    public NoteManager getNoteManager()
    {
        return noteManager;
    }

    /**
     * @param noteManager
     *            the noteManager to set
     */
    public void setNoteManager(NoteManager noteManager)
    {
        this.noteManager = noteManager;
    }

    /**
     * @return the snapshotDAO
     */
    public SnapshotDAO getSnapshotDAO()
    {
        return snapshotDAO;
    }

    /**
     * @param snapshotDAO
     *            the snapshotDAO to set
     */
    public void setSnapshotDAO(SnapshotDAO snapshotDAO)
    {
        this.snapshotDAO = snapshotDAO;
    }

    /**
     * @return the froot
     */
    public String getFroot()
    {
        return froot;
    }

    /**
     * @param froot
     *            the froot to set
     */
    public void setFroot(String froot)
    {
        this.froot = froot;
    }

    /**
     * @return the parameterDAO
     */
    public ParameterDAO getParameterDAO()
    {
        return parameterDAO;
    }

    /**
     * @param parameterDAO
     *            the parameterDAO to set
     */
    public void setParameterDAO(ParameterDAO parameterDAO)
    {
        this.parameterDAO = parameterDAO;
    }
}
