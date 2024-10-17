package beerrecipegenerator.federicodipresa.services;


import beerrecipegenerator.federicodipresa.entities.BeerRecipe;
import beerrecipegenerator.federicodipresa.repository.BeerRecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BeerRecipeService {

    @Autowired
    private BeerRecipeRepository beerRecipeRepository;

    public BeerRecipe createRecipe(BeerRecipe beerRecipe) {
        return beerRecipeRepository.save(beerRecipe);
    }

    public BeerRecipe getRecipeById(Long id) {
        return beerRecipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ricetta non trovata con ID: " + id));
    }
}

