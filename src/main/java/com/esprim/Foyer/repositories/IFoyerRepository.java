package com.esprim.Foyer.repositories;


import com.esprim.Foyer.entities.Foyer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFoyerRepository extends JpaRepository<Foyer, Long> {
}