package com.servicesproject.model;

public class Utente {
    private final String name;
    private final Integer age;

    public Utente(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }
    public Integer getAge() {
        return age;
    }
}
