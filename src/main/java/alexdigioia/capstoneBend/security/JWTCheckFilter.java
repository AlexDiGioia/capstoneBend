package alexdigioia.capstoneBend.security;

import alexdigioia.capstoneBend.entities.Utente;
import alexdigioia.capstoneBend.exceptions.UnauthorizedException;
import alexdigioia.capstoneBend.services.UtenteService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTCheckFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UtenteService utenteService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Per favore inserisci correttamente il token nell'Authorization Header");

        String accessToken = authHeader.substring(7);
        jwtTools.verifyToken(accessToken);
        String id = jwtTools.extractIdFromToken(accessToken);
        Utente currentUser = this.utenteService.findById(UUID.fromString(id));

        Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);

    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        AntPathMatcher matcher = new AntPathMatcher();


        return matcher.match("/auth/**", path) ||
                matcher.match("/disegni", path) ||
                (matcher.match("/disegni/*", path) &&
                        !matcher.match("/disegni/upload", path));
    }

}
