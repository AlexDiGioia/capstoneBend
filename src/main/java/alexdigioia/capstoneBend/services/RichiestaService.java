package alexdigioia.capstoneBend.services;

import alexdigioia.capstoneBend.entities.Richiesta;
import alexdigioia.capstoneBend.entities.Utente;
import alexdigioia.capstoneBend.enums.TipoDisegno;
import alexdigioia.capstoneBend.exceptions.NotFoundException;
import alexdigioia.capstoneBend.payloads.RichiestaDTO;
import alexdigioia.capstoneBend.repositories.DisegnoRepository;
import alexdigioia.capstoneBend.repositories.RichiestaRepository;
import alexdigioia.capstoneBend.repositories.UtenteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RichiestaService {

    @Autowired
    private final RichiestaRepository richiestaRepository;
    @Autowired
    private final UtenteRepository utenteRepository;
    @Autowired
    private final DisegnoRepository disegnoRepository;

    public RichiestaService(RichiestaRepository richiestaRepository, UtenteRepository utenteRepository, DisegnoRepository disegnoRepository) {
        this.richiestaRepository = richiestaRepository;
        this.utenteRepository = utenteRepository;
        this.disegnoRepository = disegnoRepository;
    }

    public Richiesta save(RichiestaDTO richiestaDTO) {

        UUID utenteId = UUID.fromString(richiestaDTO.utenteId());


        Utente utente = utenteRepository.findById(utenteId)
                .orElseThrow(() -> new NotFoundException("Utente non trovato con id: " + utenteId));

        int prezzo = calcolaPrezzo(richiestaDTO.tipoDisegno(), richiestaDTO.sfondo());

        Richiesta richiesta = new Richiesta(richiestaDTO.descrizione(), richiestaDTO.tipoDisegno(), richiestaDTO.sfondo(), utente);
        return richiestaRepository.save(richiesta);
    }

    private int calcolaPrezzo(TipoDisegno tipoDisegno, boolean sfondo) {
        int prezzo = switch (tipoDisegno) {
            case HALF_BODY_LINEART -> 35;
            case FULL_BODY_LINEART -> 70;
            case HALF_BODY_FULL_COLOR -> 90;
            case FULL_BODY_FULL_COLOR -> 140;
            case ILLUSTRAZIONE_COMPLETA -> 230;
        };
        if (sfondo) {
            prezzo += 40;
        }
        return prezzo;
    }

    @Transactional
    public void deleteRichiesta(UUID richiestaId) {
        Richiesta richiesta = richiestaRepository.findById(richiestaId)
                .orElseThrow(() -> new NotFoundException("Richiesta non trovata con id: " + richiestaId));
        richiestaRepository.delete(richiesta);
    }
}