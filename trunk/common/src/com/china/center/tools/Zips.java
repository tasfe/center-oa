package com.china.center.tools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
 * @author zhu
 * @version 2006-7-23
 * @see Zips
 * @since
 */

public class Zips
{
	/**
	 * ȡ��ָ��Ŀ¼�µ������ļ��б�������Ŀ¼.
	 *
	 * @param baseDir
	 *            File ָ����Ŀ¼
	 * @return ����java.io.File��List
	 */
	private static List<?> getSubFiles(File baseDir)
	{
		List<Object> ret = new ArrayList<Object>();
		File[] tmp = baseDir.listFiles();
		for (int i = 0; i < tmp.length; i++)
		{
			if (tmp[i].isFile())
			{
				ret.add(tmp[i]);
			}
			if (tmp[i].isDirectory())
			{
				ret.addAll(getSubFiles(tmp[i]));
			}
		}
		return ret;
	}

	/**
	 * ������Ŀ¼��������һ���ļ��������·��������zip�ļ��е�·��.
	 *
	 * @param baseDir
	 *            java.lang.String ��Ŀ¼
	 * @param realFileName
	 *            java.io.File ʵ�ʵ��ļ���
	 * @return ����ļ���
	 */
	private static String getAbsFileName(String baseDir, File realFileName)
	{
		File real = realFileName;
		File base = new File(baseDir);
		String ret = real.getName();
		while (true)
		{
			real = real.getParentFile();
			if (real == null)
				break;
			if (real.equals(base))
			{
				break;
			}
			else
			{
				ret = real.getName() + "/" + ret;
			}
		}
		return ret;
	}

	/**
	 * ��ѹ�ļ�
	 *
	 * @param path
	 * @param file
	 * @throws IOException
	 */
	public static void unZipFile(String path, File file) throws IOException
	{
		ZipFile zfile = new ZipFile(file);

		Enumeration enu = zfile.getEntries();

		path = FileTools.getCommonPath(path.trim());

		if (!path.endsWith("/"))
		{
			path += "/";
		}

		while (enu.hasMoreElements())
		{
			ZipEntry entry = (ZipEntry) enu.nextElement();

			if (entry.isDirectory())
			{
				FileTools.mkdirs(path + entry.getName());
			}
			else
			{
				File innerFile = FileTools.createFile(path + entry.getName());

				InputStream inn = zfile.getInputStream(entry);

				OutputStream out = new FileOutputStream(innerFile);

				UtilStream us = null;

				try
				{
					us = new UtilStream(inn, out);

					us.copyStream();
				}
				catch (Exception e)
				{
				}
				finally
				{
					if (us != null)
					{
						try
						{
							us.close();
						}
						catch (IOException e)
						{
						}
					}
				}

			}
		}

		zfile.close();
	}

	public static void zipFloder(String path, OutputStream out) throws IOException
	{
		// ѹ��baseDir�������ļ���������Ŀ¼
		String baseDir = path;
		List<?> fileList = getSubFiles(new File(baseDir));

		// ѹ���ļ���
		ZipOutputStream zos = new ZipOutputStream(out);
		zos.setEncoding("GBK");

		ZipEntry ze = null;
		byte[] buf = new byte[1024];
		int readLen = 0;
		for (int i = 0; i < fileList.size(); i++)
		{
			File f = (File) fileList.get(i);

			// ����һ��ZipEntry��������Name��������һЩ����
			ze = new ZipEntry(getAbsFileName(baseDir, f));
			ze.setSize(f.length());
			ze.setTime(f.lastModified());

			// ��ZipEntry�ӵ�zos�У���д��ʵ�ʵ��ļ�����
			zos.putNextEntry(ze);
			InputStream is = new BufferedInputStream(new FileInputStream(f));
			while ((readLen = is.read(buf, 0, 1024)) != -1)
			{
				zos.write(buf, 0, readLen);
				zos.flush();
			}
			is.close();
		}

		zos.close();
	}

	public static void main(String[] args) throws IOException
	{
		unZipFile("c:/unzip", new File(
				"C:/TDDOWNLOAD/com.atlassw.tools.eclipse.checkstyle_4.4.1-bin.zip"));
	}
}
