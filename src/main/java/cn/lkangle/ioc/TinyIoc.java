package cn.lkangle.ioc;

import cn.lkangle.ioc.core.IocContext;
import cn.lkangle.ioc.loader.IocLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @AUTHOR soft
 * @DATE 2018/8/22 14:47
 * @DESCRIBE
 */
public class TinyIoc {
    private final static Logger log = LoggerFactory.getLogger(TinyIoc.class);
    private static TinyIoc tinyIoc = new TinyIoc();
    private boolean init = false;
    private TinyIoc() {}

    public static TinyIoc of() {
        return tinyIoc;
    }

    /**
     * 初始化
     */
    public TinyIoc init() {
        IocLoader.of().load();
        init = true;
        return this;
    }

    /**
     * 根据类型获取对象实例
     * @param clazz 类型
     * @return 实例
     */
    public <T> T getBean(Class<T> clazz) {
        if (init) {
            return IocContext.ioc().get(clazz);
        }
        noInit();
        return null;
    }

    /**
     * 通过BeanName获取类实例
     * @param beanName beanName
     * @return IOC容器中的对象实例
     */
    public Object getBean(String beanName) {
        if (init) {
            return IocContext.ioc().get(beanName);
        }
        noInit();
        return null;
    }

    private void noInit() {
        log.error("未进行初始化！(TinyIoc.init)");
        System.exit(0);
    }
}
