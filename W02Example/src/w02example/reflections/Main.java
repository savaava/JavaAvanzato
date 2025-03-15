package w02example.reflections;

import java.lang.reflect.Constructor;

public class Main {
    public static void main(String[] args) {
        Studente s = new Studente("Mario","Rossi","MRS0001","087654567");

        /* E' un metodo di tipo class che è una classe a sua volta di tipo parametro
        * Class<?>, dove ? sarà Studente */
        s.getClass();

        /* quindi può essere una qualsiasi classe. Racchiude la struttura della classe
        * e ci fornisce una scansione, come posso fare la scansione: */
        Class<?> c = s.getClass();
        /* altri modi per ottenere l'oggetto class: */
        Class<?> c1 = Studente.class;
        /* oppure ocn class for name:
        * siccome stiamo passando una stringa la classe potrebbe non esistere e quindi
        * gestire l'eccezione controllata: */
        try{
            Class<?> c2 = Class.forName("w02example.reflections.Studente");
        }catch(ClassNotFoundException e) {System.out.println(e.getMessage());}

        inspectClass(c);
    }

    /* creiamo un metodo per ispezionare una classe: */
    private static void inspectClass(Class<?> c){
        /* vi sono molti nomi: nome canonico e nome normale cambia molto
        * quando ci sono le classi innestate e quindi per questo esempio
        * getName o getCanonicName dà la stessa cosa */
        System.out.println(c.getCanonicalName());

        System.out.println("*** costruttori ***");
        Constructor<?>[] cc = c.getConstructors();
        /* getDeclaredConstructor
        declared -> se è una sottoclasse si dichiarano solo i metodi
        * della sottoclasse (quelli nuovi o quelli ridefiniti)
         senza declared -> tutti i metodi di tutta la gerarchia */
        for(Constructor<?> ci : cc){
            System.out.println(ci.getName());
        }
    }
}