package alexdigioia.capstoneBend.services;

import alexdigioia.capstoneBend.entities.Commento;
import alexdigioia.capstoneBend.entities.Disegno;
import alexdigioia.capstoneBend.entities.Utente;
import alexdigioia.capstoneBend.exceptions.NotFoundException;
import alexdigioia.capstoneBend.exceptions.UnauthorizedException;
import alexdigioia.capstoneBend.payloads.CommentoDTO;
import alexdigioia.capstoneBend.repositories.CommentoRepository;
import alexdigioia.capstoneBend.repositories.DisegnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentoService {

    @Autowired
    private final CommentoRepository commentoRepository;

    @Autowired
    private DisegnoRepository disegnoRepository;

    public CommentoService(CommentoRepository commentoRepository) {
        this.commentoRepository = commentoRepository;
    }

    public Commento save(Commento commento) {
        return commentoRepository.save(commento);
    }

    public List<Commento> findByDisegno(Disegno disegno) {
        return commentoRepository.findByDisegno(disegno);
    }

    public List<Commento> findByUtente(Utente utente) {
        return commentoRepository.findByUtente(utente);
    }

    public Commento updateCommento(UUID commentoId, CommentoDTO commentoDTO, Utente utenteCorrente) {
        UUID utenteId = UUID.fromString(commentoDTO.utenteId());
        UUID disegnoId = UUID.fromString(commentoDTO.disegnoId());

        // Verifica che l'utente loggato sia lo stesso dell'ID nel commento
        if (!utenteCorrente.getIdUtente().equals(utenteId)) {
            throw new UnauthorizedException("Non sei autorizzato a modificare questo commento.");
        }

        Disegno disegno = disegnoRepository.findById(disegnoId)
                .orElseThrow(() -> new NotFoundException("Disegno non trovato con id: " + disegnoId));

        Commento commento = commentoRepository.findById(commentoId)
                .orElseThrow(() -> new NotFoundException("Commento non trovato con id: " + commentoId));

        commento.setTesto(commentoDTO.testo());
        commento.setDisegno(disegno);

        return commentoRepository.save(commento);
    }

    public void deleteCommento(UUID commentoId, Utente utenteCorrente) {
        Commento commento = commentoRepository.findById(commentoId)
                .orElseThrow(() -> new NotFoundException("Commento non trovato con id: " + commentoId));

        if (!commento.getUtente().getIdUtente().equals(utenteCorrente.getIdUtente())) {
            throw new UnauthorizedException("Non sei autorizzato a eliminare questo commento.");
        }

        commentoRepository.delete(commento);
    }
}