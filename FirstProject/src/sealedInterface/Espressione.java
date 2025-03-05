package sealedInterface;

/* anche sulle interfacce ci sono le gerarchie quindi: */
public sealed interface Espressione permits Descrivibile {
    int valuta();
}

/* Per un'interfaccia non ha senso mettere final per cui a differenza delle classi si può:
 * - essere non-sealed
 * - Oppure sealed e quindi mantiene la caratteristica sigillata con permits...
* - Ma NON possiamo metterla final altrimenti non si potrebbe implementare */
non-sealed interface Descrivibile extends Espressione {
    void descrivi();
}