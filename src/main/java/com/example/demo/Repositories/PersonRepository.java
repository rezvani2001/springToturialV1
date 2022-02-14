package com.example.demo.Repositories;

import com.example.demo.Models.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends CrudRepository<Person, UUID> {
    public Optional<Person> findPersonByNationalKey(String nationalKey);
}
