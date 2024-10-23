package beerrecipegenerator.federicodipresa.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import lombok.Data;
import java.util.List;

@Data
public class BeerRecipeDTO {
    private Long id;

    @NotNull(message = "Il nome della ricetta è obbligatorio")
    @NotEmpty(message = "Il nome della ricetta non può essere vuoto")
    private String name;

    @NotNull(message = "La lista dei malti è obbligatoria")
    @Size(min = 1, message = "Devi selezionare almeno un malto")
    private List<String> malts;

    @NotNull(message = "La lista dei luppoli è obbligatoria")
    @Size(min = 1, message = "Devi selezionare almeno un luppolo")
    private List<String> hops;

    @NotNull(message = "La lista dei lieviti è obbligatoria")
    @Size(min = 1, message = "Devi selezionare almeno un lievito")
    private List<String> yeasts;

    @NotNull(message = "Il grado alcolico stimato è obbligatorio")
    @Min(value = 0, message = "Il grado alcolico non può essere negativo")
    private double estimatedAlcohol;

    @NotNull(message = "L'email dell'utente è obbligatoria")
    @NotEmpty(message = "L'email dell'utente non può essere vuota")
    private String userEmail;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMalts() {
        return malts;
    }

    public void setMalts(List<String> malts) {
        this.malts = malts;
    }

    public List<String> getHops() {
        return hops;
    }

    public void setHops(List<String> hops) {
        this.hops = hops;
    }

    public List<String> getYeasts() {
        return yeasts;
    }

    public void setYeasts(List<String> yeasts) {
        this.yeasts = yeasts;
    }

    public double getEstimatedAlcohol() {
        return estimatedAlcohol;
    }

    public void setEstimatedAlcohol(double estimatedAlcohol) {
        this.estimatedAlcohol = estimatedAlcohol;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
