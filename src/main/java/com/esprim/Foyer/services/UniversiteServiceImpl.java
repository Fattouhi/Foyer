package com.esprim.Foyer.services;

import com.esprim.Foyer.entities.Foyer;
import com.esprim.Foyer.entities.Universite;
import com.esprim.Foyer.repositories.IFoyerRepository;
import com.esprim.Foyer.repositories.IUniversiteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UniversiteServiceImpl implements IUniversiteService {

    private IUniversiteRepository universiteRepository;
    private IFoyerRepository foyerRepository;

    @Override
    public List<Universite> retrieveAllUniversities() {
        return universiteRepository.findAll();
    }

    @Override
    public Universite addUniversite(Universite u) {
        return universiteRepository.save(u);
    }

    @Override
    public Universite updateUniversite(Universite u) {
        return universiteRepository.save(u);
    }

    @Override
    public Universite retrieveUniversite(long idUniversite) {
        return universiteRepository.findById(idUniversite).orElse(null);
    }

    @Override
    public Universite affecterFoyerAUniversite(long idFoyer, String nomUniversite) {
        Universite universite = universiteRepository.findByNomUniversite(nomUniversite);
        Foyer foyer = foyerRepository.findById(idFoyer).orElse(null);

        if (universite != null && foyer != null) {
            universite.setFoyer(foyer);
            foyer.setUniversite(universite);
            return universiteRepository.save(universite);
        }
        return null;
    }

    @Override
    public Universite desaffecterFoyerAUniversite(long idUniversite) {
        Universite universite = universiteRepository.findById(idUniversite).orElse(null);
        if (universite != null && universite.getFoyer() != null) {
            Foyer foyer = universite.getFoyer();
            universite.setFoyer(null);
            foyer.setUniversite(null);
            foyerRepository.save(foyer);
            return universiteRepository.save(universite);
        }
        return null;
    }
}
