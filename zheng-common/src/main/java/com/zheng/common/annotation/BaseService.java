package com.zheng.common.annotation;

import java.lang.annotation.*;

/**
 * 初始化继承BaseService的Service
 * @author Joy
 * @date 2018/3/2
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BaseService {
}
