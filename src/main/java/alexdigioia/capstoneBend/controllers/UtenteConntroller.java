package alexdigioia.capstoneBend.controllers;

import alexdigioia.capstoneBend.services.CommentoService;
import alexdigioia.capstoneBend.services.DisegnoService;
import alexdigioia.capstoneBend.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/utenti")
public class UtenteConntroller {

    @Autowired
    private UtenteService utenteService;
    @Autowired
    private CommentoService commentoService;
    @Autowired
    DisegnoService disegnoService;
}
