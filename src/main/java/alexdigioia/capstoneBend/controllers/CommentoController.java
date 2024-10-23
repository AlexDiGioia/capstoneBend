package alexdigioia.capstoneBend.controllers;

import alexdigioia.capstoneBend.entities.Commento;
import alexdigioia.capstoneBend.entities.Disegno;
import alexdigioia.capstoneBend.entities.Utente;
import alexdigioia.capstoneBend.payloads.CommentoDTO;
import alexdigioia.capstoneBend.services.CommentoService;
import alexdigioia.capstoneBend.services.DisegnoService;
import alexdigioia.capstoneBend.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
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

    @GetMapping
    public Page<Commento> getAll(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "20") int size,
                                 @RequestParam(defaultValue = "id") String sortBy) {
        return this.commentoService.findAll(page, size, sortBy);
    }

    @GetMapping("/{commentoId}")
    public Commento getById(@PathVariable UUID commentoId) {
        return this.commentoService.findById(commentoId);
    }


    @PostMapping("/crea")
    @PreAuthorize("hasAnyAuthority('ADMIN','BASIC_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public Commento save(@AuthenticationPrincipal Utente utenteCorrenteAutenticato, @RequestBody @Validated CommentoDTO commentoDTO) {

        return commentoService.save(utenteCorrenteAutenticato, commentoDTO);
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