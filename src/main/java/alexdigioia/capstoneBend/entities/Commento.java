package alexdigioia.capstoneBend.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "commenti")
@Getter
@Setter
@NoArgsConstructor
public class Commento {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idCommento;

    @ManyToOne
    @JoinColumn(name = "disegno_id", nullable = false)
    private Disegno disegno;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    private String testo;


    public Commento(Disegno disegno, Utente utente, String testo) {
        this.disegno = disegno;
        this.utente = utente;
        this.testo = testo;
    }


}