package cn.lkangle.ioc.utils;

import cn.lkangle.ioc.Config;

/**
 * @AUTHOR soft
 * @DATE 2018/8/22 17:04
 * @DESCRIBE
 */
public class ConfigUtils {
    private static Config config = new Config();

    public static Config get() {
        return config;
    }
}
