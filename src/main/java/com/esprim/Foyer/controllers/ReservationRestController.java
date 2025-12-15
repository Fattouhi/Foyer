package com.esprim.Foyer.controllers;

import com.esprim.Foyer.entities.Reservation;
import com.esprim.Foyer.services.IReservationService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/reservation")
public class ReservationRestController {

    private IReservationService reservationService;

    @GetMapping("/retrieve-all-reservations")
    public List<Reservation> retrieveAllReservation() {
        return reservationService.retrieveAllReservation();
    }

    @GetMapping("/retrieve-reservation/{id-reservation}")
    public Reservation retrieveReservation(@PathVariable("id-reservation") String idReservation) {
        return reservationService.retrieveReservation(idReservation);
    }

    @PutMapping("/update-reservation")
    public Reservation updateReservation(@RequestBody Reservation reservation) {
        return reservationService.updateReservation(reservation);
    }

    @PostMapping("/ajouter-reservation/{id-chambre}/{cin-etudiant}")
    public Reservation ajouterReservation(
            @PathVariable("id-chambre") Long idChambre,
            @PathVariable("cin-etudiant") Long cinEtudiant) {
        return reservationService.ajouterReservation(idChambre, cinEtudiant);
    }

    @PutMapping("/annuler-reservation/{cin-etudiant}")
    public Reservation annulerReservation(@PathVariable("cin-etudiant") Long cinEtudiant) {
        return reservationService.annulerReservation(cinEtudiant);
    }

    @GetMapping("/get-reservations-by-annee-and-universite/{nom-universite}")
    public List<Reservation> getReservationParAnneeUniversitaireEtNomUniversite(
            @RequestParam("annee") @DateTimeFormat(pattern = "yyyy-MM-dd") Date anneeUniversite,
            @PathVariable("nom-universite") String nomUniversite) {
        return reservationService.getReservationParAnneeUniversitaireEtNomUniversite(anneeUniversite, nomUniversite);
    }
}