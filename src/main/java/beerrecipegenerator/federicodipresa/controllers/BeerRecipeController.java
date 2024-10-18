package beerrecipegenerator.federicodipresa.controllers;

import beerrecipegenerator.federicodipresa.dto.BeerRecipeDTO;
import beerrecipegenerator.federicodipresa.entities.BeerRecipe;
import beerrecipegenerator.federicodipresa.entities.Hop;
import beerrecipegenerator.federicodipresa.entities.Malt;
import beerrecipegenerator.federicodipresa.entities.Yeast;
import beerrecipegenerator.federicodipresa.services.BeerRecipeService;
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

    @PostMapping
    public ResponseEntity<BeerRecipeDTO> createRecipe(@RequestBody BeerRecipeDTO beerRecipeDTO) {
        BeerRecipe beerRecipe = new BeerRecipe();
        beerRecipe.setName(beerRecipeDTO.getName());

        List<Malt> malts = new ArrayList<>();
        for (String maltId : beerRecipeDTO.getMalts()) {
            Malt malt = new Malt();
            malt.setId(Long.parseLong(maltId));
            malts.add(malt);
        }
        beerRecipe.setMalts(malts);

        List<Hop> hops = new ArrayList<>();
        for (String hopId : beerRecipeDTO.getHops()) {
            Hop hop = new Hop();
            hop.setId(Long.parseLong(hopId));
            hops.add(hop);
        }
        beerRecipe.setHops(hops);

        List<Yeast> yeasts = new ArrayList<>();
        for (String yeastId : beerRecipeDTO.getYeasts()) {
            Yeast yeast = new Yeast();
            yeast.setId(Long.parseLong(yeastId));
            yeasts.add(yeast);
        }
        beerRecipe.setYeasts(yeasts);

        beerRecipe.setEstimatedAlcohol(beerRecipeDTO.getEstimatedAlcohol());

        BeerRecipe savedRecipe = beerRecipeService.createRecipe(beerRecipe);

        BeerRecipeDTO responseDTO = new BeerRecipeDTO();
        responseDTO.setName(savedRecipe.getName());
        responseDTO.setMalts(beerRecipeDTO.getMalts());
        responseDTO.setHops(beerRecipeDTO.getHops());
        responseDTO.setYeasts(beerRecipeDTO.getYeasts());
        responseDTO.setEstimatedAlcohol(savedRecipe.getEstimatedAlcohol());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeerRecipeDTO> getRecipe(@PathVariable Long id) {
        BeerRecipe beerRecipe = beerRecipeService.getRecipeById(id);

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
}





// Endpoint


