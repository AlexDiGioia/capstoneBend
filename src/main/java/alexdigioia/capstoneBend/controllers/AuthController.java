package alexdigioia.capstoneBend.controllers;

import alexdigioia.capstoneBend.exceptions.BadRequestException;
import alexdigioia.capstoneBend.payloads.UtenteDTO;
import alexdigioia.capstoneBend.payloads.UtenteLoginDTO;
import alexdigioia.capstoneBend.payloads.UtenteLoginRespDTO;
import alexdigioia.capstoneBend.payloads.UtenteRespDTO;
import alexdigioia.capstoneBend.services.AuthService;
import alexdigioia.capstoneBend.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173") // annotazione per abilitare CORS
public class AuthController {

    @Autowired
    private UtenteService utentiService;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public UtenteLoginRespDTO login(@RequestBody @Validated UtenteLoginDTO body) {
        return new UtenteLoginRespDTO(this.authService.checkCredenzialiAndGeneraToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UtenteRespDTO creaUtente(@RequestBody @Validated UtenteDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String messages = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException(("Errori nel Payload. " + messages));
        } else {
            return new UtenteRespDTO(this.utentiService.save(body).utenteId());
        }
    }


}
