package com.annotations;

import java.util.List;

@Deprecated
@MyAnnotation("Sono il padre")
public sealed class Padre permits Figlio{}

final class Figlio extends Padre {}

@SuppressWarnings({"deprecation"})
class MainProvaAnnotation {
    public static void main(String[] args) {
        System.out.println("Annotazioni della classe Padre:");
        List.of(Padre.class.getDeclaredAnnotations()).forEach(System.out::println);
        // @java.lang.Deprecated(forRemoval=false, since="")
        // @com.annotations.MyAnnotation(otherValue=7, value="Sono il padre")


        Class<?> figlioClass = Figlio.class;
        System.out.println("\nAnnotazioni della classe Figlia:");
        List.of(figlioClass.getAnnotations()).forEach(System.out::println);
        // @com.annotations.MyAnnotation(otherValue=7, value="Sono il padre")


        if(figlioClass.isAnnotationPresent(MyAnnotation.class)){
            MyAnnotation myAnnotation = figlioClass.getAnnotation(MyAnnotation.class);
            System.out.println("value: "+myAnnotation.value());
            System.out.println("other value: "+myAnnotation.otherValue());
        }
    }
}
