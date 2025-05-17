package com.ymj.campuscanvas.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Sortable {
    // 允许自定义数据库列名（解决驼峰转下划线问题）
    String column() default "";
}
