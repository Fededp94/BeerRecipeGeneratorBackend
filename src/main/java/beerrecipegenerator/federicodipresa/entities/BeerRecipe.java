package beerrecipegenerator.federicodipresa.entities;

import jakarta.persistence.*;

import java.util.List;

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

}


