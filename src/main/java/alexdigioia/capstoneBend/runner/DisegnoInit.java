package alexdigioia.capstoneBend.runner;

//@Component
//public class DisegnoInit implements CommandLineRunner {
//
//    @Autowired
//    private DisegnoScraper disegnoScraper;
//
//    @Autowired
//    private DisegnoService disegnoService;
//
//    @Override
//    public void run(String... args) throws Exception {
//        String url = "https://www.artstation.com/erwinhidalgo9";
//        String artistName = "Erwin";
//
//        try {
//
//            List<Disegno> disegni = disegnoScraper.scrapeArtStationPage(url, artistName);
//
//
//            for (Disegno disegno : disegni) {
//                List<Disegno> existingDisegni = disegnoService.findByTitle(disegno.getTitle());
//
//                boolean alreadyExists = existingDisegni.stream()
//                        .anyMatch(existing -> existing.getImageUrl().equals(disegno.getImageUrl()));
//
//                if (!alreadyExists) {
//                    DisegnoDTO disegnoDTO = new DisegnoDTO(disegno.getImageUrl(), disegno.getTitle());
//                    disegnoService.save(disegnoDTO);
//                    System.out.println("Disegno salvato: " + disegno.getTitle());
//                } else {
//                    System.out.println("Disegno già esistente: " + disegno.getTitle());
//                }
//            }
//        } catch (IOException e) {
//            System.err.println("Errore durante il parsing della pagina di ArtStation: " + e.getMessage());
//        }
//    }
//}