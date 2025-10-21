package com.esprim.Foyer.controllers;

import com.esprim.Foyer.entities.Chambre;
import com.esprim.Foyer.services.IChambreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class ChambreRestController {
    IChambreService iChambreService;

    @GetMapping("/retrieve-all-chambres")
    public List<Chambre> retrieveAllChambres() {
        return iChambreService.getAllChambre();
    }

    @GetMapping("/retrieve-all-chambres/{chambre-id}")
    public Chambre retrieveChambreById(@PathVariable("chambre-id") Long chambreId) {
        return iChambreService.getChambreById(chambreId);
    }

    @PostMapping("/add-chambre")
    public Chambre addChambre(@RequestBody Chambre chambre) {
        return iChambreService.saveChambre(chambre);
    }

    @DeleteMapping("/remove-chambre/{chmabre-id}")
    public void removeChambre(@PathVariable("chmabre-id") Long chambreId) {
        iChambreService.deleteChambreById(chambreId);
    }

    @PutMapping("/modify-chambre")
    public Chambre modifyChambre(@RequestBody Chambre chambre) {
        return iChambreService.saveChambre(chambre);
    }
}
