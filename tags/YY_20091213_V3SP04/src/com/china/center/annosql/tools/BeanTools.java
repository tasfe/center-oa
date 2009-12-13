/*
 * File Name: BeanTools.java CopyRight: Copyright by www.center.china
 * Description: Creater: zhuAchen CreateTime: 2007-9-28 Grant: open source to
 * everybody
 */
package com.china.center.annosql.tools;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.china.center.annosql.ErrorBean;
import com.china.center.annosql.ErrorConstant;
import com.china.center.annosql.InnerBean;
import com.china.center.annosql.MYSqlException;
import com.china.center.annotation.Column;
import com.china.center.annotation.Entity;
import com.china.center.annotation.FK;
import com.china.center.annotation.Html;
import com.china.center.annotation.Id;
import com.china.center.annotation.Ignore;
import com.china.center.annotation.Join;
import com.china.center.annotation.Note;
import com.china.center.annotation.Relationship;
import com.china.center.annotation.Table;
import com.china.center.annotation.Unique;


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
     * ���object�ı���
     * 
     * @param claz
     * @return
     */
    public static String getTableName(Class<?> claz)
    {
        Table table = claz.getAnnotation(Table.class);

        String tableName = null;

        // ��ñ���
        if (table == null)
        {
            if (isInherit(claz))
            {
                Class sup = claz.getSuperclass();

                if (sup != Object.class)
                {
                    // �ݹ���
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
     * ���object�ı���
     * 
     * @param claz
     * @return
     */
    public static String getEntryName(Class<?> claz)
    {
        Entity entity = claz.getAnnotation(Entity.class);

        // ��ñ���
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
     * ���object�ı���
     * 
     * @param claz
     * @return
     */
    public static boolean isEntry(Class<?> claz)
    {
        Entity entity = claz.getAnnotation(Entity.class);

        // ��ñ���
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
     * ���object�ı���
     * 
     * @param claz
     * @return
     */
    public static boolean isCache(Class<?> claz)
    {
        Entity entity = claz.getAnnotation(Entity.class);

        // ��ñ���
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
     * ��field������columnName
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
     * ��field������columnName
     * 
     * @param field
     * @return
     */
    public static Relationship getRelationship(Field field)
    {
        return field.getAnnotation(Relationship.class);
    }

    /**
     * ��field������columnName
     * 
     * @param field
     * @return
     */
    public static Join getJoin(Field field)
    {
        return field.getAnnotation(Join.class);
    }

    /**
     * ��class������id column(����м̳���ô��������id��Ч)
     * 
     * @param claz
     * @return
     */
    public static String getIdColumn(Class<?> claz)
    {
        // �������
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
     * ��class������id column(����м̳���ô��������id��Ч)
     * 
     * @param claz
     * @return
     */
    public static Field getIdField(Class<?> claz)
    {
        // �������
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
     * ��class������id field(����м̳���ô��������id��Ч)
     * 
     * @param claz
     * @return
     */
    public static String getIdFieldName(Class<?> claz)
    {
        // �������
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
     * ��class������Unique field(����м̳���ô��������Unique��Ч)
     * 
     * @param claz
     * @return
     */
    public static String getUniqueFieldName(Class<?> claz)
    {
        // �������
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
     * ��class������Unique field(����м̳���ô��������Unique��Ч)
     * 
     * @param claz
     * @return
     */
    public static String[] getUniqueFields(Class<?> claz)
    {
        // �������
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
     * ��class������id field(����м̳���ô��������id��Ч)
     * 
     * @param claz
     * @return
     */
    public static String getFKFieldName(Class<?> claz, int index)
    {
        // �������
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
     * ��class������id field(����м̳���ô��������id��Ч)
     * 
     * @param claz
     * @return
     */
    public static Html getPropertyHtml(Field field)
    {
        return field.getAnnotation(Html.class);
    }

    /**
     * ���List<T>
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
        throws InstantiationException, IllegalAccessException, InvocationTargetException,
        NoSuchMethodException
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
        throws InstantiationException, IllegalAccessException, InvocationTargetException,
        NoSuchMethodException, MYSqlException
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
     * ��class������������field(��Сд������)
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
     * ���error����Ϣ
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
     * ���class�����ʵ��column
     * 
     * @param claz
     * @return
     * @throws MYSqlException
     */
    public static List<InnerBean> getClassFieldsInner(Class claz)
        throws MYSqlException
    {
        List<InnerBean> columns = new ArrayList<InnerBean>();

        // �������
        Field[] fields = claz.getDeclaredFields();

        for (Field field : fields)
        {
            if (isIgnore(field))
            {
                continue;
            }

            InnerBean bean = new InnerBean();

            bean.setField(field);

            if (getRelationship(field) != null)
            {
                bean.setRelationship(true);
            }

            bean.setColumnName(BeanTools.getColumnName(field));

            // ����������field�ģ���Ȼ�����ʧ��
            bean.setFieldName(field.getName());

            columns.add(bean);
        }

        // ����̳У���Ҫ�Ӹ���������
        if (isInherit(claz))
        {
            Class sup = claz.getSuperclass();

            if (sup != Object.class)
            {
                // �ݹ���
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
     * ���INSERT��ʱ��class�����ʵ��column(Id��Ҫ���⴦��)
     * 
     * @param claz
     * @param processIdAutoIncrement
     *            �Ƿ���ID�������͵�
     * @return
     * @throws MYSqlException
     */
    public static List<InnerBean> getClassFieldsInsert(Class claz, boolean processIdAutoIncrement)
        throws MYSqlException
    {
        List<InnerBean> columns = new ArrayList<InnerBean>();

        // �������
        Field[] fields = claz.getDeclaredFields();

        for (Field field : fields)
        {
            if (isIgnore(field))
            {
                continue;
            }

            // �����Ҫ������������
            if (processIdAutoIncrement && isIdAutoIncrement(field))
            {
                continue;
            }

            InnerBean bean = new InnerBean();

            bean.setColumnName(BeanTools.getColumnName(field));

            // ����������field�ģ���Ȼ�����ʧ��
            bean.setFieldName(field.getName());

            bean.setField(field);

            columns.add(bean);
        }

        // ����̳У���Ҫ�Ӹ���������
        if (isInherit(claz))
        {
            Class sup = claz.getSuperclass();

            if (sup != Object.class)
            {
                // �ݹ���
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
     * All���class�����ʵ��column
     * 
     * @param claz
     * @return
     * @throws MYSqlException
     */
    public static List<InnerBean> getAllClassFieldsInner(Class claz)
    {
        List<InnerBean> columns = new ArrayList<InnerBean>();

        // �������
        Field[] fields = claz.getDeclaredFields();

        for (Field field : fields)
        {
            InnerBean bean = new InnerBean();

            bean.setColumnName(BeanTools.getColumnName(field));

            // ����������field�ģ���Ȼ�����ʧ��
            bean.setFieldName(field.getName());

            bean.setField(field);

            columns.add(bean);
        }

        // ����̳У���Ҫ�Ӹ���������
        Class sup = claz.getSuperclass();

        if (sup != Object.class && sup != null)
        {
            // �ݹ���
            columns.addAll(getAllClassFieldsInner(sup));
        }

        return columns;
    }

    /**
     * All���class�����ʵ��column(�ų�Ignore)
     * 
     * @param claz
     * @return
     * @throws MYSqlException
     */
    public static List<InnerBean> getAllClassSqlFieldsInner(Class claz)
    {
        List<InnerBean> columns = new ArrayList<InnerBean>();

        // �������
        Field[] fields = claz.getDeclaredFields();

        for (Field field : fields)
        {
            if (isIgnore(field))
            {
                continue;
            }

            InnerBean bean = new InnerBean();

            bean.setColumnName(BeanTools.getColumnName(field));

            // ����������field�ģ���Ȼ�����ʧ��
            bean.setFieldName(field.getName());

            columns.add(bean);
        }

        // ����̳У���Ҫ�Ӹ���������
        Class sup = claz.getSuperclass();

        if (sup != Object.class && sup != null)
        {
            // �ݹ���
            columns.addAll(getAllClassFieldsInner(sup));
        }

        return columns;
    }

    /**
     * ���class�����ʵ��column
     * 
     * @param claz
     * @return
     */
    public static List<Field> getClassFields(Class claz)
    {
        List<Field> columns = new ArrayList<Field>();

        // �������
        Field[] fields = claz.getDeclaredFields();

        for (Field field : fields)
        {
            if (isIgnore(field))
            {
                continue;
            }

            columns.add(field);
        }

        // ����̳У���Ҫ�Ӹ���������
        if (isInherit(claz))
        {
            Class sup = claz.getSuperclass();

            if (sup != Object.class)
            {
                // �ݹ���
                columns.addAll(getClassFields(sup));
            }
        }

        return columns;
    }

    /**
     * ���class�����ʵ��column(ignore��Ҳ��)
     * 
     * @param claz
     * @return
     */
    public static List<Field> getClassAllFields(Class claz)
    {
        List<Field> columns = new ArrayList<Field>();

        // �������
        Field[] fields = claz.getDeclaredFields();

        for (Field field : fields)
        {
            columns.add(field);
        }

        // ����̳У���Ҫ�Ӹ���������
        if (isInherit(claz))
        {
            Class sup = claz.getSuperclass();

            if (sup != Object.class)
            {
                // �ݹ���
                columns.addAll(getClassFields(sup));
            }
        }

        return columns;
    }

    /**
     * field�Ƿ񱻺���
     * 
     * @param field
     * @return
     */
    public static boolean isIgnore(Field field)
    {
        return field.getAnnotation(Ignore.class) != null;
    }

    /**
     * field�Ƿ񱻺���(Id�Զ�������Insert��ʱ��)
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
     * ���Ƿ��Ǽ̳е�
     * 
     * @param claz
     * @return
     */
    public static boolean isInherit(Class<?> claz)
    {
        // ��ʵ���
        Entity en = claz.getAnnotation(Entity.class);

        if (en == null)
        {
            return false;
        }

        return en.inherit();
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

        if (claz == Integer.class)
        {
            return Types.INTEGER;
        }

        if (claz == Byte.class)
        {
            return Types.BIT;
        }

        if (claz == Float.class)
        {
            return Types.FLOAT;
        }

        if (claz == Double.class)
        {
            return Types.DOUBLE;
        }

        if (claz == Character.class)
        {
            return Types.CHAR;
        }

        if (claz == Short.class)
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

        if (claz == Boolean.class)
        {
            return Types.BOOLEAN;
        }

        return Types.NULL;
    }

    /**
     * ����޸ĵ�ͷsql
     * 
     * @param claz
     * @return
     */
    public static String getUpdateHead(Class<?> claz)
    {
        return "update " + getTableName(claz) + " ";
    }

    public static String getDeleteHead(Class<?> claz)
    {
        return "delete from " + getTableName(claz) + " ";
    }

    public static String getCountHead(Class<?> claz)
    {
        return "select count(1) from " + getTableName(claz) + " ";
    }

}
