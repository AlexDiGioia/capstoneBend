package alexdigioia.capstoneBend.enums;

public enum TipoDisegno {
    HALF_BODY_LINEART("Halfbody lineart"),
    FULL_BODY_LINEART("Fullbody lineart"),
    HALF_BODY_FULL_COLOR("Halfbody full color"),
    FULL_BODY_FULL_COLOR("Fullbody full color"),
    ILLUSTRAZIONE_COMPLETA("Illustrazione completa");

    private final String descrizione;

    TipoDisegno(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getDescrizione() {
        return descrizione;
    }
}