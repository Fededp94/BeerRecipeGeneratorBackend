package beerrecipegenerator.federicodipresa.controllers;

import beerrecipegenerator.federicodipresa.dto.BeerRecipeDTO;
import beerrecipegenerator.federicodipresa.entities.*;
import beerrecipegenerator.federicodipresa.services.BeerRecipeService;
import beerrecipegenerator.federicodipresa.services.UserService;
import beerrecipegenerator.federicodipresa.repository.MaltRepository;
import beerrecipegenerator.federicodipresa.repository.HopRepository;
import beerrecipegenerator.federicodipresa.repository.YeastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recipes")
public class BeerRecipeController {

    private final BeerRecipeService beerRecipeService;
    private final UserService userService;
    private final MaltRepository maltRepository;
    private final HopRepository hopRepository;
    private final YeastRepository yeastRepository;

    @Autowired
    public BeerRecipeController(BeerRecipeService beerRecipeService,
                                UserService userService,
                                MaltRepository maltRepository,
                                HopRepository hopRepository,
                                YeastRepository yeastRepository) {
        this.beerRecipeService = beerRecipeService;
        this.userService = userService;
        this.maltRepository = maltRepository;
        this.hopRepository = hopRepository;
        this.yeastRepository = yeastRepository;
    }

    private BeerRecipeDTO convertToDTO(BeerRecipe recipe) {
        BeerRecipeDTO dto = new BeerRecipeDTO();
        dto.setId(recipe.getId());
        dto.setName(recipe.getName());
        dto.setEstimatedAlcohol(recipe.getEstimatedAlcohol());
        dto.setUserEmail(recipe.getUser().getEmail());

        dto.setMalts(recipe.getMalts().stream().map(malt -> malt.getId().toString()).collect(Collectors.toList()));
        dto.setHops(recipe.getHops().stream().map(hop -> hop.getId().toString()).collect(Collectors.toList()));
        dto.setYeasts(recipe.getYeasts().stream().map(yeast -> yeast.getId().toString()).collect(Collectors.toList()));

        return dto;
    }

    private List<Malt> getMaltsFromIds(List<String> maltIds) {
        return maltIds.stream()
                .map(id -> maltRepository.findById(Long.parseLong(id))
                        .orElseThrow(() -> new EntityNotFoundException("Malto non trovato con ID: " + id)))
                .collect(Collectors.toList());
    }

    private List<Hop> getHopsFromIds(List<String> hopIds) {
        return hopIds.stream()
                .map(id -> hopRepository.findById(Long.parseLong(id))
                        .orElseThrow(() -> new EntityNotFoundException("Luppolo non trovato con ID: " + id)))
                .collect(Collectors.toList());
    }

    private List<Yeast> getYeastsFromIds(List<String> yeastIds) {
        return yeastIds.stream()
                .map(id -> yeastRepository.findById(Long.parseLong(id))
                        .orElseThrow(() -> new EntityNotFoundException("Lievito non trovato con ID: " + id)))
                .collect(Collectors.toList());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> createRecipe(@RequestBody @Valid BeerRecipeDTO beerRecipeDTO) {
        try {
            User user = userService.findByEmail(beerRecipeDTO.getUserEmail());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Utente non trovato con email: " + beerRecipeDTO.getUserEmail());
            }

            BeerRecipe beerRecipe = new BeerRecipe();
            beerRecipe.setName(beerRecipeDTO.getName());
            beerRecipe.setEstimatedAlcohol(beerRecipeDTO.getEstimatedAlcohol());
            beerRecipe.setUser(user);
            beerRecipe.setMalts(getMaltsFromIds(beerRecipeDTO.getMalts()));
            beerRecipe.setHops(getHopsFromIds(beerRecipeDTO.getHops()));
            beerRecipe.setYeasts(getYeastsFromIds(beerRecipeDTO.getYeasts()));

            BeerRecipe savedRecipe = beerRecipeService.createRecipe(beerRecipe);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedRecipe));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("ID non valido fornito per malto, luppolo o lievito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore durante la creazione della ricetta: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecipe(@PathVariable Long id) {
        try {
            BeerRecipe recipe = beerRecipeService.getRecipeById(id);
            return ResponseEntity.ok(convertToDTO(recipe));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ricetta non trovata con ID: " + id);
        }
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<?> getAllRecipesByUser(@PathVariable String email) {
        try {
            User user = userService.findByEmail(email);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utente non trovato con email: " + email);
            }

            List<BeerRecipe> recipes = beerRecipeService.getAllRecipesByUser(user);
            if (recipes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nessuna ricetta trovata per l'utente");
            }

            List<BeerRecipeDTO> dtos = recipes.stream().map(this::convertToDTO).collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore durante il recupero delle ricette: " + e.getMessage());
        }
    }
}