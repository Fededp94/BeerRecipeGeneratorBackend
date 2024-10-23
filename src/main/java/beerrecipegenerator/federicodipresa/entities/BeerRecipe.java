package beerrecipegenerator.federicodipresa.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "beer_recipes")
public class BeerRecipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "beer_recipe_malt",
            joinColumns = @JoinColumn(name = "beer_recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "malt_id")
    )
    private List<Malt> malts;

    @ManyToMany
    @JoinTable(
            name = "beer_recipe_hop",
            joinColumns = @JoinColumn(name = "beer_recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "hop_id")
    )
    private List<Hop> hops;

    @ManyToMany
    @JoinTable(
            name = "beer_recipe_yeast",
            joinColumns = @JoinColumn(name = "beer_recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "yeast_id")
    )
    private List<Yeast> yeasts;

    private double estimatedAlcohol;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

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

    public List<Malt> getMalts() {
        return malts;
    }

    public void setMalts(List<Malt> malts) {
        this.malts = malts;
    }

    public List<Hop> getHops() {
        return hops;
    }

    public void setHops(List<Hop> hops) {
        this.hops = hops;
    }

    public List<Yeast> getYeasts() {
        return yeasts;
    }

    public void setYeasts(List<Yeast> yeasts) {
        this.yeasts = yeasts;
    }

    public double getEstimatedAlcohol() {
        return estimatedAlcohol;
    }

    public void setEstimatedAlcohol(double estimatedAlcohol) {
        this.estimatedAlcohol = estimatedAlcohol;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}


