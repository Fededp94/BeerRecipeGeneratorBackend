package beerrecipegenerator.federicodipresa.controllers;

import beerrecipegenerator.federicodipresa.JWT.JwtUtil;
import beerrecipegenerator.federicodipresa.dto.BeerRecipeDTO;
import beerrecipegenerator.federicodipresa.entities.*;
import beerrecipegenerator.federicodipresa.services.BeerRecipeService;
import beerrecipegenerator.federicodipresa.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recipes")
public class BeerRecipeController {

    @Autowired
    private BeerRecipeService beerRecipeService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<BeerRecipeDTO> createRecipe(
            @Valid @RequestBody BeerRecipeDTO recipeDTO,
            @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            recipeDTO.setUserEmail(jwtUtil.extractUsername(token));

            BeerRecipe savedRecipe = beerRecipeService.createRecipe(recipeDTO);
            return new ResponseEntity<>(convertToDTO(savedRecipe), HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Errore creazione ricetta: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<BeerRecipeDTO>> getAllRecipes(
            @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String userEmail = jwtUtil.extractUsername(token);

            User user = userService.findByEmail(userEmail);
            if (user == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato");
            }

            List<BeerRecipe> recipes = beerRecipeService.getAllRecipesByUser(user);
            List<BeerRecipeDTO> recipeDTOs = recipes.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(recipeDTOs);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Errore ricetta");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeerRecipeDTO> getRecipeById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String userEmail = jwtUtil.extractUsername(token);

            BeerRecipe recipe = beerRecipeService.getRecipeById(id);

            if (!recipe.getUser().getEmail().equals(userEmail)) {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "Non hai l'accesso a questa ricetta");
            }

            return ResponseEntity.ok(convertToDTO(recipe));
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Ricetta non trovata con questo id: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);
            String userEmail = jwtUtil.extractUsername(token);

            BeerRecipe recipe = beerRecipeService.getRecipeById(id);
            if (!recipe.getUser().getEmail().equals(userEmail)) {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "Non puoi cancellare questa ricetta");
            }

            beerRecipeService.deleteRecipe(id);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Errore cancellazione ricetta");
        }
    }

    private BeerRecipeDTO convertToDTO(BeerRecipe recipe) {
        BeerRecipeDTO dto = new BeerRecipeDTO();
        dto.setId(recipe.getId());
        dto.setName(recipe.getName());
        dto.setEstimatedAlcohol(recipe.getEstimatedAlcohol());
        dto.setUserEmail(recipe.getUser().getEmail());

        dto.setMalts(recipe.getMalts().stream()
                .map(Malt::getName)
                .collect(Collectors.toList()));

        dto.setHops(recipe.getHops().stream()
                .map(Hop::getName)
                .collect(Collectors.toList()));

        dto.setYeasts(recipe.getYeasts().stream()
                .map(Yeast::getName)
                .collect(Collectors.toList()));

        return dto;
    }
}
