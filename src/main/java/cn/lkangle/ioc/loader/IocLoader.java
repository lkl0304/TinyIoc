package cn.lkangle.ioc.loader;

import cn.lkangle.ioc.Config;
import cn.lkangle.ioc.core.IocContext;
import cn.lkangle.ioc.exception.NoBeanException;
import cn.lkangle.ioc.utils.ConfigUtils;
import cn.lkangle.ioc.utils.Const;
import cn.lkangle.ioc.utils.PackageUtils;
import cn.lkangle.ioc.utils.ReflectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @AUTHOR soft
 * @DATE 2018/8/22 14:46
 * @DESCRIBE IOC容器加载
 */
public class IocLoader {
    private final static Logger log = LoggerFactory.getLogger(IocLoader.class);
    private static IocLoader loader = null;
    private IocLoader() {}

    public synchronized static IocLoader of() {
        if (loader == null) {
            loader = new IocLoader();
        }
        return loader;
    }

    public IocLoader load() {
        return this.load(null);
    }
    /**
     * 从指定包下开始加载
     * @param basePackage 指定的包
     *                    若不指定就读取配置中指定的包
     */
    public IocLoader load(String basePackage) {
        Config config = this.loadConfig();
        if (basePackage != null)
            config.setScanPackage(basePackage);
        List<Class<?>> components = PackageUtils.classSet(config.getScanPackage())
                .stream().filter(ReflectUtils::isComponent)
                .collect(Collectors.toList());

        this.saveComponents(components).ComponentsInject();
        return this;
    }

    /**
     * 把组件保存起来，同时实例化，但是没有进行注入
     * @param components 组件Class集合
     */
    private IocLoader saveComponents(List<Class<?>> components) {
        components.forEach(component->{
            String beanName = ReflectUtils.getBeanNameFormClass(component);
            IocContext.ioc().add(beanName, component);
        });
        return this;
    }

    /**
     * 组件字段注入，完成组件的实例化
     */
    private void ComponentsInject() {
        IocContext.getAllBeans().forEach(bean -> {
            List<Field> fields = ReflectUtils.getFields(bean.getClass());
            fields.stream()
                    .filter(ReflectUtils::isInject)
                    .forEach(field -> {
                        String name = ReflectUtils.getAutoWriteName(field);
                        Object value;
                        if (name == null) {
                            name = ReflectUtils.getBeanNameFromField(field);
                            Class<?> fieldClass = field.getType();
                            value = IocContext.ioc().get(fieldClass);
                            if (value == null) {
                                Class<?> childrenClazz = IocContext.getChildrenClazz(fieldClass);
                                if (childrenClazz != null)
                                    value = IocContext.ioc().get(childrenClazz);
                            }
                        } else {
                            value = IocContext.ioc().get(name);
                        }
                        if (value != null) {
                            ReflectUtils.setField(field, bean, value);
                        } else {
                            try {
                                throw new NoBeanException(field.getType().getName());
                            } catch (NoBeanException e) {
                                log.error("NoBean: {}。{}", name, e.getMessage());
                            }
                        }
                    });
        });
    }

    /**
     * 加载配置文件
     * @return 配置
     */
    private Config loadConfig() {
        Properties properties = new Properties();
        Config config = ConfigUtils.get();
        try {
            String prop = "app.properties";
            InputStream inputStream = Const.LOADER.getResourceAsStream(prop);
            properties.load(inputStream);
            config.setScanPackage((String) properties.getOrDefault("config.scan", ""));
            config.setSingle("true".equals(properties.getOrDefault("config.single", "true")));
        } catch (Exception e) {
            log.error("配置文件不存在！使用默认配置！");
        }
        return config;
    }
}
