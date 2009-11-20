/*
 * File Name: FileTools.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-5-15
 * Grant: open source to everybody
 */
package com.china.center.tools;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


/**
 * ���ֲ���ϵͳ���ļ�������
 * 
 * @author zhuzhu
 * @version 2007-5-15
 * @see
 * @since
 */
public class FileTools
{
    /**
     * ��������
     */
    private static int buffer = 102400;

    // ���ݵ�ǰ��ϵͳ��ʶ�� / ϵͳͳһʹ�� ��
    public final static String file_separator = "/";

    /**
     * Description:�����ļ�(����ͬʱ�����ļ���Ҫ���ļ���) <br>
     * 
     * @param path
     * @return File
     */
    public static File createFile(String path)
    {
        // �ȸ�ʽ��·��
        if (path == null || "".equals(path))
        {
            return null;
        }

        path = getPath(path);
        String mdr = path.substring(0, path.lastIndexOf(file_separator));

        File file = new File(mdr);

        if ( !file.exists())
        {
            // ����·��
            file.mkdirs();
        }

        return new File(path);
    }

    public static boolean createFolders(String path)
    {
        // �ȸ�ʽ��·��
        if (path == null || "".equals(path))
        {
            return false;
        }

        path = getPath(path);

        File file = new File(path);

        if ( !file.exists())
        {
            // ����·��
            file.mkdirs();
        }
        return true;
    }

    /**
     * Description: ��ñ�����ʶ��path(����ƽ̨֮����ַ�������)<br>
     * 
     * @param path
     * @return String
     */
    public static String getPath(String path)
    {
        path = path.replaceAll("\\\\", file_separator);

        try
        {
            path = new String(path.getBytes(Constant.DEFAULT_CHAESET));
        }
        catch (UnsupportedEncodingException e)
        {}

        return path;
    }

    public static void delete(String path)
        throws IOException
    {
        deleteIn(getLocalString(path));
    }

    public static File newFile(String path)
    {
        return new File(getLocalString(path));
    }

    public static String getFileName(File file)
    {
        if (file == null)
        {
            return "";
        }

        return StringTools.getDefaultString(file.getName());
    }

    public static String getFileName(String allPath)
    {
        File file = new File(allPath);

        return getFileName(file);
    }

    /**
     * ����ļ���������ʵ����
     * 
     * @param allPath
     * @return
     */
    public static String getAbsoluteFileName(String allPath)
    {
        allPath = getCommonPath(allPath);

        String[] path = allPath.split("/");

        if (path.length < 2)
        {
            return allPath;
        }

        return path[path.length - 1];
    }

    public static String getFilePath(File file)
    {
        if (file == null)
        {
            return "";
        }

        return StringTools.getDefaultString(file.getPath());
    }

    public static String getAbsolutePath(File file)
    {
        if (file == null)
        {
            return "";
        }

        return StringTools.getDefaultString(file.getAbsolutePath());
    }

    /**
     * ֱ�ӿ���
     * 
     * @param src
     * @param dir
     * @return
     * @throws IOException
     */
    public static int copy(String src, String dir)
        throws IOException
    {
        return move(src, dir, false, false);
    }

    /**
     * �����ļ�
     * 
     * @param src
     * @param dir
     * @return
     * @throws IOException
     */
    public static int cut(String src, String dir)
        throws IOException
    {
        return move(src, dir, false, true);
    }

    /**
     * ���е�Ŀ��Ŀ¼������ǰ�����Ŀ��Ŀ¼
     * 
     * @param src
     * @param dir
     * @return
     * @throws IOException
     */
    public static int cutAndClearTar(String src, String dir)
        throws IOException
    {
        return move(src, dir, true, true);
    }

    /**
     * copyԴĿ¼��Ŀ��Ŀ¼(���Ŀ��Ŀ¼)
     * 
     * @param src
     *            ԴĿ¼
     * @param dir
     *            Ŀ��Ŀ¼
     * @return �������ļ���
     * @throws IOException
     */
    public static int copyAndClearTar(String src, String dir)
        throws IOException
    {
        return move(src, dir, true, false);
    }

    public static boolean mkdirs(String path)
    {
        File file = new File(getLocalString(path));

        return file.mkdirs();
    }

    public static long fileSize(String path)
    {
        File file = new File(getLocalString(path));

        if ( !file.exists())
        {
            return 0l;
        }

        if (file.isFile())
        {
            return file.length();
        }

        return lengthIn(getLocalString(path));

    }

