package beerrecipegenerator.federicodipresa.services;

import beerrecipegenerator.federicodipresa.dto.BeerRecipeDTO;
import beerrecipegenerator.federicodipresa.entities.*;
import beerrecipegenerator.federicodipresa.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class BeerRecipeService {

    @Autowired
    private BeerRecipeRepository beerRecipeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MaltRepository maltRepository;

    @Autowired
    private HopRepository hopRepository;

    @Autowired
    private YeastRepository yeastRepository;

    public BeerRecipe createRecipe(BeerRecipeDTO recipeDTO) {
        User user = userService.findByEmail(recipeDTO.getUserEmail());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }


        BeerRecipe recipe = new BeerRecipe();
        recipe.setName(recipeDTO.getName());
        recipe.setEstimatedAlcohol(recipeDTO.getEstimatedAlcohol());
        recipe.setUser(user);

        // Gestione malti
        List<Malt> selectedMalts = new ArrayList<>();
        for (String maltName : recipeDTO.getMalts()) {
            switch (maltName) {
                // Malti Chiari
                case "Malto Wheat":
                    selectedMalts.add(findOrCreateMalt("Malto Wheat", "Dona corpo leggero e schiuma persistente"));
                    break;
                case "Pilsner":
                    selectedMalts.add(findOrCreateMalt("Pilsner", "Dona un colore chiaro e gusto pulito"));
                    break;
                case "Pale Ale":
                    selectedMalts.add(findOrCreateMalt("Pale Ale", "Dona un gusto maltato e bilanciato"));
                    break;
                // Malti Ambrati
                case "Vienna":
                    selectedMalts.add(findOrCreateMalt("Vienna", "Aggiunge dolcezza e note tostate"));
                    break;
                case "Monaco":
                    selectedMalts.add(findOrCreateMalt("Monaco", "Dona colore ambrato e sapore di pane tostato"));
                    break;
                case "Caramel":
                    selectedMalts.add(findOrCreateMalt("Caramel", "Dona un sapore caramellato e corposo"));
                    break;
                // Malti Scuri
                case "Abbey":
                    selectedMalts.add(findOrCreateMalt("Abbey", "Dona sapori complessi di frutta secca"));
                    break;
                case "Carafa III":
                    selectedMalts.add(findOrCreateMalt("Carafa III", "Aggiunge note di caffè e cioccolato"));
                    break;
                case "Chocolate":
                    selectedMalts.add(findOrCreateMalt("Chocolate", "Conferisce sapori intensi di cacao e caffè"));
                    break;
            }
        }
        recipe.setMalts(selectedMalts);

        // Gestione luppoli
        List<Hop> selectedHops = new ArrayList<>();
        for (String hopName : recipeDTO.getHops()) {
            switch (hopName) {
                // Luppoli Base
                case "Tettnang":
                    selectedHops.add(findOrCreateHop("Tettnang", "Luppolo nobile tedesco con note floreali e speziate"));
                    break;
                case "Fuggle":
                    selectedHops.add(findOrCreateHop("Fuggle", "Classico luppolo inglese con note erbacee e terrose"));
                    break;
                case "Hallertau Magnum":
                    selectedHops.add(findOrCreateHop("Hallertau Magnum", "Offre un amaro pulito e secco"));
                    break;
                // Luppoli Agrumati
                case "Citra":
                    selectedHops.add(findOrCreateHop("Citra", "E' famoso per il suo aroma di agrumi e frutti tropicali"));
                    break;
                case "Hbc-630":
                    selectedHops.add(findOrCreateHop("Hbc-630", "Ha un profilo aromatico di agrumi e frutta"));
                    break;
                case "Wakatu":
                    selectedHops.add(findOrCreateHop("Wakatu", "Offre note di lime e spezie"));
                    break;
                // Luppoli Fruttati
                case "Wai-ti":
                    selectedHops.add(findOrCreateHop("Wai-ti", "Ha note di frutta tropicale e fiori"));
                    break;
                case "Mosaic":
                    selectedHops.add(findOrCreateHop("Mosaic", "E' noto per il suo profilo complesso di frutta e terra"));
                    break;
                case "Vic Secret":
                    selectedHops.add(findOrCreateHop("Vic Secret", "Offre sapori di frutta tropicale e pino"));
                    break;
            }
        }
        recipe.setHops(selectedHops);

        // Gestione lieviti
        List<Yeast> selectedYeasts = new ArrayList<>();
        for (String yeastName : recipeDTO.getYeasts()) {
            switch (yeastName) {
                case "US-05":
                    selectedYeasts.add(findOrCreateYeast("US-05",
                            "Lievito Ale americano che produce birre chiare e pulite con note fruttate"));
                    break;
                case "Saflager W34/70":
                    selectedYeasts.add(findOrCreateYeast("Saflager W34/70",
                            "Lievito Lager versatile che offre un profilo pulito e ben bilanciato"));
                    break;
            }
        }
        recipe.setYeasts(selectedYeasts);

        return beerRecipeRepository.save(recipe);
    }

    private Malt findOrCreateMalt(String name, String description) {
        return maltRepository.findAll().stream()
                .filter(m -> m.getName().equals(name))
                .findFirst()
                .orElseGet(() -> {
                    Malt newMalt = new Malt();
                    newMalt.setName(name);
                    newMalt.setDescription(description);
                    return maltRepository.save(newMalt);
                });
    }

    private Hop findOrCreateHop(String name, String description) {
        return hopRepository.findAll().stream()
                .filter(h -> h.getName().equals(name))
                .findFirst()
                .orElseGet(() -> {
                    Hop newHop = new Hop();
                    newHop.setName(name);
                    newHop.setDescription(description);
                    return hopRepository.save(newHop);
                });
    }

    private Yeast findOrCreateYeast(String name, String description) {
        return yeastRepository.findAll().stream()
                .filter(y -> y.getName().equals(name))
                .findFirst()
                .orElseGet(() -> {
                    Yeast newYeast = new Yeast();
                    newYeast.setName(name);
                    newYeast.setDescription(description);
                    return yeastRepository.save(newYeast);
                });
    }

    public List<BeerRecipe> getAllRecipesByUser(User user) {
        return beerRecipeRepository.findByUser(user);
    }

    public BeerRecipe getRecipeById(Long id) {
        return beerRecipeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found with id: " + id));
    }

    public void deleteRecipe(Long id) {
        BeerRecipe recipe = getRecipeById(id);
        beerRecipeRepository.delete(recipe);
    }
}