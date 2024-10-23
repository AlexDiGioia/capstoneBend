package alexdigioia.capstoneBend.controllers;

import alexdigioia.capstoneBend.entities.Richiesta;
import alexdigioia.capstoneBend.payloads.RichiestaDTO;
import alexdigioia.capstoneBend.services.RichiestaService;
import alexdigioia.capstoneBend.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/richieste")
public class RichiestaController {

    @Autowired
    private RichiestaService richiestaService;

    @Autowired
    private UtenteService utenteService;

    @GetMapping
    public Page<Richiesta> getAll(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "20") int size,
                                  @RequestParam(defaultValue = "id") String sortBy) {
        return this.richiestaService.findAll(page, size, sortBy);
    }

    @GetMapping("/{richiestaId}")
    public Richiesta getById(@PathVariable String richiestaId) {
        UUID richiestaUUID = UUID.fromString(richiestaId);
        return this.richiestaService.findById(richiestaUUID);
    }

    @PutMapping("/{richiestaId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Richiesta updateRichiesta(@PathVariable UUID richiestaId, @RequestBody @Validated RichiestaDTO richiestaDTO) {
        return richiestaService.updateById(richiestaId, richiestaDTO);
    }

    @DeleteMapping("/{richiestaId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID richiestaId) {
        this.richiestaService.delete(richiestaId);
    }
}
