package alexdigioia.capstoneBend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Richiesta {

    @Id
    @GeneratedValue
    private UUID idRichiesta;

    @Column(nullable = false)
    private String descrizione;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;
}