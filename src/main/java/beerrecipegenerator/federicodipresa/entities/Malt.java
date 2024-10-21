package beerrecipegenerator.federicodipresa.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "malts")
public class Malt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;
}



