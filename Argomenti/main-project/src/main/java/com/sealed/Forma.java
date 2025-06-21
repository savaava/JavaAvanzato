package com.sealed;

public abstract sealed class Forma permits Triangolo, Ellisse, Quadrilatero {
    public String colore;
}

sealed class Triangolo extends Forma permits TriangoloEquilatero {
    public double base;
    public double altezza;
}
non-sealed class TriangoloEquilatero extends Triangolo {
    public double lato;
}
class TriangoloEquilateroIsoscele extends TriangoloEquilatero {
    public double angolo;
}

final class Ellisse extends Forma {
    public double asseMaggiore;
    public double asseMinore;
}

/* Caso particolare: sealed senza permits
* Quadrilatero può essere estesa solo da una classe che si trova nello stesso file */
sealed class Quadrilatero extends Forma {}
non-sealed class Quadrato extends Quadrilatero {}
