package cn.lkangle.ioc;

/**
 * @AUTHOR soft
 * @DATE 2018/8/22 13:53
 * @DESCRIBE
 */
public class Config {
    /**
     * 是不是单例模式，默认单例
     */
    private boolean single = true;

    /**
     * 初始化扫描的包，默认扫描类路径下
     */
    private String scanPackage = "";

    public String getScanPackage() {
        return scanPackage;
    }

    public Config setScanPackage(String scanPackage) {
        this.scanPackage = scanPackage;
        return this;
    }

    public boolean isSingle() {
        return single;
    }

    public Config setSingle(boolean single) {
        this.single = single;
        return this;
    }
}
