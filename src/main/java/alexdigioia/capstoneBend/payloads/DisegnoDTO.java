package alexdigioia.capstoneBend.payloads;

import org.springframework.web.multipart.MultipartFile;

public record DisegnoDTO(
        String title,
        MultipartFile file
) {
}