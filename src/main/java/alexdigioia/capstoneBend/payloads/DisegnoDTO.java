package alexdigioia.capstoneBend.payloads;

import jakarta.validation.constraints.NotEmpty;

public record DisegnoDTO(
        @NotEmpty(message = "L'URL dell'immagine è obbligatorio.")
        String imageUrl,

        @NotEmpty(message = "Il titolo è obbligatorio.")
        String title
) {
}