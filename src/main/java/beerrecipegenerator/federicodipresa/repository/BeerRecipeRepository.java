package beerrecipegenerator.federicodipresa.repository;

import beerrecipegenerator.federicodipresa.entities.BeerRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeerRecipeRepository extends JpaRepository<BeerRecipe, Long> {
}

