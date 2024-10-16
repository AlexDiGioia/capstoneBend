package alexdigioia.capstoneBend.services;

import alexdigioia.capstoneBend.entities.Utente;
import alexdigioia.capstoneBend.exceptions.UnauthorizedException;
import alexdigioia.capstoneBend.payloads.UtenteLoginDTO;
import alexdigioia.capstoneBend.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UtenteService utentiService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;

    public String checkCredenzialiAndGeneraToken(UtenteLoginDTO body) {

        Utente trovato = this.utentiService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), trovato.getPassword())) {
            return jwtTools.createToken(trovato);
        } else {
            throw new UnauthorizedException("Credenziali errate");
        }
    }
}
