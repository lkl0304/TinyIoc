# TinyIoc
简单的IOC实现
- 支持接口类型注入
- 支持自定义bean name
# 使用
```
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
```
```
@Component
public class Dog implements Animal {

}
-------------
@Component
public class People01 implements People {

    @AutoWrite
    private Animal animal;

    @Override
    public void say(String world) {
        System.out.println("People01 say: " + world);
    }
}

```
```
    @Test
    public void iocT() {
        TinyIoc ioc = TinyIoc.of().init();

        Lee lee = ioc.getBean(Lee.class);

        lee.say("Hello TinyIoc");
    }
```
