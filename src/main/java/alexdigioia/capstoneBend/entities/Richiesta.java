package alexdigioia.capstoneBend.entities;

import alexdigioia.capstoneBend.enums.TipoDisegno;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "richieste")
@Getter
@Setter
@NoArgsConstructor
public class Richiesta {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idRichiesta;

    private String descrizione;

    @Enumerated(EnumType.STRING)
    private TipoDisegno tipoDisegno;

    private boolean sfondoIncluso;
    private double prezzo;

    @ManyToOne
    @JoinColumn(name = "utente", nullable = false)
    private Utente utente;

    public Richiesta(String descrizione, TipoDisegno tipoDisegno, boolean sfondoIncluso, Utente utente) {
        this.descrizione = descrizione;
        this.tipoDisegno = tipoDisegno;
        this.sfondoIncluso = sfondoIncluso;
        this.utente = utente;
        this.prezzo = calcolaPrezzo(tipoDisegno, sfondoIncluso);
    }

    private double calcolaPrezzo(TipoDisegno tipoDisegno, boolean sfondoIncluso) {
        double basePrice;

        switch (tipoDisegno) {
            case HALF_BODY_LINEART:
                basePrice = 35.0;
                break;
            case FULL_BODY_LINEART:
                basePrice = 70.0;
                break;
            case HALF_BODY_FULL_COLOR:
                basePrice = 90.0;
                break;
            case FULL_BODY_FULL_COLOR:
                basePrice = 140.0;
                break;
            case ILLUSTRAZIONE_COMPLETA:
                basePrice = 230.0;
                break;
            default:
                throw new IllegalArgumentException("Tipo di disegno non valido!");
        }

        if (sfondoIncluso) {
            basePrice += 40.0;
        }

        return basePrice;
    }
}