package alexdigioia.capstoneBend.repositories;

import alexdigioia.capstoneBend.entities.Richiesta;
import alexdigioia.capstoneBend.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RichiestaRepository extends JpaRepository<Richiesta, UUID> {

    List<Richiesta> findByUtente(Utente utente);
}