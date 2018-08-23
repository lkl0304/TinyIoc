package cn.lkangle.ioc.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @AUTHOR soft
 * @DATE 2018/8/22 15:13
 * @DESCRIBE 包工具
 */
public class PackageUtils {
    private final static Logger log = LoggerFactory.getLogger(PackageUtils.class);

    /**
     * 根据包名获取包下所有类
     * @param packageName 包名
     * @return 所有类
     */
    public static Set<Class<?>> classSet(String packageName) {
        Set<Class<?>> classes = new HashSet<>();
        try {
            String dir = toDir(packageName);
            File package_ = new File(Const.LOADER.getResource(dir).getPath());
            File[] listFiles = package_.listFiles();

            if (listFiles != null) {
                for (File file : listFiles) {
                    if (file.isFile()) {
                        String filename = file.getName();
                        if (filename.endsWith(".class")) {
                            classes.add(loadClass(toClassName(file)));
                        } else if (filename.endsWith(".jar")) {
                            JarFile jarFile = new JarFile(file);
                            Enumeration<JarEntry> entries = jarFile.entries();
                            while (entries.hasMoreElements()) {
                                JarEntry jarEntry = entries.nextElement();
                                String name = jarEntry.getName();
                                if (name.endsWith(".class")) {
                                    classes.add(loadClass(toClassName(name)));
                                }
                            }
                        }
                    } else if (file.isDirectory()) {
                        String path = file.getPath();
                        classes.addAll(classSet(toPackage(path)));
                    }
                }
            } else {
                log.debug("空包 {}", packageName);
            }
        } catch (NullPointerException e) {
            log.error("资源加载失败！NullPointerException，包({})不存在！", packageName);
        } catch (Exception e) {
            log.error("未知错误！");
            e.printStackTrace();
        }
        return classes;
    }

    /**
     * 加载类
     * @param className 类名
     * @return 类
     */
    private static Class<?> loadClass(String className) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className, true, Const.LOADER);
            log.debug("成功加载类：{}", className);
        } catch (ClassNotFoundException e) {
            log.error("加载类({})失败！{}", className, e.getMessage());
        }
        return cls;
    }

    /**
     * 将包名转化为文件路径名
     * 会自动添加classpath
     * @param package_ 包名
     * @return 文件路径名
     */
    private static String toDir(String package_) {
        return package_.replace(".", "/");
    }

    /**
     * 将文件路径名转化为包名
     * 会自动去除classpath
     * @param dir 文件路径名
     * @return 包名
     */
    private static String toPackage(String dir) {
        return dir.replace(Const.CLASSPATH, "")
                .replace(File.separator, ".")
                .replace("/", ".");
    }

    /**
     * 将文件转换为类名
     * @param file 文件
     * @return 类名
     */
    private static String toClassName(File file) {
        String path = file.getAbsolutePath();
        return toClassName(path);
    }

    /**
     * 将完整的文件路径名转化为类名
     * @param filename 文件名
     * @return 类名
     */
    private static String toClassName(String filename) {
        return filename
                .replace(Const.CLASSPATH, "")
                .replace(".class", "")
                .replace(File.separator, ".")
                .replace("/", ".");
    }
}
