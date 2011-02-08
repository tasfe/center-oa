/*
 * File Name: BeanTools.java CopyRight: Copyright by www.center.china
 * Description: Creater: zhuAchen CreateTime: 2007-9-28 Grant: open source to
 * everybody
 */
package com.china.center.jdbc.annosql.tools;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.china.center.jdbc.annosql.ErrorBean;
import com.china.center.jdbc.annosql.ErrorConstant;
import com.china.center.jdbc.annosql.InnerBean;
import com.china.center.jdbc.annosql.MYSqlException;
import com.china.center.jdbc.annotation.Alias;
import com.china.center.jdbc.annotation.CacheRelation;
import com.china.center.jdbc.annotation.Column;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Note;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;


/**
 * BeanTools
 */
public abstract class BeanTools
{
    private static final Log _logger = LogFactory.getLog(BeanTools.class);

    private static Map<String, String> errorMap = new HashMap<String, String>();

    static
    {
        ErrorBean error = new ErrorBean();

        Field[] fs = ErrorBean.class.getFields();

        for (Field field : fs)
        {
            try
            {
                errorMap.put(field.get(error).toString(), field.getName());
            }
            catch (Exception e)
            {
                _logger.warn(e, e);
            }
        }
    }

    /**
     * add blank in the begin and end of the table
     * 
     * @param claz
     * @return
     */
    public static String getTableName2(Class<?> claz)
    {
        return " " + getTableName(claz) + " ";
    }

    /**
     * 获得object的表名
     * 
     * @param claz
     * @return
     */
    public static String getTableName(Class<?> claz)
    {
        Table table = claz.getAnnotation(Table.class);

        String tableName = null;

        // 获得表名
        if (table == null)
        {
            if (isInherit(claz))
            {
                Class sup = claz.getSuperclass();

                if (sup != Object.class)
                {
                    // 递归获得
                    return getTableName(sup);
                }
            }

            tableName = getClassName(claz);
        }
        else
        {
            tableName = table.name();
        }

        return tableName;
    }

    /**
     * 获得修改的头sql
     * 
     * @param claz
     * @return
     */
    public static String getUpdateHead(Class<?> claz)
    {
        return "update " + getTableName(claz) + " " + getClassName(claz) + " ";
    }

    /**
     * MYSQL的删除不能增加表后缀
     * 
     * @param claz
     * @return
     */
    public static String getDeleteHead(Class<?> claz)
    {
        return "delete from " + getTableName(claz) + " ";
    }

    public static String getCountHead(Class<?> claz)
    {
        return "select count(1) from " + getTableName(claz) + " " + getClassName(claz) + " ";
    }

    public static String getSumHead(Class<?> claz, String sumField)
    {
        return "select sum(" + sumField + ") from " + getTableName(claz) + " " + getClassName(claz) + " ";
    }

    /**
     * 获得object的表名
     * 
     * @param claz
     * @return
     */
    public static String getEntryName(Class<?> claz)
    {
        Entity entity = claz.getAnnotation(Entity.class);

        // 获得表名
        if (entity == null)
        {
            return "";
        }
        else
        {
            return entity.name();
        }

    }

