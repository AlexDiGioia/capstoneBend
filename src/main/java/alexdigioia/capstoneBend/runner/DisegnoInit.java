package alexdigioia.capstoneBend.runner;

import alexdigioia.capstoneBend.entities.Disegno;
import alexdigioia.capstoneBend.payloads.DisegnoDTO;
import alexdigioia.capstoneBend.services.DisegnoService;
import alexdigioia.capstoneBend.tools.DisegnoScraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class DisegnoInit implements CommandLineRunner {

    @Autowired
    private DisegnoScraper disegnoScraper;

    @Autowired
    private DisegnoService disegnoService;

    @Override
    public void run(String... args) throws Exception {
        String url = "https://www.artstation.com/artist-page";
        String artistName = "Artist Name";

        try {

            List<Disegno> disegni = disegnoScraper.scrapeArtStationPage(url, artistName);


            for (Disegno disegno : disegni) {
                List<Disegno> existingDisegni = disegnoService.findByTitle(disegno.getTitle());

                boolean alreadyExists = existingDisegni.stream()
                        .anyMatch(existing -> existing.getImageUrl().equals(disegno.getImageUrl()));

                if (!alreadyExists) {
                    DisegnoDTO disegnoDTO = new DisegnoDTO(disegno.getImageUrl(), disegno.getTitle());
                    disegnoService.save(disegnoDTO);
                    System.out.println("Disegno salvato: " + disegno.getTitle());
                } else {
                    System.out.println("Disegno già esistente: " + disegno.getTitle());
                }
            }
        } catch (IOException e) {
            System.err.println("Errore durante il parsing della pagina di ArtStation: " + e.getMessage());
        }
    }
}