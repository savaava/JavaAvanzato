package sealedInterface;

public class EspressioneSomma implements Descrivibile {
    private int a,b;

    public EspressioneSomma(int a, int b){
        this.a = a;
        this.b = b;
    }

    @Override
    public void descrivi() {
        /* la descrizione è un moto per stampare il risultato */
        System.out.println("Il risultato di "+a+"+"+b+"="+valuta());
    }

    @Override
    public int valuta() {
        return a+b;
    }
}
