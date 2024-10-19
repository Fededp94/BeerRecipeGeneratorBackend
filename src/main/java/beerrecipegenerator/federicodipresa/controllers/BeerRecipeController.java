package beerrecipegenerator.federicodipresa.controllers;

import beerrecipegenerator.federicodipresa.dto.BeerRecipeDTO;
import beerrecipegenerator.federicodipresa.entities.BeerRecipe;
import beerrecipegenerator.federicodipresa.entities.Hop;
import beerrecipegenerator.federicodipresa.entities.Malt;
import beerrecipegenerator.federicodipresa.entities.Yeast;
import beerrecipegenerator.federicodipresa.entities.User;
import beerrecipegenerator.federicodipresa.services.BeerRecipeService;
import beerrecipegenerator.federicodipresa.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class BeerRecipeController {

    @Autowired
    private BeerRecipeService beerRecipeService;

    @Autowired
    private UserService userService;

    // Crea una ricetta e la associa a un utente
    @PostMapping
    public ResponseEntity<BeerRecipeDTO> createRecipe(@RequestBody BeerRecipeDTO beerRecipeDTO, @RequestParam String email) {
        // Trova l'utente tramite email
        User user = userService.findByEmail(email);


        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Crea la ricetta
        BeerRecipe beerRecipe = new BeerRecipe();
        beerRecipe.setName(beerRecipeDTO.getName());

        // Associa gli ingredienti (malti, luppoli, lieviti)
        List<Malt> malts = new ArrayList<>();
        for (String maltId : beerRecipeDTO.getMalts()) {
            Malt malt = new Malt();
            try {
                malt.setId(Long.parseLong(maltId)); // Assicurati che l'ID sia valido
                malts.add(malt);
            } catch (NumberFormatException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Se l'ID non è valido
            }
        }
        beerRecipe.setMalts(malts);

        List<Hop> hops = new ArrayList<>();
        for (String hopId : beerRecipeDTO.getHops()) {
            Hop hop = new Hop();
            try {
                hop.setId(Long.parseLong(hopId)); // Assicurati che l'ID sia valido
                hops.add(hop);
            } catch (NumberFormatException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Se l'ID non è valido
            }
        }
        beerRecipe.setHops(hops);

        List<Yeast> yeasts = new ArrayList<>();
        for (String yeastId : beerRecipeDTO.getYeasts()) {
            Yeast yeast = new Yeast();
            try {
                yeast.setId(Long.parseLong(yeastId)); // Assicurati che l'ID sia valido
                yeasts.add(yeast);
            } catch (NumberFormatException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Se l'ID non è valido
            }
        }
        beerRecipe.setYeasts(yeasts);

        beerRecipe.setEstimatedAlcohol(beerRecipeDTO.getEstimatedAlcohol());

        // Associa la ricetta all'utente
        beerRecipe.setUser(user);

        // Salva la ricetta
        BeerRecipe savedRecipe = beerRecipeService.createRecipe(beerRecipe);

        // Mappa la ricetta salvata nel DTO di risposta
        BeerRecipeDTO responseDTO = new BeerRecipeDTO();
        responseDTO.setName(savedRecipe.getName());
        responseDTO.setMalts(beerRecipeDTO.getMalts());
        responseDTO.setHops(beerRecipeDTO.getHops());
        responseDTO.setYeasts(beerRecipeDTO.getYeasts());
        responseDTO.setEstimatedAlcohol(savedRecipe.getEstimatedAlcohol());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    // Ottieni una ricetta per ID
    @GetMapping("/{id}")
    public ResponseEntity<BeerRecipeDTO> getRecipe(@PathVariable Long id) {
        BeerRecipe beerRecipe = beerRecipeService.getRecipeById(id);

        if (beerRecipe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        BeerRecipeDTO beerRecipeDTO = new BeerRecipeDTO();
        beerRecipeDTO.setName(beerRecipe.getName());

        List<String> maltIds = new ArrayList<>();
        for (Malt malt : beerRecipe.getMalts()) {
            maltIds.add(malt.getId().toString());
        }
        beerRecipeDTO.setMalts(maltIds);

        List<String> hopIds = new ArrayList<>();
        for (Hop hop : beerRecipe.getHops()) {
            hopIds.add(hop.getId().toString());
        }
        beerRecipeDTO.setHops(hopIds);

        List<String> yeastIds = new ArrayList<>();
        for (Yeast yeast : beerRecipe.getYeasts()) {
            yeastIds.add(yeast.getId().toString());
        }
        beerRecipeDTO.setYeasts(yeastIds);

        beerRecipeDTO.setEstimatedAlcohol(beerRecipe.getEstimatedAlcohol());

        return ResponseEntity.ok(beerRecipeDTO);
    }

    // Ottieni tutte le ricette di un utente
    @GetMapping("/user/{email}")
    public ResponseEntity<List<BeerRecipeDTO>> getAllRecipesByUser(@PathVariable String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<BeerRecipe> beerRecipes = beerRecipeService.getAllRecipesByUser(user);
        if (beerRecipes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);  // Se non ci sono ricette
        }

        List<BeerRecipeDTO> beerRecipeDTOs = new ArrayList<>();
        for (BeerRecipe beerRecipe : beerRecipes) {
            BeerRecipeDTO beerRecipeDTO = new BeerRecipeDTO();
            beerRecipeDTO.setName(beerRecipe.getName());

            List<String> maltIds = new ArrayList<>();
            for (Malt malt : beerRecipe.getMalts()) {
                maltIds.add(malt.getId().toString());
            }
            beerRecipeDTO.setMalts(maltIds);

            List<String> hopIds = new ArrayList<>();
            for (Hop hop : beerRecipe.getHops()) {
                hopIds.add(hop.getId().toString());
            }
            beerRecipeDTO.setHops(hopIds);

            List<String> yeastIds = new ArrayList<>();
            for (Yeast yeast : beerRecipe.getYeasts()) {
                yeastIds.add(yeast.getId().toString());
            }
            beerRecipeDTO.setYeasts(yeastIds);

            beerRecipeDTO.setEstimatedAlcohol(beerRecipe.getEstimatedAlcohol());
            beerRecipeDTOs.add(beerRecipeDTO);
        }

        return ResponseEntity.ok(beerRecipeDTOs);
    }
}
