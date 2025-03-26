package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME) /* altrimenti non è possibile accedere tramite reflection anche a runtime dal processore */
public @interface FieldNumberConstraint {
    int value() default 0;
}
