package beerrecipegenerator.federicodipresa.dto;

import lombok.Data;
import java.util.List;
import java.util.ArrayList;

@Data
public class UserDTO {
    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private List<Long> beerRecipeIds;

    // Costruttore vuoto
    public UserDTO() {
        this.beerRecipeIds = new ArrayList<>();
    }

    // Costruttore con campi
    public UserDTO(Long id, String nome, String cognome, String email) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.beerRecipeIds = new ArrayList<>();
    }

    // Metodo per aggiungere un ID di ricetta
    public void addBeerRecipeId(Long recipeId) {
        if (this.beerRecipeIds == null) {
            this.beerRecipeIds = new ArrayList<>();
        }
        this.beerRecipeIds.add(recipeId);
    }
}