public sealed class Forma permits Ellisse, Triangolo {
    /* final -> no extends available */

}
/* posso non mettere Triangolo se è nello stesso file ma Ellisse lo dovrei mettere scrivere quindi:
* - public sealed class Forma permits Ellisse, Triangolo
* - public sealed class Forma (Ellisse non estende Forma perchè sta in un altro file) */

/*
* un'altra classe nello stesso file è possibile
* l'unica classe public può essere solo una
* vantaggio organizzativo però buona pratica file separati o classi innestate
* Classi Sigillate dove vincolo la gerarchia: dalla jdk 15 in poi si mette sealed anzichè final
* e permits per specificare precisamente da quali classe può essere estese
* vincolo per Triangolo: può essere a sua volta sigillata ma deve
* - essere non-sealed
* - Oppure sealed e quindi mantiene la caratteristica sigillata con permits...
* - final
*/
sealed class Triangolo extends Forma permits GG {

}

non-sealed class GG extends Triangolo {

}

/*
* le classe sigillate possono essere estese se sono nello stesso package della
* classe sigillata o nello stesso modulo nominativo (che ha un nome e raccoglie tutti i package)
* package differenti ma fanno parte tutti dello stesso modulo nominativo
*/
