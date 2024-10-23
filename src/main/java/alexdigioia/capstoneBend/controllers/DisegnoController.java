package alexdigioia.capstoneBend.controllers;

import alexdigioia.capstoneBend.entities.Disegno;
import alexdigioia.capstoneBend.services.DisegnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/disegni")
public class DisegnoController {

    @Autowired
    private DisegnoService disegnoService;

    @GetMapping("/{disegnoId}")
    public Disegno getDisegnoById(@PathVariable UUID disegnoId) {
        return disegnoService.findById(disegnoId);
    }

    @GetMapping
    public List<Disegno> getAllDisegni() {
        return disegnoService.findAll();
    }
}