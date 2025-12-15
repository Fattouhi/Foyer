package com.esprim.Foyer.services;

import com.esprim.Foyer.entities.Chambre;
import com.esprim.Foyer.entities.TypeChambre;
import com.esprim.Foyer.repositories.IChambreRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class ChambreServiceImpl implements IChambreService {

    private IChambreRepository chambreRepository;

    @Override
    public List<Chambre> retrieveAllChambres() {
        return chambreRepository.findAll();
    }

    @Override
    public Chambre addChambre(Chambre c) {
        return chambreRepository.save(c);
    }

    @Override
    public Chambre updateChambre(Chambre c) {
        return chambreRepository.save(c);
    }

    @Override
    public Chambre retrieveChambre(long idChambre) {
        return chambreRepository.findById(idChambre).orElse(null);
    }

    @Override
    public List<Chambre> getChambresParNomUniversite(String nomUniversite) {
        return chambreRepository.findChambresParNomUniversite(nomUniversite);
    }

    @Override
    public List<Chambre> getChambresParBlocEtType(long idBloc, TypeChambre typeC) {
        // Using JPQL method
        return chambreRepository.findChambresParBlocEtTypeJPQL(idBloc, typeC);

        // Alternative: Using Keywords method
        // return chambreRepository.findByBlocIdBlocAndTypeC(idBloc, typeC);
    }

    @Override
    public List<Chambre> getChambresNonReserveParNomUniversiteEtTypeChambre(String nomUniversite, TypeChambre type) {
        return chambreRepository.findChambresNonReserveesParNomUnivEtType(nomUniversite, type);
    }
    @Scheduled(cron = "*/15 * * * * *")
    public void pourcentageChambreParTypeChambre() {

        List<Chambre> chambreList = chambreRepository.findAll();
        int totalChambre = chambreList.size();

        log.info("Nombre total des chambres: " + totalChambre);

        if (totalChambre > 0) {

            Map<String, Integer> countByTypeChambre = new HashMap<>();

            for (Chambre chambre : chambreList) {

                // Prevent NullPointerException
                String type = (chambre.getTypeC() != null) ? chambre.getTypeC().toString() : "NON_AFFECTÉ";

                countByTypeChambre.put(type, countByTypeChambre.getOrDefault(type, 0) + 1);
            }

            // Logging percentages
            for (Map.Entry<String, Integer> entry : countByTypeChambre.entrySet()) {
                String type = entry.getKey();
                int count = entry.getValue();
                double percentage = (count * 100.0) / totalChambre;

                log.info("Type: " + type + " | Nombre: " + count + " | Pourcentage: " + String.format("%.2f", percentage) + "%");
            }
        }
    }
    @Scheduled(cron = "0 0 9 * * *") // Every day at 9 AM
    public void nbPlacesDisponibleParChambreAnneeEnCours() {
        log.info("=== Rapport des chambres non réservées pour l'année en cours ===");

        List<Chambre> chambresNonReservees = chambreRepository.findChambresNonReservees();

        if (chambresNonReservees.isEmpty()) {
            log.info("Aucune chambre non réservée trouvée.");
            return;
        }

        // Group by university
        Map<String, List<Chambre>> chambresByUniversite = new HashMap<>();

        for (Chambre chambre : chambresNonReservees) {
            String nomUniversite = "NON AFFECTÉ";
            if (chambre.getBloc() != null &&
                    chambre.getBloc().getFoyer() != null &&
                    chambre.getBloc().getFoyer().getUniversite() != null) {
                nomUniversite = chambre.getBloc().getFoyer().getUniversite().getNomUniversite();
            }

            chambresByUniversite.putIfAbsent(nomUniversite, new java.util.ArrayList<>());
            chambresByUniversite.get(nomUniversite).add(chambre);
        }

        // Log results
        chambresByUniversite.forEach((universite, chambres) -> {
            log.info("Université: " + universite);
            log.info("  Nombre de chambres non réservées: " + chambres.size());

            Map<String, Integer> countByType = new HashMap<>();
            for (Chambre ch : chambres) {
                String type = (ch.getTypeC() != null) ? ch.getTypeC().toString() : "NON DÉFINI";
                countByType.put(type, countByType.getOrDefault(type, 0) + 1);
            }

            countByType.forEach((type, count) ->
                    log.info("    → " + type + ": " + count + " chambres disponibles")
            );
        });
    }
}