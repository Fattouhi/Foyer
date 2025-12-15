package com.esprim.Foyer.controllers;

import com.esprim.Foyer.entities.Universite;
import com.esprim.Foyer.services.IUniversiteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/universite")
public class UniversiteRestController {

    private IUniversiteService universiteService;

    @GetMapping("/retrieve-all-universites")
    public List<Universite> retrieveAllUniversities() {
        return universiteService.retrieveAllUniversities();
    }

    @GetMapping("/retrieve-universite/{id-universite}")
    public Universite retrieveUniversite(@PathVariable("id-universite") Long idUniversite) {
        return universiteService.retrieveUniversite(idUniversite);
    }

    @PostMapping("/add-universite")
    public Universite addUniversite(@RequestBody Universite universite) {
        return universiteService.addUniversite(universite);
    }

    @PutMapping("/update-universite")
    public Universite updateUniversite(@RequestBody Universite universite) {
        return universiteService.updateUniversite(universite);
    }

    @PutMapping("/affecter-foyer/{id-foyer}/{nom-universite}")
    public Universite affecterFoyerAUniversite(
            @PathVariable("id-foyer") Long idFoyer,
            @PathVariable("nom-universite") String nomUniversite) {
        return universiteService.affecterFoyerAUniversite(idFoyer, nomUniversite);
    }

    @PutMapping("/desaffecter-foyer/{id-universite}")
    public Universite desaffecterFoyerAUniversite(@PathVariable("id-universite") Long idUniversite) {
        return universiteService.desaffecterFoyerAUniversite(idUniversite);
    }
}