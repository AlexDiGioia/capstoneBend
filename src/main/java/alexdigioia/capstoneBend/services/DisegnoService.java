package alexdigioia.capstoneBend.services;

import alexdigioia.capstoneBend.entities.Disegno;
import alexdigioia.capstoneBend.exceptions.NotFoundException;
import alexdigioia.capstoneBend.payloads.DisegnoDTO;
import alexdigioia.capstoneBend.repositories.DisegnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DisegnoService {

    @Autowired
    private DisegnoRepository disegnoRepository;

    public Disegno save(DisegnoDTO disegnoDTO) {
        Disegno disegno = new Disegno();
        disegno.setImageUrl(disegnoDTO.imageUrl());
        disegno.setTitle(disegnoDTO.title());
        return disegnoRepository.save(disegno);
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