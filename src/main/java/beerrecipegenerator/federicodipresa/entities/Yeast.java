package beerrecipegenerator.federicodipresa.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "yeasts")
public class Yeast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;
}
