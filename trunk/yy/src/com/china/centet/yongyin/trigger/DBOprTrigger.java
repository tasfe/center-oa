/**
 * File Name: DBOprTrigger.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * Creater: zhuAchen<br>
 * CreateTime: 2008-2-17<br>
 * Grant: open source to everybody
 */
package com.china.centet.yongyin.trigger;


import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.china.center.fileWriter.WriteFile;
import com.china.center.fileWriter.WriteFileFactory;
import com.china.center.tools.CMDTools;
import com.china.center.tools.FileTools;
import com.china.center.tools.TimeTools;


/**
 * ���ݿⱸ�ݵ���
 * 
 * @author zhuzhu
 * @version 2008-2-17
 * @see
 * @since
 */
public class DBOprTrigger
{
    private final Log _logger = LogFactory.getLog(getClass());

    private final Log logger1 = LogFactory.getLog("sec");

    /**
     * �ű�·��
     */
    private String script = "";

    /**
     * ����·��
     */
    private String db_bak = "";

    /**
     * mysql�İ�װ·��
     */
    private String mysql_root = "";

    /**
     * �û�
     */
    private String user = "";

    /**
     * ����
     */
    private String password = "";

    /**
     * ���ݿ�����
     */
    private String db_name = "";

    /**
     * default constructor
     */
    public DBOprTrigger()
    {}

    /**
     * ÿ6Сʱ����һ�����ݿ�
     */
    public void bakDB()
    {
        // �����ɽű�
        WriteFile write = WriteFileFactory.getMyTXTWriter();

        String filePath = "";
        try
        {
            FileTools.mkdirs(this.script);

            write.openFile(this.script + '/' + "bak.bat");

            String content = "path " + this.mysql_root;

            String dirPath = this.db_bak + '/' + mkdir();

            filePath = dirPath + "/yongyin.dump";

            String content1 = "mysqldump -u"
                              + this.user
                              + " -p"
                              + this.password
                              + " "
                              + this.db_name
                              + " --skip-opt --create-option --set-charset --default-character-set=gbk -e --max_allowed_packet=1047552 --net_buffer_length=16384>"
                              + filePath;

            write.writeLine(content);

            write.writeLine(content1);

            write.close();
        }
        catch (IOException e)
        {
            _logger.error(e, e);
        }

        // ִ�б������ݿ�
        if (CMDTools.cmd(this.script + '/' + "bak.bat"))
        {
            logger1.info("�������ݿ�ɹ�:" + filePath);
        }
        else
        {
            logger1.info("�������ݿ�ʧ��:" + filePath);
        }

        String delPath = this.db_bak + '/' + TimeTools.getSpecialDateString( -15, "yyyy/MM/dd");

        try
        {
            FileTools.delete(delPath);

            logger1.info("ɾ���������ݿ�ɹ�:" + delPath);
        }
        catch (IOException e)
        {
            _logger.error(e, e);
        }
    }

    private String mkdir()
    {
        String path = TimeTools.now("yyyy/MM/dd");

        FileTools.mkdirs(this.db_bak + '/' + path);

        return path;
    }

    /**
     * @return the script
     */
    public String getScript()
    {
        return script;
    }

    /**
     * @return the db_bak
     */
    public String getDb_bak()
    {
        return db_bak;
    }

    /**
     * @return the mysql_root
     */
    public String getMysql_root()
    {
        return mysql_root;
    }

    /**
     * @return the user
     */
    public String getUser()
    {
        return user;
    }

    /**
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * @return the db_name
     */
    public String getDb_name()
    {
        return db_name;
    }

    /**
     * @param script
     *            the script to set
     */
    public void setScript(String script)
    {
        this.script = script;
    }

    /**
     * @param db_bak
     *            the db_bak to set
     */
    public void setDb_bak(String db_bak)
    {
        this.db_bak = db_bak;
    }

    /**
     * @param mysql_root
     *            the mysql_root to set
     */
    public void setMysql_root(String mysql_root)
    {
        this.mysql_root = mysql_root;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(String user)
    {
        this.user = user;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * @param db_name
     *            the db_name to set
     */
    public void setDb_name(String db_name)
    {
        this.db_name = db_name;
    }
}
