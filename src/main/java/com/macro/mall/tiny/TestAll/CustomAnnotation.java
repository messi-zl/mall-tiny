package com.macro.mall.tiny.TestAll;

import java.lang.annotation.*;

/**
 * @Description: 自定义注解
 * @CreateBy: li.zheng871@sim。com
 * @DateTime: 2021/7/7
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomAnnotation {
}
