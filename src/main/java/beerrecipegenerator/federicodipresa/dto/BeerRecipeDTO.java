package beerrecipegenerator.federicodipresa.dto;

import java.util.List;

public class BeerRecipeDTO {
    private String name;
    private List<String> malts;
    private List<String> hops;
    private List<String> yeasts;
    private double estimatedAlcohol;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private List<String> beerRecipes;

    // Getters e Setters

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

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<String> getBeerRecipes() {
        return beerRecipes;
    }

    public void setBeerRecipes(List<String> beerRecipes) {
        this.beerRecipes = beerRecipes;
    }
}

