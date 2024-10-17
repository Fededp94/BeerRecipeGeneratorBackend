package beerrecipegenerator.federicodipresa.dto;

import java.util.List;

public class BeerRecipeDTO {
    private String name;
    private List<String> malts;  // Lista di ID per i malti selezionati
    private List<String> hops;
    private List<String> yeasts;
    private double estimatedAlcohol;

}

