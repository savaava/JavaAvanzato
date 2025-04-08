package com.savaava;

@FunctionalInterface
public interface EstraiCampo<T> {
    T estrai(Film f);
}

/*
* @FunctionalInterface
public interface EstraiCampo<T,U> {
    T estrai(U f);
}
* */
