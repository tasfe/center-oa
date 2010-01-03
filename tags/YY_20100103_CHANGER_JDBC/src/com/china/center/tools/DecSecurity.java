/**
 * �ļ�����DecSecurity.java
 * ��Ȩ��Copyright by www.centerchina.com
 * ������
 * �޸��ˣ�zhuzhu
 * �޸�ʱ�䣺2006-6-17
 * ���ٵ��ţ�
 * �޸ĵ��ţ�
 * �޸����ݣ�
 */

package com.china.center.tools;


import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * ��des�����û���id����ò�����
 * 
 * @author zhuzhu
 * @version 2006-6-17
 * @see DecSecurity
 * @since
 */

public class DecSecurity
{
    private static String algorithm = "DES"; // ���� �����㷨,���� DES,DESede,Blowfish

    private static String key = "A68-q%k)"; // ��Կ�ַ���,������8�ֽ�,�����DESede������24�ֽ�

    private static final char[] hexDigits = {'m', 'k', 'p', '3', '4', '5', 'a', '7', '8', 'f',
        '6', 'b', 'c', 'd', 'x', '9'};

    // ��ʵ����ģʽ
    public static final DecSecurity INSTANCE = new DecSecurity();

    // ������
    private DecSecurity()
    {}

    private static boolean needSecurity = true;

    /**
     * ��DES���� String Ҫ���ܵ��ַ���
     */
    public static String encrypt(int num)
    {
        return encrypt(String.valueOf(num));
    }

    public static String encrypt(String enStr)
    {
        return encrypt(enStr, DecSecurity.key);
    }

    /**
     * ��DES���� String Ҫ���ܵ��ַ���
     */
    public static String encrypt(String enStr, String keygen)
    {
        if (StringTools.isNullOrNone(enStr))
        {
            return "";
        }

        if ( !needSecurity)
        {
            return enStr;
        }

        // ����°�ȫ�㷨,�����JCE��Ҫ������ӽ�ȥ
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        String myinfo = "Ҫ���ܵ���Ϣ";
        byte[] cipherByte = null; // ���ܺ������
        if (enStr != null)
        {
            myinfo = enStr;
        }
        try
        {
            SecretKey deskey = new SecretKeySpec(keygen.getBytes(), algorithm);
            // ����
            Cipher c1 = Cipher.getInstance(algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            cipherByte = c1.doFinal(myinfo.getBytes());

        }
        catch (java.security.NoSuchAlgorithmException e1)
        {
            e1.printStackTrace();
        }
        catch (javax.crypto.NoSuchPaddingException e2)
        {
            e2.printStackTrace();
        }
        catch (java.lang.Exception e3)
        {
            e3.printStackTrace();
        }

        return getString(cipherByte);
    }

    public static String decrypt(String str)
    {
        return decrypt(str, DecSecurity.key);
    }

    /**
     * ��DES���� String Ҫ���ܵ����� SecretKey ��Կ
     */
    public static String decrypt(String str, String keygen)
    {
        if (StringTools.isNullOrNone(str))
        {
            return "";
        }

        if ( !needSecurity)
        {
            return str;
        }

        String result = "";
        try
        {
            SecretKey deskey = new SecretKeySpec(keygen.getBytes(), algorithm);

            byte[] deStr = getbytes(str);
            // ����°�ȫ�㷨,�����JCE��Ҫ������ӽ�ȥ
            Security.addProvider(new com.sun.crypto.provider.SunJCE());

            // ����
            Cipher c1 = Cipher.getInstance(algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            byte[] clearByte = c1.doFinal(deStr);
            result = new String(clearByte);
        }
        catch (Exception e)
        {}

        return result;
    }

    /**
     * Description:ͨ��ɢ��char����bytes <br>
     * 
     * @param b
     * @return String
     */
    private static String getString(byte[] b)
    {
        char[] str = new char[b.length << 1];
        int k = 0;
        for (int i = 0; i < b.length; i++ )
        {
            byte bb = b[i];
            str[k++ ] = hexDigits[bb >>> 4 & 0xf];
            str[k++ ] = hexDigits[bb & 0xf];
        }
        return new String(str);
    }

    /**
     * Description:ͨ�����ܵ�String���bytes <br>
     * 
     * @param s
     * @return byte[]
     */
    private static byte[] getbytes(String s)
    {
        byte[] b = new byte[s.length() >> 1];
        int j = 0;
        for (int i = 0; i < s.length(); i = i + 2)
        {
            b[j++ ] = (byte) ( (getPos(s.charAt(i)) << 4) | (getPos(s.charAt(i + 1))));
        }
        return b;
    }

    private static int getPos(char c)
    {
        for (int i = 0; i < hexDigits.length; i++ )
        {
            if (hexDigits[i] == c)
            {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args)
        throws Exception
    {
        String ss = DecSecurity.encrypt("123456789012345678901234567890AP", "12345678");
        System.out.println(ss);
        System.out.println(DecSecurity.decrypt(ss, "12345678"));
    }
}