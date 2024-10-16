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
        //richiesta di connessione e get
        Document doc = Jsoup.connect(url).get();

        //seleziono le immagini
        Elements images = doc.select("img");

        //per ogni img ottengo il link e il totolo e creo l'oggetto Disegno
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

        return disegni;
    }
}