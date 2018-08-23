# TinyIoc
简单的IOC实现
- 支持接口类型注入
- 支持自定义bean name
# 使用
```
    @Test
    public void iocT() {
        TinyIoc ioc = TinyIoc.of().init();

        Lee lee = ioc.getBean(Lee.class);

        lee.say("Hello TinyIoc");
    }
```
