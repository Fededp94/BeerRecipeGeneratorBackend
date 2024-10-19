package beerrecipegenerator.federicodipresa.repository;

import beerrecipegenerator.federicodipresa.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Metodo per trovare un utente per email
    User findByEmail(String email);


}

