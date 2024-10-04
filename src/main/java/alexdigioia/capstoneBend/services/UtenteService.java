package alexdigioia.capstoneBend.services;

import alexdigioia.capstoneBend.entities.Utente;
import alexdigioia.capstoneBend.repositories.UtenteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UtenteService {

    private final UtenteRepository utenteRepository;

    public UtenteService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    public List<Utente> findAll() {
        return utenteRepository.findAll();
    }

    public Utente findById(UUID id) {
        return utenteRepository.findById(id).orElse(null);
    }

    public Utente save(Utente utente) {
        return utenteRepository.save(utente);
    }
    
    public void deleteById(UUID id) {
        utenteRepository.deleteById(id);
    }
}