    public static boolean copyFile(File srcFile, File dirFile)
        throws IOException
    {
        FileInputStream in = new FileInputStream(srcFile);

        FileOutputStream out = new FileOutputStream(dirFile);

        UtilStream util = new UtilStream(in, out, buffer);

        util.copyAndCloseStream();

        return true;
    }

    public static boolean copyFile(String srcFile, String dir)
        throws IOException
    {
        File file = new File(srcFile);

        FileInputStream in = new FileInputStream(file);

        mkdirs(dir);

        FileOutputStream out = new FileOutputStream(new File(dir, file.getName()));

        UtilStream util = new UtilStream(in, out, buffer);

        util.copyAndCloseStream();

        return true;
    }

    public static boolean copyFile2(String srcFile, String dir)
        throws IOException
    {
        File file = new File(srcFile);

        FileInputStream in = new FileInputStream(file);

        FileOutputStream out = new FileOutputStream(new File(dir));

        UtilStream util = new UtilStream(in, out, buffer);

        util.copyAndCloseStream();

        return true;
    }

    /**
     * ȡ���ļ��ĺ�׺
     * 
     * @param fileName(δת���)
     * @return
     */
    public static String getFilePostfix(String fileName)
    {
        fileName = StringTools.getDefaultString(fileName);

        if (fileName.indexOf('.') == -1 || fileName.endsWith("."))
        {
            return null;
        }

        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }

    public static String getLocalString(String path)
    {
        return StringTools.getLocalString(path);
    }

    /**
     * get common path
     * 
     * @param path
     * @return
     */
    public static String getCommonPath(String path)
    {
        if (StringTools.isNullOrNone(path))
        {
            return "";
        }

        return path.replaceAll("\\\\", "/");
    }

    /**
     * get format path
     * 
     * @param path
     * @return
     */
    public static String formatPath(String path)
    {
        path = getCommonPath(path);

        if (path.endsWith("/"))
        {
            return path;
        }
        else
        {
            return path + "/";
        }
    }

    /**
     * get format path
     * 
     * @param path
     * @return
     */
    public static String formatPath2(String path)
    {
        path = getCommonPath(path);

        if (path.endsWith("/"))
        {
            return path.substring(0, path.length() - 1);
        }
        else
        {
            return path;
        }
    }

    private static long lengthIn(String path)
    {
        File file = new File(path);

        File[] f = file.listFiles();

        long length = 0l;

        if (f != null)
        {
            for (int i = 0; i < f.length; i++ )
            {
                if (f[i].isFile())
                {
                    length += f[i].length();
                }
                else
                {
                    length += lengthIn(f[i].getPath());
                }
            }
        }

        return length;
    }

    private static int copyIn(String src, String dir)
        throws IOException
    {
        int copyFiles = 0;
        File file = new File(src);

        File[] files = file.listFiles();

        if (files == null || files.length == 0)
        {
            return 0;
        }

        for (int i = 0; i < files.length; i++ )
        {
            File temp = new File(dir, files[i].getName());
            if (files[i].isFile())
            {
                copyFile(files[i], temp);
                copyFiles++ ;
            }
            else
            {
                // �����ļ���
                temp.mkdirs();
                copyFiles += copyIn(files[i].getPath(), temp.getPath());
            }
        }

        return copyFiles;
    }

    /**
     * copyԴĿ¼��Ŀ��Ŀ¼
     * 
     * @param src
     *            ԴĿ¼
     * @param dir
     *            Ŀ��Ŀ¼
     * @param delDir
     *            �Ƿ����Ŀ��Ŀ¼
     * @param delSrc
     *            �Ƿ����ԴĿ¼
     * @return �������ļ���
     * @throws IOException
     */
    private static int move(String src, String dir, boolean delDir, boolean delSrc)
        throws IOException
    {
        File file = new File(getLocalString(dir));

        if ( !file.exists())
        {
            file.mkdirs();
        }

        // ����ļ�
        if (delDir)
        {
            delete(dir);
        }

        int k = copyIn(getLocalString(src), getLocalString(dir));

        if (delSrc)
        {
            delete(src);
        }

        return k;
    }

    /**
     * pathΪGBK
     * 
     * @param path
     * @throws IOException
     */
    private static void deleteIn(String path)
        throws IOException
    {
        File file = new File(path);

        if (file.isFile())
        {
            file.delete();

            return;
        }

        File[] f = file.listFiles();

        if (f != null)
        {
            for (int i = 0; i < f.length; i++ )
            {
                if (f[i].isFile())
                {
                    f[i].delete();
                }
                else
                {
                    deleteIn(f[i].getPath());
                    f[i].delete();
                }
            }

            file.delete();
        }
    }
}
