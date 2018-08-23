package cn.lkangle.ioc.utils;

import cn.lkangle.ioc.annotation.AutoWrite;
import cn.lkangle.ioc.annotation.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @AUTHOR soft
 * @DATE 2018/8/22 14:11
 * @DESCRIBE 反射工具类
 */
public class ReflectUtils {
    private final static Logger log = LoggerFactory.getLogger(ReflectUtils.class);

    /**
     * 实例化Class
     * @param clazz class
     * @return 实例
     */
    public static Object newInstance(Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("类实例化失败！{}", e.getMessage());
            log.debug("{}", e);
            return null;
        }
    }

    /**
     * 获取类直接的字段集合
     * @param cls 类
     * @return 字段集合
     */
    public static List<Field> getFields(Class<?> cls) {
        return Arrays.asList(cls.getDeclaredFields());
    }

    /**
     * 根据组件类型获取组件名
     * @param component 组件类型
     * @return 组件名 默认使用组件类名首字母小写形式作为组件名
     */
    public static String getBeanNameFormClass(Class<?> component) {
        Component annotation = component.getAnnotation(Component.class);
        if (annotation != null && !"".equals(annotation.name())) {
            return annotation.name();
        }
        return toName(component);
    }

    /**
     * 从字段上获取bean名
     * 这个字段必须是被AutoWrite注解的
     * @param field 字段
     * @return name 默认字段名
     */
    public static String getBeanNameFromField(Field field) {
        AutoWrite write = field.getAnnotation(AutoWrite.class);
        if (write != null && !"".equals(write.name())) {
            return write.name();
        }
        return toName(field.getType());
    }

    /**
     * 获取字段上的AutoWrite注解的name值
     * @param field 字段类型
     * @return 注解name，没有则返回null
     */
    public static String getAutoWriteName(Field field) {
        AutoWrite write = field.getAnnotation(AutoWrite.class);
        if (write != null && !"".equals(write.name())) {
            return write.name();
        }
        return null;
    }

    /**
     * 根据类Class判断其是不是组件
     * @param clazz 类Class
     * @return 是否
     */
    public static boolean isComponent(Class<?> clazz) {
        for (Annotation annotation : clazz.getAnnotations()) {
            if (annotation instanceof Component || null != annotation.annotationType()
                    .getAnnotation(Component.class)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字段是不是需要注入
     * @param field 字段类型
     * @return 是否
     */
    public static boolean isInject(Field field) {
        for (Annotation annotation : field.getAnnotations()) {
            if (annotation instanceof AutoWrite || null != annotation.annotationType()
                    .getAnnotation(AutoWrite.class)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 填充对象的字段值
     * @param field 对象字段
     * @param target 对象实例
     * @param value 值
     */
    public static void setField(Field field, Object target, Object value) {
        try {
            field.setAccessible(true);
            field.set(target, value);
        } catch (IllegalAccessException e) {
            log.error("填充对象({})字段值失败({})！{}", target, field.getName(), e.getMessage());
        }
    }

    public static String toName(Class<?> cls) {
        String simpleName = cls.getSimpleName();
        return simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
    }
}
