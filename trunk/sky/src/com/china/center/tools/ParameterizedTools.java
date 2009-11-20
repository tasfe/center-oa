/**
 * 
 */
package com.china.center.tools;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * ���͹���
 * @author Administrator
 *
 */
public abstract class ParameterizedTools
{
    /**
     * ��÷��͵ķ���ֵ����
     * @param method
     * @return
     */
    public static Class[] getReturnType(Method method)
    {
        Type types = method.getGenericReturnType();
        
        if (types instanceof Class)
        {
            return null;
        }
        
        ParameterizedType type = (ParameterizedType) types;
        
        return getTypeClass(type);
    }
    
    public static Class[] getParamterType(Method method, int paramIndex)
    {
        Type[] types = method.getGenericParameterTypes();
        
        if (paramIndex <= (types.length - 1) && paramIndex >= 0)
        {
            if (types[paramIndex] instanceof Class)
            {
                return null;
            }
            
            ParameterizedType type = (ParameterizedType) types[paramIndex];
            
            return getTypeClass(type);
        }
        
        return null;
    }
    
    /**
     * ���Ψһ�ķ���ֵ
     * @param method
     * @return
     */
    public static Class getOnlyParamterType(Method method, int paramIndex)
    {
        Class[] re = getParamterType(method, paramIndex);
        
        if (re == null)
        {
            return null;
        }
        
        return re[0];
    }
    
    /**
     * ͨ��type��÷���ֵ
     * @param type
     * @return
     */
    private static Class[] getTypeClass(ParameterizedType type)
    {
        Type[] tt = type.getActualTypeArguments();
        
        if (tt != null && tt.length != 0)
        {
            Class[] clazs = new Class[tt.length];
            
            int i = 0;
            for (Type type2 : tt)
            {
                if (type2 instanceof Class)
                {
                    clazs[i++] = (Class) type2;
                }
                else if (type2 instanceof ParameterizedType)
                {
                    ParameterizedType temp = (ParameterizedType)type2;
                    
                    clazs[i++] = (Class)temp.getRawType();
                }
                else
                {
                    clazs[i++] = null;
                }
            }
            
            return clazs;
        }
        
        return null;
    }
    
    /**
     * ���Ψһ�ķ���ֵ
     * @param method
     * @return
     */
    public static Class getOnlyReturnType(Method method)
    {
        Class[] re = getReturnType(method);
        
        if (re == null)
        {
            return null;
        }
        
        return re[0];
    }
}
