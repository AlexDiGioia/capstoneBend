package alexdigioia.capstoneBend.payloads;

import alexdigioia.capstoneBend.enums.TipoDisegno;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RichiestaDTO(
        //@NotEmpty(message = "Campo obbligatorio. Inserire l'ID utente.")
        //String utenteId,
        @Size(min = 10, max = 255, message = "La descrizione deve essere compresa tra 10 e 255 caratteri")
        String descrizione,
        @NotEmpty(message = "Campo obbligatorio. Inserire il tipo di disegno.")
        TipoDisegno tipoDisegno,
        boolean sfondo
) {
}