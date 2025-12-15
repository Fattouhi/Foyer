package com.esprim.Foyer.controllers;

import com.esprim.Foyer.entities.Foyer;
import com.esprim.Foyer.services.IFoyerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/foyer")
public class FoyerRestController {

    private IFoyerService foyerService;

    @GetMapping("/retrieve-all-foyers")
    public List<Foyer> retrieveAllFoyers() {
        return foyerService.retrieveAllFoyers();
    }

    @GetMapping("/retrieve-foyer/{id-foyer}")
    public Foyer retrieveFoyer(@PathVariable("id-foyer") Long idFoyer) {
        return foyerService.retrieveFoyer(idFoyer);
    }

    @PostMapping("/add-foyer")
    public Foyer addFoyer(@RequestBody Foyer foyer) {
        return foyerService.addFoyer(foyer);
    }

    @PutMapping("/update-foyer")
    public Foyer updateFoyer(@RequestBody Foyer foyer) {
        return foyerService.updateFoyer(foyer);
    }

    @DeleteMapping("/remove-foyer/{id-foyer}")
    public void removeFoyer(@PathVariable("id-foyer") Long idFoyer) {
        foyerService.removeFoyer(idFoyer);
    }

    @PostMapping("/ajouter-foyer-et-affecter/{id-universite}")
    public Foyer ajouterFoyerEtAffecterAUniversite(
            @RequestBody Foyer foyer,
            @PathVariable("id-universite") Long idUniversite) {
        return foyerService.ajouterFoyerEtAffecterAUniversite(foyer, idUniversite);
    }
}