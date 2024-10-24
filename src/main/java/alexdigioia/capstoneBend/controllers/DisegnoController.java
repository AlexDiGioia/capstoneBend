package alexdigioia.capstoneBend.controllers;

import alexdigioia.capstoneBend.entities.Disegno;
import alexdigioia.capstoneBend.payloads.DisegnoDTO;
import alexdigioia.capstoneBend.services.DisegnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/disegni")
public class DisegnoController {

    @Autowired
    private DisegnoService disegnoService;
    
    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Disegno creaDisegno(@RequestParam("title") String title,
                               @RequestParam("file") MultipartFile file) {
        try {
            DisegnoDTO disegnoDTO = new DisegnoDTO(title, file);
            return disegnoService.save(disegnoDTO);
        } catch (IOException e) {
            throw new RuntimeException("Errore durante il caricamento dell'immagine e la creazione del disegno", e);
        }
    }

    @GetMapping("/{disegnoId}")
    public Disegno getDisegnoById(@PathVariable UUID disegnoId) {
        return disegnoService.findById(disegnoId);
    }

    @GetMapping
    public List<Disegno> getAllDisegni() {
        return disegnoService.findAll();
    }
}