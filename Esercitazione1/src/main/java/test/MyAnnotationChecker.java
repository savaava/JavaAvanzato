package test;

import data.LongClass;
import data.ShortClass;
import exceptions.AnnotationException;
import myannotations.AtMostThree;
import myannotations.FieldNumberConstraint;

import java.lang.annotation.Annotation;

public class MyAnnotationChecker {
    public static void main(String[] args) {
        checkClass(ShortClass.class);
        checkClass(LongClass.class);
    }

    private static void checkClass(Class<?> c){
        AtMostThree a1 = null;
        FieldNumberConstraint a2 = null;
        for(Annotation annotation : c.getAnnotations()){
            if(annotation instanceof AtMostThree){
                a1 = (AtMostThree) annotation;
            }else if(annotation instanceof FieldNumberConstraint){
                a2 = (FieldNumberConstraint) annotation;
            }
        }
        if(a1==null || a2==null)
            return;

        if(c.getDeclaredMethods().length>3)
            throw new AnnotationException("La classe "+c.getSimpleName()+" contiene più di 3 metodi");
        System.out.println("La classe "+c.getSimpleName()+" non contiene più di 3 metodi");

        int annotationValue = a2.value();
        if(c.getDeclaredFields().length != annotationValue)
            throw new AnnotationException("La classe "+c.getSimpleName()+" non contiene "+annotationValue+" attributi");
        System.out.println("La classe "+c.getSimpleName()+" contiene "+annotationValue+" attributi");
    }
}
