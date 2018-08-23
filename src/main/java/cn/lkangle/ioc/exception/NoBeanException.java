package cn.lkangle.ioc.exception;

import java.io.IOException;

/**
 * @AUTHOR soft
 * @DATE 2018/8/22 23:25
 * @DESCRIBE
 */
public class NoBeanException extends Exception {

    public NoBeanException(String msg) {
        super("容器中不存在的bean: " + msg);
    }

    public NoBeanException() {
        super("容器中不存在的bean");
    }
}
