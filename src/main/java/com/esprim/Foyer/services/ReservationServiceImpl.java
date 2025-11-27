package com.esprim.Foyer.services;

import com.esprim.Foyer.entities.*;
import com.esprim.Foyer.repositories.IChambreRepository;
import com.esprim.Foyer.repositories.IEtudiantRepository;
import com.esprim.Foyer.repositories.IReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private IReservationRepository reservationRepository;
    private IChambreRepository chambreRepository;
    private IEtudiantRepository etudiantRepository;

    @Override
    public List<Reservation> retrieveAllReservation() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation updateReservation(Reservation res) {
        return reservationRepository.save(res);
    }

    @Override
    public Reservation retrieveReservation(String idReservation) {
        return reservationRepository.findById(Long.parseLong(idReservation)).orElse(null);
    }

    @Override
    public Reservation ajouterReservation(long idChambre, long cinEtudiant) {
        Chambre chambre = chambreRepository.findById(idChambre).orElse(null);
        Etudiant etudiant = etudiantRepository.findByCin(cinEtudiant);

        if (chambre == null || etudiant == null) {
            return null;
        }

        // Check room capacity based on type
        int maxCapacity = getMaxCapacity(chambre.getTypeC());

        // Count current reservations for this chamber
        int currentReservations = 0;
        if (chambre.getReservation() != null && chambre.getReservation().getEtudiants() != null) {
            currentReservations = chambre.getReservation().getEtudiants().size();
        }

        if (currentReservations >= maxCapacity) {
            return null; // Room is full
        }

        // Create or get existing reservation
        Reservation reservation;
        if (chambre.getReservation() != null) {
            reservation = chambre.getReservation();
        } else {
            reservation = new Reservation();

            // Generate reservation ID: numChambre-nomBloc-anneeUniversitaire
            Calendar cal = Calendar.getInstance();
            int annee = cal.get(Calendar.YEAR);
            String idReservation = chambre.getNumeroChambre() + "-" +
                    chambre.getBloc().getNomBloc() + "-" +
                    annee;

            reservation.setIdReservation(idReservation);
            reservation.setAnneeUniversitaire(new Date());
            reservation.setEstValide(true);
            reservation.setEtudiants(new HashSet<>());
        }

        // Add student to reservation
        reservation.getEtudiants().add(etudiant);

        // Set bidirectional relationship
        if (etudiant.getReservations() == null) {
            etudiant.setReservations(new HashSet<>());
        }
        etudiant.getReservations().add(reservation);

        // Link reservation to chamber
        reservation.setChambre(chambre);
        chambre.setReservation(reservation);

        // Save
        Reservation savedReservation = reservationRepository.save(reservation);
        chambreRepository.save(chambre);
        etudiantRepository.save(etudiant);

        return savedReservation;
    }

    @Override
    public Reservation annulerReservation(long cinEtudiant) {
        Etudiant etudiant = etudiantRepository.findByCin(cinEtudiant);

        if (etudiant == null || etudiant.getReservations() == null || etudiant.getReservations().isEmpty()) {
            return null;
        }

        // Get the active reservation
        Reservation reservation = etudiant.getReservations().stream()
                .filter(Reservation::isEstValide)
                .findFirst()
                .orElse(null);

        if (reservation == null) {
            return null;
        }

        // Update reservation status
        reservation.setEstValide(false);

        // Remove student from reservation
        reservation.getEtudiants().remove(etudiant);
        etudiant.getReservations().remove(reservation);

        // Remove chamber association if no more students
        if (reservation.getEtudiants().isEmpty()) {
            Chambre chambre = reservation.getChambre();
            if (chambre != null) {
                chambre.setReservation(null);
                chambreRepository.save(chambre);
            }
            reservation.setChambre(null);
        }

        // Save changes
        etudiantRepository.save(etudiant);
        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getReservationParAnneeUniversitaireEtNomUniversite(Date anneeUniversite, String nomUniversite) {
        // Using JPQL query from repository
        return reservationRepository.findReservationsByAnneeAndUniversite(anneeUniversite, nomUniversite);
    }

    private int getMaxCapacity(TypeChambre type) {
        switch (type) {
            case SIMPLE:
                return 1;
            case DOUBLE:
                return 2;
            case TRIPLE:
                return 3;
            default:
                return 0;
        }
    }
}