package alexdigioia.capstoneBend.tools;

import alexdigioia.capstoneBend.entities.Disegno;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DisegnoScraper {
    //uso la libreria Jsoup per fare il parsing della pagina di ArtStation e ottenere gli URL delle immagini

    public List<Disegno> scrapeArtStationPage(String url, String artistName) throws IOException {
        List<Disegno> disegni = new ArrayList<>();
        // Imposta un user-agent per simulare un browser reale
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")
                    .referrer("https://www.google.com") // Imposta il referer
                    .timeout(10000) // Imposta un timeout per la connessione
                    .get();

            // Seleziono le immagini
            Elements images = doc.select("img");

            // Per ogni img ottengo il link e il titolo e creo l'oggetto Disegno
            for (Element img : images) {
                String imageUrl = img.attr("src");
                String title = img.attr("alt");

                if (imageUrl != null && !imageUrl.isEmpty()) {
                    Disegno disegno = new Disegno();
                    disegno.setImageUrl(imageUrl);
                    disegno.setTitle(title != null ? title : "Untitled");
                    disegni.add(disegno);
                }
            }
        } catch (IOException e) {
            System.err.println("Errore durante il parsing della pagina di ArtStation: " + e.getMessage());
        }

        return disegni;
    }
}