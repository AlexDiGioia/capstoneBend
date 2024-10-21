package alexdigioia.capstoneBend.services;

import alexdigioia.capstoneBend.entities.Utente;
import alexdigioia.capstoneBend.exceptions.BadRequestException;
import alexdigioia.capstoneBend.exceptions.NotFoundException;
import alexdigioia.capstoneBend.payloads.UtenteDTO;
import alexdigioia.capstoneBend.payloads.UtenteRespDTO;
import alexdigioia.capstoneBend.repositories.UtenteRepository;
import alexdigioia.capstoneBend.tools.Mailgun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private Mailgun mailSender;

    @Autowired
    private PasswordEncoder bcrypt;

    public Page<Utente> findAll(int page, int size, String sortBy) {
        if (page > 20) page = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.utenteRepository.findAll(pageable);
    }

    public Utente findById(UUID utenteId) {
        return this.utenteRepository.findById(utenteId)
                .orElseThrow(() -> new NotFoundException("L'utente con l'id " + utenteId + " non è stato trovato."));
    }

    public UtenteRespDTO findByIdAndUpdate(UUID utenteId, UtenteDTO utenteDTO) {
        Utente savedUtente = utenteRepository.findById(utenteId)
                .orElseThrow(() -> new NotFoundException("Utente non trovato con id: " + utenteId));

        savedUtente.setUsername(utenteDTO.username());
        savedUtente.setEmail(utenteDTO.email());
        savedUtente.setPassword(bcrypt.encode(utenteDTO.password()));
        savedUtente.setNome(utenteDTO.nome());
        savedUtente.setCognome(utenteDTO.cognome());

        Utente updatedUtente = utenteRepository.save(savedUtente);

        return new UtenteRespDTO(updatedUtente.getIdUtente());
    }

    public Utente findByEmail(String email) {
        return utenteRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("L'utente con l'email " + email + " non è stato trovato."));
    }

    public UtenteRespDTO save(UtenteDTO utenteDTO) {
        if (utenteRepository.existsByEmail(utenteDTO.email())) {
            throw new BadRequestException("Email già in uso!");
        }

        Utente savedUtente = new Utente();
        savedUtente.setUsername(utenteDTO.username());
        savedUtente.setEmail(utenteDTO.email());
        savedUtente.setPassword(bcrypt.encode(utenteDTO.password()));
        savedUtente.setNome(utenteDTO.nome());
        savedUtente.setCognome(utenteDTO.cognome());

        utenteRepository.save(savedUtente);
        mailSender.sendRegistrationEmail(savedUtente);

        return new UtenteRespDTO(savedUtente.getIdUtente());

    }

    public void deleteById(UUID id) {
        utenteRepository.deleteById(id);
    }
}

