package com.china.center.tools;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * ��Դ��λ
 *
 * @author zhuzhu
 * @version 2007-7-23
 * @see ResourceLocator
 * @since
 */
public class ResourceLocator
{

    public static final String CLASSPATH_URL_PREFIX = "classpath:";

    /**
     * ȡ�������ļ���InputStream
     * Description: �����ļ�������classpath��ͷ��ʹ����·�������ļ���Ҳ����ֱ��ʹ��file:��ͷʹ����ԣ����ԣ�·��
     * �÷�������������������ͬ�ļ��ط���������ȡ��Դ��InputStream<br>
     *
     * @param location
     * @return InputStream
     * @throws FileNotFoundException  
     */
    public static InputStream getResource(String location)
        throws FileNotFoundException
    {
        if (location == null)
        {
            throw new FileNotFoundException("Filename could not be NULL");
        }
        //����classpath:��ͷ��·��
        if (location.startsWith(CLASSPATH_URL_PREFIX))
        {
            String path = location.substring(CLASSPATH_URL_PREFIX.length());
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream is = null;
            is = classLoader.getResourceAsStream(path);

            if (is == null)
            {
                throw new FileNotFoundException(path
                                                + " cannot be opened because it does not exist");
            }
            return is;
        }
        else
        {

            try
            {
                //������file:��ͷ��·��
                URL url = new URL(location);
                File file = new File(url.getFile());
                if (file.exists())
                {
                    return new FileInputStream(file);
                }
                else
                {
                    throw new FileNotFoundException(
                        location + " cannot be opened because it does not exist");
                }
            }
            catch (MalformedURLException e)
            {
                //���������֧����Ϊ�ļ�λ�ü�û����file:��ͷ��Ҳû����classpath:��ͷ,ֱ�����ļ����Զ�ȡ
                if (location.startsWith("/"))
                {
                    location = location.substring(1);
                }
                File file = new File(location);
                if (file.exists())
                {
                    return new FileInputStream(file);
                }
                else
                {
                    throw new FileNotFoundException(
                        location + " cannot be opened because it does not exist");
                }
            }

        }
    }
}
