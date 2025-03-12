package enumexamples;

/* Quando ho un insieme finito di istanze che si possono avere nel progetto ->
* usare le enum e non una classe */
public enum Colore {
    ROSSO(255,0,0),
    NERO(0,0,0),
    GIALLO(255,0,255),
    VERDE(0,255,0);

    private final int r,g,b;

    Colore(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int getR() { return r; }
    public int getG() { return g; }
    public int getB() { return b; }

    public String getRGB() { return "{"+r+","+g+","+b+"}"; }

    @Override
    public String toString(){ return getRGB(); }
}