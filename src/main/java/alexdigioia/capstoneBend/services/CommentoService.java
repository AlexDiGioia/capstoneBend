package alexdigioia.capstoneBend.services;

import alexdigioia.capstoneBend.entities.Commento;
import alexdigioia.capstoneBend.entities.Disegno;
import alexdigioia.capstoneBend.entities.Utente;
import alexdigioia.capstoneBend.exceptions.BadRequestException;
import alexdigioia.capstoneBend.exceptions.NotFoundException;
import alexdigioia.capstoneBend.exceptions.UnauthorizedException;
import alexdigioia.capstoneBend.payloads.CommentoDTO;
import alexdigioia.capstoneBend.repositories.CommentoRepository;
import alexdigioia.capstoneBend.repositories.DisegnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Commento save(Utente utente, CommentoDTO commentoDTO) {
        UUID uuidDisegno;
        try {
            uuidDisegno = UUID.fromString(commentoDTO.disegnoId());
        } catch (Exception e) {
            throw new BadRequestException("L'id inserito non è valido! Necessario inserire un ID di Tipo UUID");
        }

        Disegno disegno = disegnoRepository.findById(uuidDisegno)
                .orElseThrow(() -> new NotFoundException(uuidDisegno));

        Commento nuovoCommento = new Commento(disegno, utente, commentoDTO.testo());
        
        disegno.addCommento(nuovoCommento);

        disegnoRepository.save(disegno);

        return nuovoCommento;
    }

    public Page<Commento> findAll(int page, int size, String sortBy) {
        if (page > 20) page = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.commentoRepository.findAll(pageable);
    }

    public Commento findById(UUID commentoId) {

        return this.commentoRepository.findById(commentoId)
                .orElseThrow(() -> new NotFoundException(commentoId));
    }

    public List<Commento> findByDisegno(Disegno disegno) {
        return commentoRepository.findByDisegno(disegno);
    }

    public List<Commento> findByUtente(Utente utente) {
        return commentoRepository.findByUtente(utente);
    }

    public Commento updateCommento(UUID commentoId, CommentoDTO commentoDTO, Utente utenteCorrente) {

        UUID disegnoId = UUID.fromString(commentoDTO.disegnoId());

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