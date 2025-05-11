package main;

public abstract class Serbatoio {
    private final Integer ID;
    private final Integer volume;

    public Serbatoio(Integer ID, Integer volume) {
        this.ID = ID;
        this.volume = volume;
    }

    public Integer getID() {
        return ID;
    }

    public Integer getVolume() {
        return volume;
    }
}
