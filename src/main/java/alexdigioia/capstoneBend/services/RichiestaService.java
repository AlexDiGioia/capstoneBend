package alexdigioia.capstoneBend.services;

import alexdigioia.capstoneBend.entities.Richiesta;
import alexdigioia.capstoneBend.entities.Utente;
import alexdigioia.capstoneBend.enums.TipoDisegno;
import alexdigioia.capstoneBend.exceptions.NotFoundException;
import alexdigioia.capstoneBend.payloads.RichiestaDTO;
import alexdigioia.capstoneBend.repositories.DisegnoRepository;
import alexdigioia.capstoneBend.repositories.RichiestaRepository;
import alexdigioia.capstoneBend.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RichiestaService {

    @Autowired
    private RichiestaRepository richiestaRepository;
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private DisegnoRepository disegnoRepository;

    public Richiesta findById(UUID richiestaId) {
        Richiesta richiesta = this.richiestaRepository.findById(richiestaId)
                .orElseThrow(() -> new NotFoundException("Il richiesta con l'id " + richiestaId + " non è stato trovato."));
        return richiesta;
    }

    public Page<Richiesta> findAll(int page, int size, String sortBy) {
        if (page > 20) page = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.richiestaRepository.findAll(pageable);
    }

    public Richiesta save(Utente utente, RichiestaDTO richiestaDTO) {

        Richiesta nuovaRichiesta = new Richiesta(richiestaDTO.descrizione(), richiestaDTO.tipoDisegno(), richiestaDTO.sfondo(), utente);
        nuovaRichiesta.setUtente(utente);

        int prezzo = calcolaPrezzo(richiestaDTO.tipoDisegno(), richiestaDTO.sfondo());
        nuovaRichiesta.setPrezzo(prezzo);

        return richiestaRepository.save(nuovaRichiesta);
    }

    public Richiesta updateById(UUID richiestaId, RichiestaDTO richiestaDTO) {

        Richiesta richiestaEsistente = richiestaRepository.findById(richiestaId)
                .orElseThrow(() -> new NotFoundException("Richiesta non trovata con id: " + richiestaId));

        richiestaEsistente.setDescrizione(richiestaDTO.descrizione());
        richiestaEsistente.setTipoDisegno(richiestaDTO.tipoDisegno());
        richiestaEsistente.setSfondoIncluso(richiestaDTO.sfondo());

        int prezzo = calcolaPrezzo(richiestaDTO.tipoDisegno(), richiestaDTO.sfondo());
        richiestaEsistente.setPrezzo(prezzo);

        return richiestaRepository.save(richiestaEsistente);
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

    public void delete(UUID richiestaId) {
        Richiesta richiesta = this.findById(richiestaId);
        this.richiestaRepository.delete(richiesta);
    }

}