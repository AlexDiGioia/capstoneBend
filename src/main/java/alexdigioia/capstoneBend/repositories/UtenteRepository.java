package alexdigioia.capstoneBend.repositories;

import alexdigioia.capstoneBend.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, UUID> {
    Optional<Utente> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM Utente u LEFT JOIN FETCH u.richiesteList WHERE u.idUtente = :idUtente")
    Optional<Utente> findByIdWithRichieste(@Param("idUtente") UUID idUtente);

}
