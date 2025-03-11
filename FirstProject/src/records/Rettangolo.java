package records;

/* il record è final implicitamente e anche gli attributi (private) che sono solo in lettura
* tramite i metodi get */
public record Rettangolo(int base, int altezza) {
    public static final int NUMERO_LATI = 4;

    /* Costruttore canonico */
//    public Rettangolo(int base, int altezza){
//
//    }
    /* Costruttore canonico compatto -> prevede una fase di gestione iniziale */
    public Rettangolo{
        if(base<=0 || altezza<=0)
            throw new IllegalArgumentException();
    }
    /* Costruttore non canonico */
    public Rettangolo(int base, int altezza, String msg){
        this(base,altezza);
        System.out.println(msg);
    }

    public int area() {
        return base*altezza;
    }

    @Override
    public String toString(){
        return "";
    }
}
