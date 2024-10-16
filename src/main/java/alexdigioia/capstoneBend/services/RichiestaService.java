package alexdigioia.capstoneBend.services;

import alexdigioia.capstoneBend.entities.Richiesta;
import alexdigioia.capstoneBend.repositories.RichiestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RichiestaService {

    @Autowired
    private RichiestaRepository richiestaRepository;


    public List<Richiesta> findAll() {
        return richiestaRepository.findAll();
    }

    public Richiesta findById(UUID id) {
        return richiestaRepository.findById(id).orElse(null);
    }

    public Richiesta save(Richiesta richiesta) {
        return richiestaRepository.save(richiesta);
    }

    public void deleteById(UUID id) {
        richiestaRepository.deleteById(id);
    }
}