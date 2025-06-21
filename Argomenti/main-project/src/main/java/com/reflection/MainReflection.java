package com.reflection;

public class MainReflection {
    public static void main(String[] args) throws ClassNotFoundException {
        Persona persona = new Persona();
        Class<?> aClass;
        /* tre modi equivalenti */
        aClass = persona.getClass();
        aClass = Persona.class;
        aClass = Class.forName("com.reflection.Persona"); //ClassNotFoundException

        class Prova{}

        new ClassAnalyzer(Prova.class).analyzeName();

        ClassAnalyzer classAnalyzer = new ClassAnalyzer(persona);
        classAnalyzer.analyzeName();
        classAnalyzer.analyzeFields();
        classAnalyzer.analyzeConstructors();
        classAnalyzer.analyzeMethods();
        classAnalyzer.analyzeAnnotations();
    }
}


