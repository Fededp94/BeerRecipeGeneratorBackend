package beerrecipegenerator.federicodipresa.dto;

import lombok.Data;
import java.util.List;

@Data
public class BeerRecipeDTO {
    private Long id;
    private String name;
    private List<String> malts;
    private List<String> hops;
    private List<String> yeasts;
    private double estimatedAlcohol;
    private String userEmail;
}
