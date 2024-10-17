package beerrecipegenerator.federicodipresa.controllers;

import beerrecipegenerator.federicodipresa.dto.BeerRecipeDTO;
import beerrecipegenerator.federicodipresa.entities.BeerRecipe;
import beerrecipegenerator.federicodipresa.services.BeerRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import javax.validation.Valid;

@RestController
@RequestMapping("/api/recipes")
public class BeerRecipeController {

    @Autowired
    private BeerRecipeService beerRecipeService;

    // Metodo per creare la ricetta
    @PostMapping
    public ResponseEntity<BeerRecipeDTO> createRecipe(@RequestBody @Valid BeerRecipeDTO beerRecipeDTO) {
        // Converte il BeerRecipeDTO in BeerRecipe
        BeerRecipe beerRecipe = new BeerRecipe();
        beerRecipe.setName(beerRecipeDTO.getName());
        beerRecipe.setMalts(beerRecipeDTO.getMalts());
        beerRecipe.setHops(beerRecipeDTO.getHops());
        beerRecipe.setYeasts(beerRecipeDTO.getYeasts());
        beerRecipe.setAlcoholContent(beerRecipeDTO.getAlcoholContent());

        // Salva la ricetta
        BeerRecipe savedRecipe = beerRecipeService.createRecipe(beerRecipe);

        // Converte il BeerRecipe in BeerRecipeDTO
        BeerRecipeDTO savedRecipeDTO = new BeerRecipeDTO();
        savedRecipeDTO.setId(savedRecipe.getId());
        savedRecipeDTO.setName(savedRecipe.getName());
        savedRecipeDTO.setMalts(savedRecipe.getMalts());
        savedRecipeDTO.setHops(savedRecipe.getHops());
        savedRecipeDTO.setYeasts(savedRecipe.getYeasts());
        savedRecipeDTO.setAlcoholContent(savedRecipe.getAlcoholContent());

        // Ritorna la risposta con lo status 201 (creato)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRecipeDTO);
    }

    // Metodo per ottenere una ricetta tramite ID
    @GetMapping("/{id}")
    public ResponseEntity<BeerRecipeDTO> getRecipe(@PathVariable Long id) {
        // Ottiene la ricetta dal servizio
        BeerRecipe beerRecipe = beerRecipeService.getRecipeById(id);

        // Converte il BeerRecipe in BeerRecipeDTO
        BeerRecipeDTO beerRecipeDTO = new BeerRecipeDTO();
        beerRecipeDTO.setId(beerRecipe.getId());
        beerRecipeDTO.setName(beerRecipe.getName());
        beerRecipeDTO.setMalts(beerRecipe.getMalts());
        beerRecipeDTO.setHops(beerRecipe.getHops());
        beerRecipeDTO.setYeasts(beerRecipe.getYeasts());
        beerRecipeDTO.setAlcoholContent(beerRecipe.getAlcoholContent());

        // Ritorna la ricetta DTO
        return ResponseEntity.ok(beerRecipeDTO);
    }
}




    // Endpoint


