package alexdigioia.capstoneBend.controllers;

import alexdigioia.capstoneBend.entities.Commento;
import alexdigioia.capstoneBend.entities.Disegno;
import alexdigioia.capstoneBend.entities.Utente;
import alexdigioia.capstoneBend.exceptions.BadRequestException;
import alexdigioia.capstoneBend.services.CommentoService;
import alexdigioia.capstoneBend.services.DisegnoService;
import alexdigioia.capstoneBend.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/commenti")
public class CommentoController {

    @Autowired
    private CommentoService commentoService;
    @Autowired
    private DisegnoService disegnoService;
    @Autowired
    private UtenteService utenteService;


    @PostMapping("/disegno/{disegnoId}/utente/{utenteId}")
    public ResponseEntity<Commento> aggiungiCommento(
            @PathVariable UUID disegnoId,
            @PathVariable UUID utenteId,
            @RequestBody String testo) {

        if (testo == null || testo.trim().isEmpty()) {
            throw new BadRequestException("Il commento non può essere vuoto!");
        }

        Disegno disegno = disegnoService.findById(disegnoId);
        Utente utente = utenteService.findById(utenteId);


        Commento commento = new Commento(disegno, utente, testo);
        commentoService.save(commento);

        return ResponseEntity.ok(commento);
    }

    @GetMapping("/disegno/{disegnoId}")
    public ResponseEntity<List<Commento>> getCommentiPerDisegno(@PathVariable UUID disegnoId) {
        Disegno disegno = disegnoService.findById(disegnoId);
        List<Commento> commenti = commentoService.findByDisegno(disegno);
        return ResponseEntity.ok(commenti);
    }

    @GetMapping("/utente/{utenteId}")
    public ResponseEntity<List<Commento>> getCommentiPerUtente(@PathVariable UUID utenteId) {
        Utente utente = utenteService.findById(utenteId);
        List<Commento> commenti = commentoService.findByUtente(utente);
        return ResponseEntity.ok(commenti);
    }
}