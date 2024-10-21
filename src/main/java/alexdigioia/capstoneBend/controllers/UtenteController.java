package alexdigioia.capstoneBend.controllers;

import alexdigioia.capstoneBend.entities.Utente;
import alexdigioia.capstoneBend.payloads.UtenteDTO;
import alexdigioia.capstoneBend.payloads.UtenteRespDTO;
import alexdigioia.capstoneBend.services.CommentoService;
import alexdigioia.capstoneBend.services.RichiestaService;
import alexdigioia.capstoneBend.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/utenti")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;
    @Autowired
    private CommentoService commentoService;
    @Autowired
    private RichiestaService richiestaService;

    @GetMapping
    public Page<Utente> findAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "idUtente") String sortBy) {
        return this.utenteService.findAll(page, size, sortBy);
    }

    @GetMapping("/{utenteId}")
    public Utente getById(@PathVariable UUID utenteId) {
        return this.utenteService.findById(utenteId);
    }

    @PutMapping("/{utenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UtenteRespDTO findByIdAndUpdate(@PathVariable UUID utenteId, @RequestBody @Validated UtenteDTO body) {
        return this.utenteService.findByIdAndUpdate(utenteId, body);
    }

    @DeleteMapping("/{utenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID utenteId) {
        this.utenteService.deleteById(utenteId);
    }

    @GetMapping("/me")
    public Utente findProfile(@AuthenticationPrincipal Utente utenteCorrenteAutenticato) {
        return utenteCorrenteAutenticato;
    }

    @PutMapping("/me")
    public UtenteRespDTO updateProfile(@AuthenticationPrincipal Utente utenteCorrenteAutenticato, @RequestBody @Validated UtenteDTO body) {
        return this.utenteService.findByIdAndUpdate(utenteCorrenteAutenticato.getIdUtente(), body);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal Utente utenteCorrenteAutenticato) {
        this.utenteService.deleteById(utenteCorrenteAutenticato.getIdUtente());
    }

}