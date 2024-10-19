package beerrecipegenerator.federicodipresa.repository;

import beerrecipegenerator.federicodipresa.entities.BeerRecipe;
import beerrecipegenerator.federicodipresa.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BeerRecipeRepository extends JpaRepository<BeerRecipe, Long> {


    List<BeerRecipe> findByUser(User user);
}


