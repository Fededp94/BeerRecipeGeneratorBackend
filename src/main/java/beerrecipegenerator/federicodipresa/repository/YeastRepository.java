package beerrecipegenerator.federicodipresa.repository;

import beerrecipegenerator.federicodipresa.entities.Yeast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YeastRepository extends JpaRepository<Yeast, Long> {
}

