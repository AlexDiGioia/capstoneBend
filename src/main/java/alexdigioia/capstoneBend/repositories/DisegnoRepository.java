package alexdigioia.capstoneBend.repositories;

import alexdigioia.capstoneBend.entities.Disegno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DisegnoRepository extends JpaRepository<Disegno, UUID> {

    List<Disegno> findByTitleContainingIgnoreCase(String title);
}