package com.annotations;

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.*;

@Inherited
@Documented
@Target({TYPE, CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    String value();
    int otherValue() default 7;
}
