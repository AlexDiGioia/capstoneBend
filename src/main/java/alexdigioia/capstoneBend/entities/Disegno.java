package alexdigioia.capstoneBend.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Disegno {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idDisegno;

    private String imageUrl;

    private String title;

    @OneToMany(mappedBy = "disegno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commento> commenti;

}