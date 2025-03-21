package w02example.reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

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

        /* Possiamo definire PROCESSORI DI ANNOTAZIONE agiscono a lvl di compilazione
        * Le annotazioni vengono rilevate
        * Meccanismo per la validazione, documentazione e generazione del codice
        * package javax.annotation.processing per i metodi per definire l'annotazione
        * e le sue proprietà
        * possiamo gestire anche i livelli: errore o semplice warning in base a cosa non
        * è stato rispettato nella definizione del metodo o classe annotata con la mia
        * annotazione personalizzata. Voglio che gli avvertimenti avvengano a tempo di
        * compilazione
        * AbstractProcessor del package di prima
        * process da ridefinire con 2 parametri
        * - set di annotations limitato superiormente (TypeElement != ElementType)
        * - RoundEnvironment -> è un ciclo di acquisizione di annotazioni
        * un'annotazione può essere gestita da più cicli (da più fasi) perchè il processore
        * ha bisogno di un round successivo per generare altro codice
        * Ci possono essere più processori e più round per ogni processore
        *
        *
        * Bisogna importare la dipendenza: importare il .jar del progetto con
        * l'annotazione e il processore dell'annotazione
        * Il progetto che importa la dipendenza devo farlo nel classPath
        * 2 per farlo funzionare nell'ambiente dobbiamo abilitare l'Annotation Processing
        * in Compiling e aggiungere il processore di annotazione con il path completo:
        * annotation.processor. */
    }

    /* creiamo un metodo per ispezionare una classe: */
    private static void inspectClass(Class<?> c) {
        /* vi sono molti nomi: nome canonico e nome normale cambia molto
         * quando ci sono le classi innestate e quindi per questo esempio
         * getName o getCanonicName dà la stessa cosa */
        System.out.println(c.getCanonicalName());

        System.out.println("*** Costruttori ***");
        Constructor<?>[] cc = c.getConstructors();
        /* getDeclaredConstructor
        declared -> se è una sottoclasse si dichiarano solo i metodi
        * della sottoclasse (quelli nuovi o quelli ridefiniti)
         senza declared -> tutti i metodi di tutta la gerarchia */
        StringBuilder signature = new StringBuilder();
        for (Constructor<?> ci : cc) {
            signature.append(Modifier.toString(ci.getModifiers())).append(" ");
            signature.append(c.getSimpleName()).append("( ");
            Parameter pVett[] = ci.getParameters();
            for (Parameter pi : pVett) {
                signature.append(pi.getType().getSimpleName()).append(" ");
                signature.append(pi.getName()).append(",");
            }
            signature.setCharAt(signature.length() - 1, ')');

            signature.append("\n");
        }
        System.out.println(signature.toString());

        System.out.println("*** Metodi ***");
        /* senza declared mi mostra tutti i metodi anche quelli delle superclassi
        * con declared solo i metodi definiti nella classe stessa o ridefiniti */
        Method m[] = c.getDeclaredMethods();

        StringBuilder s = new StringBuilder();
        for(Method mi : m){
            s.append(Modifier.toString(mi.getModifiers())).append(" ");
            s.append(mi.getReturnType().getSimpleName()).append(" ");
            s.append(mi.getName()).append("( ");

            Parameter p[] = mi.getParameters();
            for(Parameter pi : p){
                s.append(pi.getType().getSimpleName()).append(" ");
                s.append(pi.getName()).append(",");
            }
            s.setCharAt(s.length()-1, ')');

            DaImplementare di = null;
            if((di = mi.getAnnotation(DaImplementare.class)) != null){
                s.append(" ----> "+di.toString()+": "+di.value()+", "+di.assegnatoA()+", "+di.assegnatoB());
            }
            /* senza meta annotazioni non riesce e a individuare l'annotazione
            * quindi con le meta annotazioni si annotano le annotazioni e
            * si definisce Retention la policy di visibilità (di default è di class)
            * a che livello l'annotazione deve essere visibile ?
            * (1 RUNTIME, 2 CLASS (default), 3 SOURCE)
            * @Documented  */

            s.append("\n");
        }
        System.out.println(s);
    }
}