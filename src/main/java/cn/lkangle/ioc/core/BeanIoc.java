package cn.lkangle.ioc.core;

import cn.lkangle.ioc.Config;
import cn.lkangle.ioc.utils.ConfigUtils;
import cn.lkangle.ioc.utils.ReflectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @AUTHOR soft
 * @DATE 2018/8/22 13:51
 * @DESCRIBE bean容器实现
 * 默认单例模式，直接根据bean的类型获取bean实例
 */
public final class BeanIoc implements Ioc {
    // 保存bean类型和实例的映射
    private Map<Class<?>, Object> typeToInstance = new ConcurrentHashMap<>();
    // 保存bean名和bean类型的映射
    private Map<String, Class<?>> nameToType = new ConcurrentHashMap<>();

    BeanIoc() {}

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Class<T> clazz) {
        if (ConfigUtils.get().isSingle()) {

        }
        return (T) typeToInstance.get(clazz);
    }

    @Override
    public Object get(String beanName) {
        Class<?> clazz = nameToType.get(beanName);
        if (null != clazz) {
            return typeToInstance.get(clazz);
        }
        return null;
    }

    @Override
    public void add(String name, Object bean) {
        Class<?> clazz = bean.getClass();
        nameToType.put(name, clazz);
        typeToInstance.put(clazz, bean);
    }

    @Override
    public void add(String name, Class<?> clazz) {
        nameToType.put(name, clazz);
        Object instance = ReflectUtils.newInstance(clazz);
        typeToInstance.put(clazz, instance);
    }

    List<Object> getBeans() {
        return new ArrayList<>(typeToInstance.values());
    }

    List<Class<?>> getTypes() {
        return new ArrayList<>(nameToType.values());
    }
}
