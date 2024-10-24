package alexdigioia.capstoneBend.services;

import alexdigioia.capstoneBend.entities.Disegno;
import alexdigioia.capstoneBend.exceptions.NotFoundException;
import alexdigioia.capstoneBend.payloads.DisegnoDTO;
import alexdigioia.capstoneBend.repositories.DisegnoRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class DisegnoService {

    @Autowired
    private DisegnoRepository disegnoRepository;

    @Autowired
    private Cloudinary cloudinary;

    public Disegno save(DisegnoDTO disegnoDTO) throws IOException {
        // Carica l'immagine su Cloudinary e ottieni l'URL
        String imageUrl = this.uploadImage(disegnoDTO.file());

        Disegno disegno = new Disegno();
        disegno.setTitle(disegnoDTO.title());
        disegno.setImageUrl(imageUrl);

        return disegnoRepository.save(disegno);
    }

    public String uploadImage(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap("resource_type", "auto"));
        return uploadResult.get("secure_url").toString();
    }

    public Disegno findById(UUID disegnoId) {
        return this.disegnoRepository.findById(disegnoId)
                .orElseThrow(() -> new NotFoundException("Il disegno con l'id " + disegnoId + " non è stato trovato."));
    }

    public List<Disegno> findAll() {
        return disegnoRepository.findAll();
    }

    public void deleteById(UUID id) {
        if (disegnoRepository.existsById(id)) {
            disegnoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Disegno non trovato con id: " + id);
        }
    }

    public List<Disegno> findByTitle(String title) {
        return disegnoRepository.findByTitleContainingIgnoreCase(title);
    }
}