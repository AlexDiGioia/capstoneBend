package alexdigioia.capstoneBend.services;

import alexdigioia.capstoneBend.entities.Commento;
import alexdigioia.capstoneBend.entities.Disegno;
import alexdigioia.capstoneBend.entities.Utente;
import alexdigioia.capstoneBend.repositories.CommentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentoService {

    @Autowired
    private final CommentoRepository commentoRepository;

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
}