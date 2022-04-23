package com.example.demo.services;

import com.example.demo.Exceptions.GeneralException;
import com.example.demo.Models.User;
import com.example.demo.Models.messages.UserMessages;
import com.example.demo.Models.security.Authority;
import com.example.demo.Models.security.Token;
import com.example.demo.Repositories.AuthorityRepository;
import com.example.demo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository, AuthorityRepository authorityRepository) {
        this.repository = repository;
//
//        Authority authority = new Authority();
//        authority.setId(UUID.randomUUID());
//        authority.setAuthority("ROLE_USER");
//        authority.setUsername("amir");
//        authorityRepository.save(authority);
    }

    public void makeUser(User user) throws GeneralException {
        if (!isPresentByUsername(user.getUsername())) {
            repository.save(user);
        } else {
            throw new GeneralException(UserMessages.DUPLICATED_USERNAME);
        }
    }

    public boolean isPresentByUsername(String username) {
        return repository.findUserByUsername(username).isPresent();
    }

    public User getUserByUsername(String username) throws GeneralException {
        Optional<User> user = repository.findUserByUsername(username);
        if (user.isPresent()) return user.get();
        else throw new GeneralException(UserMessages.NOT_FOUND);
    }

//    public User getUserByToken(String token) throws GeneralException {
//        Optional<User> user = repository.findUserByToken(token);
//
//        if (user.isPresent()) return user.get();
//        else throw new GeneralException(UserMessages.NOT_AUTHENTICATED);
//    }
}
