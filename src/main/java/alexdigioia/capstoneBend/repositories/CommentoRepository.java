package alexdigioia.capstoneBend.repositories;

import alexdigioia.capstoneBend.entities.Commento;
import alexdigioia.capstoneBend.entities.Disegno;
import alexdigioia.capstoneBend.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentoRepository extends JpaRepository<Commento, UUID> {

    List<Commento> findByDisegno(Disegno disegno);

    List<Commento> findByUtente(Utente utente);
}