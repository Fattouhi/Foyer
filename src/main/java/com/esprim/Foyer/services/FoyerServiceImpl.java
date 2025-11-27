package com.esprim.Foyer.services;

import com.esprim.Foyer.entities.Bloc;
import com.esprim.Foyer.entities.Foyer;
import com.esprim.Foyer.entities.Universite;
import com.esprim.Foyer.repositories.IFoyerRepository;
import com.esprim.Foyer.repositories.IUniversiteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FoyerServiceImpl implements IFoyerService {

    private IFoyerRepository foyerRepository;
    private IUniversiteRepository universiteRepository;

    @Override
    public List<Foyer> retrieveAllFoyers() {
        return foyerRepository.findAll();
    }

    @Override
    public Foyer addFoyer(Foyer f) {
        return foyerRepository.save(f);
    }

    @Override
    public Foyer updateFoyer(Foyer f) {
        return foyerRepository.save(f);
    }

    @Override
    public Foyer retrieveFoyer(long idFoyer) {
        return foyerRepository.findById(idFoyer).orElse(null);
    }

    @Override
    public void removeFoyer(long idFoyer) {
        foyerRepository.deleteById(idFoyer);
    }

    @Override
    public Foyer ajouterFoyerEtAffecterAUniversite(Foyer foyer, long idUniversite) {
        Universite universite = universiteRepository.findById(idUniversite).orElse(null);
        if (universite != null) {
            // Save foyer with its blocs (cascade will handle blocs)
            Foyer savedFoyer = foyerRepository.save(foyer);

            // Set bidirectional relationship for blocs
            if (savedFoyer.getBlocs() != null) {
                for (Bloc bloc : savedFoyer.getBlocs()) {
                    bloc.setFoyer(savedFoyer);
                }
            }

            // Affect foyer to universite
            universite.setFoyer(savedFoyer);
            savedFoyer.setUniversite(universite);
            universiteRepository.save(universite);

            return savedFoyer;
        }
        return null;
    }
}