package w02example.reflections;

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.*;

@Inherited
@Documented
@Target({TYPE, METHOD, CONSTRUCTOR, PACKAGE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DaImplementare {
    int value(); /* se fosse da solo non c'è bisogno di scrivere value="Mario" */
    String assegnatoA() default "nop";
    String assegnatoB() default "nop";
}
