package com.esprim.Foyer.controllers;

import com.esprim.Foyer.entities.Chambre;
import com.esprim.Foyer.entities.TypeChambre;
import com.esprim.Foyer.services.IChambreService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/chambre")
public class ChambreRestController {

    private IChambreService chambreService;

    @GetMapping("/retrieve-all-chambres")
    public List<Chambre> retrieveAllChambres() {
        return chambreService.retrieveAllChambres();
    }

    @GetMapping("/retrieve-chambre/{chambre-id}")
    public Chambre retrieveChambre(@PathVariable("chambre-id") Long idChambre) {
        return chambreService.retrieveChambre(idChambre);
    }

    @PostMapping("/add-chambre")
    public Chambre addChambre(@RequestBody Chambre chambre) {
        return chambreService.addChambre(chambre);
    }

    @PutMapping("/update-chambre")
    public Chambre updateChambre(@RequestBody Chambre chambre) {
        return chambreService.updateChambre(chambre);
    }

    @GetMapping("/get-chambres-by-universite/{nom-universite}")
    public List<Chambre> getChambresParNomUniversite(@PathVariable("nom-universite") String nomUniversite) {
        return chambreService.getChambresParNomUniversite(nomUniversite);
    }

    @GetMapping("/get-chambres-by-bloc-and-type/{id-bloc}/{type}")
    public List<Chambre> getChambresParBlocEtType(
            @PathVariable("id-bloc") Long idBloc,
            @PathVariable("type") TypeChambre typeC) {
        return chambreService.getChambresParBlocEtType(idBloc, typeC);
    }

    @GetMapping("/get-chambres-non-reserve/{nom-universite}/{type}")
    public List<Chambre> getChambresNonReserveParNomUniversiteEtTypeChambre(
            @PathVariable("nom-universite") String nomUniversite,
            @PathVariable("type") TypeChambre type) {
        return chambreService.getChambresNonReserveParNomUniversiteEtTypeChambre(nomUniversite, type);
    }
}