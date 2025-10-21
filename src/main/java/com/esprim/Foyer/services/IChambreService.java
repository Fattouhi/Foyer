package com.esprim.Foyer.services;

import com.esprim.Foyer.entities.Chambre;

import java.util.List;

public interface IChambreService {
    public List<Chambre> getAllChambre();
    public Chambre getChambreById(Long id);
    public Chambre saveChambre(Chambre chambre);
    public void deleteChambreById(Long id);
    public void updateChambre(Chambre chambre);
}
