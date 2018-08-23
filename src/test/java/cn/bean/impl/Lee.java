package cn.bean.impl;

import cn.bean.Animal;
import cn.bean.People;
import cn.lkangle.ioc.annotation.AutoWrite;
import cn.lkangle.ioc.annotation.Component;

/**
 * @AUTHOR soft
 * @DATE 2018/8/22 22:55
 * @DESCRIBE
 */
@Component
public class Lee {

    @AutoWrite
    private People peo;

    @AutoWrite
    private Animal animal;

    public void say(String world) {
        System.out.println("Lee say: " + world);

        peo.say("我在lee这里！");
    }
}
