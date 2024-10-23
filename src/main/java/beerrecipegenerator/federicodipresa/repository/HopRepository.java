package beerrecipegenerator.federicodipresa.repository;

import beerrecipegenerator.federicodipresa.entities.Hop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface HopRepository extends JpaRepository<Hop, Long> {
    Optional<Hop> findByName(String name);
}

