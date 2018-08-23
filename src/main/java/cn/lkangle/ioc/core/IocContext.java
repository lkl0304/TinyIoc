package cn.lkangle.ioc.core;

import java.util.List;

/**
 * @AUTHOR soft
 * @DATE 2018/8/22 14:32
 * @DESCRIBE IOC上下文，可用来管理Bean
 */
public final class IocContext {
    private static BeanIoc beanIoc = new BeanIoc();

    /**
     * 获取ioc容器，单例
     * @return ioc容器实例
     */
    public static Ioc ioc() {
        return beanIoc;
    }

    /**
     * 获取所有bean实例
     * @return 实例集合
     */
    public static List<Object> getAllBeans() {
        return beanIoc.getBeans();
    }

    /**
     * 根据接口的类型获取相应的子类型（如果存在）
     * @param pClazz 接口类型
     * @return 子类型
     */
    public static Class<?> getChildrenClazz(Class<?> pClazz) {
        List<Class<?>> types = beanIoc.getTypes();
        for (Class<?> type : types) {
            if (pClazz.isAssignableFrom(type)) {
                return type;
            }
        }
        return null;
    }
}
