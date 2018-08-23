package cn;

import cn.bean.impl.Lee;
import cn.lkangle.ioc.TinyIoc;
import org.junit.Test;

/**
 * @AUTHOR soft
 * @DATE 2018/8/22 20:56
 * @DESCRIBE
 */
public class TinyIocTest {

    @Test
    public void iocT() {
        TinyIoc ioc = TinyIoc.of().init();

        Lee lee = ioc.getBean(Lee.class);

        lee.say("Hello TinyIoc");
    }
}
