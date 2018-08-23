package cn.lkangle.ioc.annotation;

import java.lang.annotation.*;

/**
 * @Author: Soft
 * @Date: 2018/5/29 18:00
 * @Desc: bean组件注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Documented
@Inherited
public @interface Component {

    /**
     * 组件的名字
     */
    String name() default "";
}
