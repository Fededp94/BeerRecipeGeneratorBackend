package beerrecipegenerator.federicodipresa.services;

import beerrecipegenerator.federicodipresa.entities.BeerRecipe;
import beerrecipegenerator.federicodipresa.entities.User;
import beerrecipegenerator.federicodipresa.repository.BeerRecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeerRecipeService {

    @Autowired
    private BeerRecipeRepository beerRecipeRepository;

    @Autowired
    private UserService userService;

    // Aggiungi il metodo per recuperare tutte le ricette di un utente
    public List<BeerRecipe> getAllRecipesByUser(User user) {
        return beerRecipeRepository.findByUser(user);
    }

    // Metodo per ottenere una ricetta tramite ID
    public BeerRecipe getRecipeById(Long id) {
        return beerRecipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ricetta non trovata con ID: " + id));
    }

    // Aggiungi il metodo per creare una nuova ricetta
    public BeerRecipe createRecipe(BeerRecipe beerRecipe) {
        // Salva la ricetta nel database e restituisci l'oggetto salvato
        return beerRecipeRepository.save(beerRecipe);
    }
}


