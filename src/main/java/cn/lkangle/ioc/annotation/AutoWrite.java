package cn.lkangle.ioc.annotation;

import java.lang.annotation.*;

/**
 * @Author: Soft
 * @Date: 2018/5/29 18:00
 * @Desc: 自动注入注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.CONSTRUCTOR})
@Documented
@Inherited
public @interface AutoWrite {

    /**
     * 用于注入指定名字的bean
     */
    String name() default "";
}
