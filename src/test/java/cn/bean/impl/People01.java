package cn.bean.impl;

import cn.bean.Animal;
import cn.bean.People;
import cn.lkangle.ioc.annotation.AutoWrite;
import cn.lkangle.ioc.annotation.Component;

/**
 * @AUTHOR soft
 * @DATE 2018/8/23 0:22
 * @DESCRIBE
 */
@Component
public class People01 implements People {

    @AutoWrite
    private Animal animal;

    @Override
    public void say(String world) {
        System.out.println("People01 say: " + world);
    }
}
