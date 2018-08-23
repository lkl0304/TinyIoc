package cn.lkangle.ioc.core;

import java.util.List;

/**
 * @Author: Soft
 * @Date: 2018/5/29 15:25
 * @Desc: Bean上下文，管理所有的bean
 */
public interface Ioc {
    /**
     * 通过类型类实例
     * @param clazz 类型
     * @return IOC容器中的对象实例
     */
    <T> T get(Class<T> clazz);

    /**
     * 通过BeanName获取类实例
     * @param beanName 被管理的beanName
     * @return IOC容器中的对象实例
     */
    Object get(String beanName);

    /**
     * 添加一个bean
     * @param name bean的名字
     * @param bean bean实例
     */
    void add(String name, Object bean);

    /**
     * 添加一个bean
     * @param name bean名字
     * @param clazz bean类型
     */
    void add(String name, Class<?> clazz);
}
