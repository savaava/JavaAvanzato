package w02example.reflections;

import java.lang.annotation.*;

@Inherited
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DaImplementare {
    String value(); /* se fosse da solo non c'è bisogno di scrivere value="Mario" */
    String assegnatoA();
    String assegnatoB();
}
