package beerrecipegenerator.federicodipresa.services;

import beerrecipegenerator.federicodipresa.entities.User;
import beerrecipegenerator.federicodipresa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }


    public User saveUser(User user) {
        return userRepository.save(user);
    }
}

