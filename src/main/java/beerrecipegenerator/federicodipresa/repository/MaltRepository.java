package beerrecipegenerator.federicodipresa.repository;

import beerrecipegenerator.federicodipresa.entities.Malt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaltRepository extends JpaRepository<Malt, Long> {
}

