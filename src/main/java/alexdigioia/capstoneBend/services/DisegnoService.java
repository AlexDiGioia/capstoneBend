package alexdigioia.capstoneBend.services;

import alexdigioia.capstoneBend.entities.Disegno;
import alexdigioia.capstoneBend.exceptions.NotFoundException;
import alexdigioia.capstoneBend.repositories.DisegnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DisegnoService {

    @Autowired
    private DisegnoRepository disegnoRepository;


    public Disegno save(Disegno disegno) {
        return disegnoRepository.save(disegno);
    }

    public Disegno update(UUID id, Disegno updatedDisegno) {
        Optional<Disegno> existingDisegnoOpt = disegnoRepository.findById(id);
        if (existingDisegnoOpt.isPresent()) {
            Disegno existingDisegno = existingDisegnoOpt.get();
            existingDisegno.setImageUrl(updatedDisegno.getImageUrl());
            existingDisegno.setTitle(updatedDisegno.getTitle());
            return disegnoRepository.save(existingDisegno);
        } else {
            throw new RuntimeException("Disegno non trovato con id: " + id);
        }
    }

    public Disegno findById(UUID disegnoId) {
        return this.disegnoRepository.findById(disegnoId)
                .orElseThrow(() -> new NotFoundException("Il disegno con l'id " + disegnoId + " non è stato trovato."));
    }

    public List<Disegno> findAll() {
        return disegnoRepository.findAll();
    }

    public void deleteById(UUID id) {
        if (disegnoRepository.existsById(id)) {
            disegnoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Disegno non trovato con id: " + id);
        }
    }


    public List<Disegno> findByTitle(String title) {
        return disegnoRepository.findByTitleContainingIgnoreCase(title);
    }
}