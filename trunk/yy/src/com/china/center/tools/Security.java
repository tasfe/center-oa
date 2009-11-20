/*
 * �ļ�����Security
 * ��Ȩ��Copyright by www.centerchina.com
 * ������
 * �޸��ˣ�public1247
 * �޸�ʱ�䣺2006-4-8
 * ���ٵ��ţ�
 * �޸ĵ��ţ�
 * �޸����ݣ�
 */
package com.china.center.tools;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * ������
 * 
 * @author public1247
 * @version
 * @see Securitys
 * @since
 */
public final class Security
{
    /**
     * Description: �õ�һ���ַ������ܺ�Ľ��<br>
     * [�����б�˵��ÿ��������;]
     * 
     * @param s
     * @return String
     */
    public static final String getSecurity(String s)
    {
        final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c',
            'd', 'e', 'f'};
        try
        {
            final byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            final byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j << 1];
            int k = 0;
            for (int i = 0; i < j; i++ )
            {
                byte byte0 = md[i];
                str[k++ ] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++ ] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        }
        catch (NoSuchAlgorithmException e)
        {
            return null;
        }
    }

    /**
     * Automatically generated method: Security
     */
    private Security()
    {

    }

    public static void main(String[] args)
    {
        System.out.println(getSecurity("123456"));
    }

}
