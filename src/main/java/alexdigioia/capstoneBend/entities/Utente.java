package alexdigioia.capstoneBend.entities;

import alexdigioia.capstoneBend.enums.Ruolo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "utenti")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"password", "listaAmici", "enabled", "accountNonLocked", "accountNonExpired", "credentialsNonExpired", "authorities"})
public class Utente implements UserDetails {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idUtente;
    private String username;
    private String email;
    private String password;
    private String nome;
    private String cognome;
    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;

    @JsonIgnore
    @OneToMany(mappedBy = "utente")
    private List<Commento> commentiList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Richiesta> richiesteList;

    public Utente(String username, String email, String password, String nome, String cognome, Ruolo ruolo) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.ruolo = ruolo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.ruolo.name()));
    }

}