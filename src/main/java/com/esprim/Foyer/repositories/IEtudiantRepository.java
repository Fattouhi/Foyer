package com.esprim.Foyer.repositories;

import com.esprim.Foyer.entities.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEtudiantRepository extends JpaRepository<Etudiant, Long> {
    Etudiant findByCin(long cin);
}