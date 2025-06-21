package com.annotations;

import java.lang.annotation.*;
import java.time.Month;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface MyAnnotation {
    int value();
    String myStr() default "no str";
    Month[] myMonths();
}
