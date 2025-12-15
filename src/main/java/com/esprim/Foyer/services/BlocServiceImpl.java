package com.esprim.Foyer.services;

import com.esprim.Foyer.entities.Bloc;
import com.esprim.Foyer.entities.Chambre;
import com.esprim.Foyer.repositories.IBlocRepository;
import com.esprim.Foyer.repositories.IChambreRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class BlocServiceImpl implements IBlocService {

    private IBlocRepository blocRepository;
    private IChambreRepository chambreRepository;

    @Override
    public List<Bloc> retrieveBlocs() {
        return blocRepository.findAll();
    }

    @Override
    public Bloc updateBloc(Bloc bloc) {
        return blocRepository.save(bloc);
    }

    @Override
    public Bloc addBloc(Bloc bloc) {
        return blocRepository.save(bloc);
    }

    @Override
    public Bloc retrieveBloc(long idBloc) {
        return blocRepository.findById(idBloc).orElse(null);
    }

    @Override
    public void removeBloc(long idBloc) {
        blocRepository.deleteById(idBloc);
    }

    @Override
    public Bloc affecterChambresABloc(List<Long> numChambre, long idBloc) {
        Bloc bloc = blocRepository.findById(idBloc).orElse(null);
        if (bloc != null) {
            for (Long numCh : numChambre) {
                Chambre chambre = chambreRepository.findById(numCh).orElse(null);
                if (chambre != null) {
                    chambre.setBloc(bloc);
                    chambreRepository.save(chambre);
                }
            }
            return bloc;
        }
        return null;
    }
    @Scheduled(cron = "0 */1 * * * *") // Every 1 minute
    public void afficherChambresParBlocEtType() {

        List<Chambre> chambres = chambreRepository.findAll();

        if (chambres.isEmpty()) {
            log.info("Aucune chambre en base.");
            return;
        }

        Map<String, Map<String, Integer>> map = new HashMap<>();

        for (Chambre chambre : chambres) {

            String blocName = (chambre.getBloc() != null) ? chambre.getBloc().getNomBloc() : "NON AFFECTÉ";
            String type = (chambre.getTypeC() != null) ? chambre.getTypeC().toString() : "TYPE NON DEFINI";

            map.putIfAbsent(blocName, new HashMap<>());
            Map<String, Integer> typeCount = map.get(blocName);

            typeCount.put(type, typeCount.getOrDefault(type, 0) + 1);
        }

        log.info(" Statistiques des chambres par Bloc:");

        map.forEach((bloc, types) -> {
            log.info("Bloc: " + bloc);
            types.forEach((type, count) -> log.info(" → " + type + ": " + count));
        });
    }

}