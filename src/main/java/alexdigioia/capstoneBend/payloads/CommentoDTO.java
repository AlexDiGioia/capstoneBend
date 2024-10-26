package alexdigioia.capstoneBend.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CommentoDTO(
        @NotEmpty(message = "Il testo del commento è obbligatorio.")
        @Size(max = 255, message = "Il testo del commento non può superare i 255 caratteri.")
        String testo,
        
        @NotEmpty(message = "Campo obbligatorio. Inserire l'ID del disegno.")
        String disegnoId
) {
}