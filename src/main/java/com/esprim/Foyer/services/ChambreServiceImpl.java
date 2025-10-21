package com.esprim.Foyer.services;

import com.esprim.Foyer.entities.Chambre;
import com.esprim.Foyer.repositories.ChambreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChambreServiceImpl implements IChambreService{
    @Autowired
    private ChambreRepository chambreRepository;

    @Override
    public List<Chambre> getAllChambre() {
        return  chambreRepository.findAll();
    }

    @Override
    public Chambre getChambreById(Long id) {
        return chambreRepository.findById(id).get();
    }

    @Override
    public void saveChambre(Chambre chambre) {

    }

    @Override
    public void deleteChambreById(Long id) {

    }

    @Override
    public void updateChambre(Chambre chambre) {

    }
}
