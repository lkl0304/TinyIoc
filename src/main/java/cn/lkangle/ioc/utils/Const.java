package cn.lkangle.ioc.utils;

import java.io.File;

/**
 * @AUTHOR soft
 * @DATE 2018/8/22 13:31
 * @DESCRIBE 一些常量
 */
public interface Const {
    /**
     * 类路径，很重要
     * 结尾必须有路径间隔符
     * 如：G:\IdeaPro\TinyIoc\target\classes\
     */
    String      CLASSPATH  = new File(Const.class.getResource("/").getPath()).getAbsolutePath() + File.separator;
    ClassLoader LOADER  = Const.class.getClassLoader();
}