    /**
     * 获得object的表名
     * 
     * @param claz
     * @return
     */
    public static boolean isEntry(Class<?> claz)
    {
        Entity entity = claz.getAnnotation(Entity.class);

        // 获得表名
        if (entity == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * 获得object的表名
     * 
     * @param claz
     * @return
     */
    public static boolean isCache(Class<?> claz)
    {
        Entity entity = claz.getAnnotation(Entity.class);

        // 获得表名
        if (entity == null)
        {
            return false;
        }
        else
        {
            return entity.cache();
        }
    }

    /**
     * 从field里面获得columnName
     * 
     * @param field
     * @return
     */
    public static String getColumnName(Field field)
    {
        Column column = field.getAnnotation(Column.class);

        if (column == null)
        {
            return field.getName();
        }
        else
        {
            return column.name();
        }
    }

    /**
     * 从field里面获得columnName
     * 
     * @param field
     * @return
     */
    public static Relationship getRelationship(Field field)
    {
        return field.getAnnotation(Relationship.class);
    }

    /**
     * getAlias
     * 
     * @param field
     * @return
     */
    public static Alias getAlias(Field field)
    {
        return field.getAnnotation(Alias.class);
    }

    /**
     * 从field里面获得columnName
     * 
     * @param field
     * @return
     */
    public static Join getJoin(Field field)
    {
        return field.getAnnotation(Join.class);
    }

    /**
     * 从class里面获得id column(如果有继承那么最近子类的id生效)
     * 
     * @param claz
     * @return
     */
    public static String getIdColumn(Class<?> claz)
    {
        // 获得列名
        List<Field> fields = BeanTools.getClassFields(claz);

        for (Field field : fields)
        {
            if (field.getAnnotation(Id.class) != null)
            {
                return getColumnName(field);
            }
        }

        return null;
    }

    /**
     * 从class里面获得id column(如果有继承那么最近子类的id生效)
     * 
     * @param claz
     * @return
     */
    public static Field getIdField(Class<?> claz)
    {
        // 获得列名
        List<Field> fields = BeanTools.getClassFields(claz);

        for (Field field : fields)
        {
            if (field.getAnnotation(Id.class) != null)
            {
                return field;
            }
        }
        return null;
    }

    /**
     * get id value form bean
     * 
     * @param obj
     * @return
     */
    public static Object getIdValue(Object obj)
    {
        Field idField = getIdField(obj.getClass());

        if (idField == null)
        {
            return null;
        }

        idField.setAccessible(true);

        try
        {
            return idField.get(obj);
        }
        catch (IllegalArgumentException e)
        {
            _logger.error(e, e);
            return null;
        }
        catch (IllegalAccessException e)
        {
            _logger.error(e, e);
            return null;
        }
    }

    /**
     * 从class里面获得id field(如果有继承那么最近子类的id生效)
     * 
     * @param claz
     * @return
     */
    public static String getIdFieldName(Class<?> claz)
    {
        // 获得列名
        List<Field> fields = BeanTools.getClassFields(claz);

        for (Field field : fields)
        {
            if (field.getAnnotation(Id.class) != null)
            {
                return field.getName();
            }
        }

        return null;
    }

    /**
     * 从class里面获得Unique field(如果有继承那么最近子类的Unique生效)
     * 
     * @param claz
     * @return
     */
    public static String getUniqueFieldName(Class<?> claz)
    {
        // 获得列名
        List<Field> fields = BeanTools.getClassFields(claz);

        for (Field field : fields)
        {
            if (field.getAnnotation(Unique.class) != null)
            {
                return field.getName();
            }
        }

        return null;
    }

    /**
     * 从class里面获得Unique field(如果有继承那么最近子类的Unique生效)
     * 
     * @param claz
     * @return
     */
    public static String[] getUniqueFields(Class<?> claz)
    {
        // 获得列名
        List<Field> fields = BeanTools.getClassFields(claz);

        for (Field field : fields)
        {
            Unique unique = field.getAnnotation(Unique.class);

            if (unique != null)
            {
                String[] depend = unique.dependFields();

                String[] result = new String[depend.length + 1];

                result[0] = field.getName();

                for (int i = 1; i < result.length; i++ )
                {
                    result[i] = depend[i - 1];
                }

                return result;
            }
        }

        return new String[] {};
    }

    /**
     * 从class里面获得id field(如果有继承那么最近子类的id生效)
     * 
     * @param claz
     * @return
     */
    public static String getFKFieldName(Class<?> claz, int index)
    {
        // 获得列名
        List<Field> fields = BeanTools.getClassFields(claz);

        for (Field field : fields)
        {
            FK fk = field.getAnnotation(FK.class);
            if (fk != null && fk.index() == index)
            {
                return field.getName();
            }
        }

        return null;
    }

    /**
     * 从class里面获得id field(如果有继承那么最近子类的id生效)
     * 
     * @param claz
     * @return
     */
    public static Html getPropertyHtml(Field field)
    {
        return field.getAnnotation(Html.class);
    }

    /**
     * 获得List<T>
     * 
     * @param <T>
     * @param list
     * @param claz
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws MYSqlException
     */
    public static <T> List<T> getBeans(List<Map> list, Class<T> claz)
        throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        List<T> result = new ArrayList<T>(list.size());

        if (BaseType.isBaseType(claz))
        {
            for (Map<?, ?> map : list)
            {
                T obj = (T)BaseType.getDefaultValue(claz);

                // process base type map only have a key and a value
                for (Map.Entry entry : map.entrySet())
                {
                    obj = (T)BaseType.getValue(entry.getValue(), claz);

                    break;
                }

                result.add(obj);
            }

            return result;
        }
        else
        {
            return BeanUtils.populateListIgnoreByField(list, claz);
        }
    }

    public static <T> T getBean(Map map, Class<T> claz)
        throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
        MYSqlException
    {
        T obj = claz.newInstance();

        BeanUtils.populateIgnorCase(obj, map);

        return obj;
    }

    public static <T> T getBeanWithOutException(Map map, Class<T> claz)
    {
        T obj = null;
        try
        {
            obj = claz.newInstance();
        }
        catch (InstantiationException e)
        {
            _logger.warn(e, e);
            return null;
        }
        catch (IllegalAccessException e)
        {
            _logger.warn(e, e);
            return null;
        }

        try
        {
            BeanUtils.populateIgnorCase(obj, map);
        }
        catch (IllegalAccessException e)
        {
            _logger.warn(e, e);
        }
        catch (InvocationTargetException e)
        {
            _logger.warn(e, e);
        }
        catch (NoSuchMethodException e)
        {
            _logger.warn(e, e);
        }

        return obj;
    }

    public static <T> List<T> getBeansWithOutException(List<Map> list, Class<T> claz)
    {
        try
        {
            return getBeans(list, claz);
        }
        catch (InstantiationException e)
        {
            _logger.warn(e, e);
            return new ArrayList<T>();
        }
        catch (IllegalAccessException e)
        {
            _logger.warn(e, e);
            return new ArrayList<T>();
        }
        catch (InvocationTargetException e)
        {
            _logger.warn(e, e);
            return new ArrayList<T>();
        }
        catch (NoSuchMethodException e)
        {
            _logger.warn(e, e);
            return new ArrayList<T>();
        }
    }

    /**
     * 从class里面获得声明的field(大小写不敏感)
     * 
     * @param name
     * @param clasz
     * @return
     */
    public static Field getFieldIgnoreCase(String name, Class clasz)
    {
        if (name == null)
        {
            return null;
        }

        List<Field> fs = BeanTools.getClassAllFields(clasz);

        for (Field field : fs)
        {
            if (field.getName().equalsIgnoreCase(name))
            {
                return field;
            }
        }

        return null;
    }

    /**
     * 获得error的消息
     * 
     * @param errorNo
     * @return
     */
    public static String getErrorMessage(String errorNo)
    {
        try
        {
            Field field = ErrorConstant.class.getField(errorMap.get(errorNo));

            if (field == null)
            {
                return "";
            }

            Note note = field.getAnnotation(Note.class);

            if (note != null)
            {
                return note.value();
            }
        }
        catch (SecurityException e)
        {
            _logger.warn(e, e);
        }
        catch (NoSuchFieldException e)
        {
            _logger.warn(e, e);
        }

        return "";
    }

    /**
     * 获得class里面的实体column
     * 
     * @param claz
     * @return
     * @throws MYSqlException
     */
    public static List<InnerBean> getClassFieldsInner(Class claz)
        throws MYSqlException
    {
        List<InnerBean> columns = new ArrayList<InnerBean>();

        // 获得列名
        Field[] fields = claz.getDeclaredFields();

        for (Field field : fields)
        {
            if (isIgnore(field))
            {
                continue;
            }

            // 过滤静态类型
            if (Modifier.isStatic(field.getModifiers()))
            {
                continue;
            }

            InnerBean bean = new InnerBean();

            bean.setField(field);

            if (getRelationship(field) != null)
            {
                bean.setRelationship(true);
            }
            else
            {
                bean.setRelationship(false);
            }

            bean.setAutoIncrement(isIdAutoIncrement(field));

            // if filed is not Relationship,judge it weather it is alias
            if ( !bean.isRelationship())
            {
                Alias alias = getAlias(field);
                if (alias != null)
                {
                    bean.setAlias(true);

                    bean.setAliasField(alias.aliasField());
                }
                else
                {
                    bean.setAlias(false);
                }
            }

            bean.setColumnName(BeanTools.getColumnName(field));

            // 参数必须是field的，不然反射会失败
            bean.setFieldName(field.getName());

            columns.add(bean);
        }

        // 如果继承，还要从父类里面获得
        if (isInherit(claz))
        {
            Class sup = claz.getSuperclass();

            if (sup != Object.class)
            {
                // 递归获得
                columns.addAll(getClassFieldsInner(sup));
            }
            else
            {
                throw new MYSqlException(ErrorConstant.OBJECT_NOT_INHERIT);
            }
        }

        return columns;
    }

    /**
     * 获得INSERT的时候class里面的实体column(Id需要特殊处理，静态类型忽略)
     * 
     * @param claz
     * @param processIdAutoIncrement
     *            是否处理ID自增类型的
     * @return
     * @throws MYSqlException
     */
    public static List<InnerBean> getClassFieldsInsert(Class claz, boolean processIdAutoIncrement)
        throws MYSqlException
    {
        List<InnerBean> columns = new ArrayList<InnerBean>();

        // 获得列名
        Field[] fields = claz.getDeclaredFields();

        for (Field field : fields)
        {
            if (isIgnore(field))
            {
                continue;
            }

            if (isRelationship(field))
            {
                continue;
            }

            // 过滤静态类型
            if (Modifier.isStatic(field.getModifiers()))
            {
                continue;
            }

            // 如果需要处理自增类型
            if (processIdAutoIncrement && isIdAutoIncrement(field))
            {
                continue;
            }

            InnerBean bean = new InnerBean();

            bean.setColumnName(BeanTools.getColumnName(field));

            // 参数必须是field的，不然反射会失败
            bean.setFieldName(field.getName());

            bean.setField(field);

            columns.add(bean);
        }

        // 如果继承，还要从父类里面获得
        if (isInherit(claz))
        {
            Class sup = claz.getSuperclass();

            if (sup != Object.class)
            {
                // 递归获得
                columns.addAll(getClassFieldsInsert(sup, processIdAutoIncrement));
            }
            else
            {
                throw new MYSqlException(ErrorConstant.OBJECT_NOT_INHERIT);
            }
        }

        return columns;
    }

    /**
     * All获得class里面的实体column
     * 
     * @param claz
     * @return
     * @throws MYSqlException
     */
    public static List<InnerBean> getAllClassFieldsInner(Class claz)
    {
        List<InnerBean> columns = new ArrayList<InnerBean>();

        // 获得列名
        Field[] fields = claz.getDeclaredFields();

        for (Field field : fields)
        {
            // 过滤静态类型
            if (Modifier.isStatic(field.getModifiers()))
            {
                continue;
            }

            InnerBean bean = new InnerBean();

            bean.setColumnName(BeanTools.getColumnName(field));

            // 参数必须是field的，不然反射会失败
            bean.setFieldName(field.getName());

            bean.setField(field);

            columns.add(bean);
        }

        // 如果继承，还要从父类里面获得
        Class sup = claz.getSuperclass();

        if (sup != Object.class && sup != null)
        {
            // 递归获得
            columns.addAll(getAllClassFieldsInner(sup));
        }

        return columns;
    }

    /**
     * All获得class里面的实体column(排除Ignore)
     * 
     * @param claz
     * @return
     * @throws MYSqlException
     */
    public static List<InnerBean> getAllClassSqlFieldsInner(Class claz)
    {
        List<InnerBean> columns = new ArrayList<InnerBean>();

        // 获得列名
        Field[] fields = claz.getDeclaredFields();

        for (Field field : fields)
        {
            if (isIgnore(field))
            {
                continue;
            }

            // 过滤静态类型
            if (Modifier.isStatic(field.getModifiers()))
            {
                continue;
            }

            InnerBean bean = new InnerBean();

            bean.setColumnName(BeanTools.getColumnName(field));

            // 参数必须是field的，不然反射会失败
            bean.setFieldName(field.getName());

            columns.add(bean);
        }

        // 如果继承，还要从父类里面获得
        Class sup = claz.getSuperclass();

        if (sup != Object.class && sup != null)
        {
            // 递归获得
            columns.addAll(getAllClassFieldsInner(sup));
        }

        return columns;
    }

    /**
     * 获得class里面的实体column
     * 
     * @param claz
     * @return
     */
    public static List<Field> getClassFields(Class claz)
    {
        List<Field> columns = new ArrayList<Field>();

        // 获得列名
        Field[] fields = claz.getDeclaredFields();

        for (Field field : fields)
        {
            if (isIgnore(field))
            {
                continue;
            }

            // 过滤静态类型
            if (Modifier.isStatic(field.getModifiers()))
            {
                continue;
            }

            columns.add(field);
        }

        // 如果继承，还要从父类里面获得
        if (isInherit(claz))
        {
            Class sup = claz.getSuperclass();

            if (sup != Object.class)
            {
                // 递归获得
                columns.addAll(getClassFields(sup));
            }
        }

        return columns;
    }

    /**
     * 获得class里面的实体column(ignore的也是,但是过滤静态类型)
     * 
     * @param claz
     * @return
     */
    public static List<Field> getClassAllFields(Class claz)
    {
        List<Field> columns = new ArrayList<Field>();

        // 获得列名
        Field[] fields = claz.getDeclaredFields();

        for (Field field : fields)
        {
            // 过滤静态类型
            if (Modifier.isStatic(field.getModifiers()))
            {
                continue;
            }

            columns.add(field);
        }

        // 如果继承，还要从父类里面获得
        if (isInherit(claz))
        {
            Class sup = claz.getSuperclass();

            if (sup != Object.class)
            {
                // 递归获得
                columns.addAll(getClassAllFields(sup));
            }
        }

        return columns;
    }

    /**
     * field是否被忽略
     * 
     * @param field
     * @return
     */
    public static boolean isIgnore(Field field)
    {
        return field.getAnnotation(Ignore.class) != null;
    }

    /**
     * isRelationship
     * 
     * @param field
     * @return
     */
    public static boolean isRelationship(Field field)
    {
        return field.getAnnotation(Relationship.class) != null;
    }

    /**
     * field是否被忽略(Id自动增加在Insert的时候)
     * 
     * @param field
     * @return
     */
    public static boolean isIdAutoIncrement(Field field)
    {
        Id id = field.getAnnotation(Id.class);

        if (id == null)
        {
            return false;
        }

        return id.autoIncrement();
    }

    /**
     * 类是否是继承的
     * 
     * @param claz
     * @return
     */
    public static boolean isInherit(Class<?> claz)
    {
        // 是实体表
        Entity en = claz.getAnnotation(Entity.class);

        if (en == null)
        {
            return false;
        }

        return en.inherit();
    }

    /**
     * getCacheRelation
     * 
     * @param claz
     * @return
     */
    public static CacheRelation getCacheRelation(Class<?> claz)
    {
        // 是实体表
        CacheRelation cacheRelation = claz.getAnnotation(CacheRelation.class);

        if (cacheRelation == null)
        {
            return null;
        }

        return cacheRelation;
    }

    public static String getClassName(Class claz)
    {
        String name = claz.getName();

        if (name.indexOf('.') == -1)
        {
            return name;
        }

        return name.substring(name.lastIndexOf('.') + 1);
    }

    public static int getFieldType(Field field)
    {
        if (field == null)
        {
            return Types.NULL;
        }

        Class claz = field.getType();

        if (claz == String.class)
        {
            return Types.VARCHAR;
        }

        if (claz == Integer.class || claz == int.class)
        {
            return Types.INTEGER;
        }

        if (claz == Long.class || claz == long.class)
        {
            return Types.INTEGER;
        }

        if (claz == BigDecimal.class)
        {
            return Types.INTEGER;
        }

        if (claz == BigInteger.class)
        {
            return Types.INTEGER;
        }

        if (claz == Byte.class || claz == byte.class)
        {
            return Types.BIT;
        }

        if (claz == Float.class || claz == float.class)
        {
            return Types.FLOAT;
        }

        if (claz == Double.class || claz == double.class)
        {
            return Types.DOUBLE;
        }

        if (claz == Character.class || claz == char.class)
        {
            return Types.CHAR;
        }

        if (claz == Short.class || claz == short.class)
        {
            return Types.BIT;
        }

        if (claz == java.util.Date.class)
        {
            return Types.DATE;
        }

        if (claz == java.sql.Date.class)
        {
            return Types.DATE;
        }

        if (claz == Calendar.class)
        {
            return Types.DATE;
        }

        if (claz == Timestamp.class)
        {
            return Types.TIMESTAMP;
        }

        if (claz == Boolean.class || claz == boolean.class)
        {
            return Types.BOOLEAN;
        }

        return Types.NULL;
    }

}
