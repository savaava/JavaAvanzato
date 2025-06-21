package com.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.List;

public class ClassAnalyzer {
    private final Class<?> classToAnalyze;

    public ClassAnalyzer(String className) throws ClassNotFoundException {
        this.classToAnalyze = Class.forName(className); //ClassNotFoundException
    }
    public ClassAnalyzer(Object objToAnalyze){
        this.classToAnalyze = objToAnalyze.getClass();
    }
    public ClassAnalyzer(Class<?> classToAnalyze){
        this.classToAnalyze = classToAnalyze;
    }

    public Class<?> getClassToAnalyze() {
        return classToAnalyze;
    }

    public void analyzeName() {
        System.out.println("\n\n******Analisi del nome della classe");
        System.out.println(
                classToAnalyze.getName()+"\n"+
                classToAnalyze.getCanonicalName()+"\n"+
                classToAnalyze.getSimpleName()
        );
    }

    public void analyzeFields() {
        System.out.println("\n\n******Analisi dei campi");
        Field[] fields = classToAnalyze.getDeclaredFields();

        for (Field f : fields) {
            String modifier = Modifier.toString(f.getModifiers());
            System.out.println(modifier + " " + f.getType().getSimpleName() + " " + f.getName());
        }
    }

    public void analyzeConstructors() {
        System.out.println("\n\n******Analisi dei costruttori");
        Constructor<?>[] cc = classToAnalyze.getConstructors();

        for (Constructor<?> c : cc) {
            analyzeAnnotations(c.getDeclaredAnnotations());
            String modifier = Modifier.toString(c.getModifiers());
            System.out.print(modifier + " " + classToAnalyze.getSimpleName() + "(");

            analyzeParameters(c.getParameters());
        }
    }

    public void analyzeMethods() {
        System.out.println("\n\n******Analisi dei metodi");
        Method[] methods = classToAnalyze.getDeclaredMethods();

        for (Method m : methods) {
            analyzeAnnotations(m.getDeclaredAnnotations());
            String modifier = Modifier.toString(m.getModifiers());
            System.out.print(modifier + " " + m.getReturnType().getSimpleName() + " " + m.getName() + "(");

            analyzeParameters(m.getParameters());
        }
    }

    private void analyzeParameters(Parameter[] parameters) {
        for (int i = 0; i < parameters.length; i++) {
            System.out.print(parameters[i].getType().getSimpleName()+" "+parameters[i].getName());

            if (i < parameters.length - 1)
                System.out.print(", ");
        }
        System.out.println(")");
    }
    private void analyzeAnnotations(Annotation[] annotations){
        List.of(annotations).stream()
                .map(a -> "@"+a.annotationType().getSimpleName())
                .forEach(System.out::println);
    }

    public void analyzeAnnotations() {
        System.out.println("\n\n******Analisi delle annotazioni");
        analyzeAnnotations(classToAnalyze.getDeclaredAnnotations());
    }
}
