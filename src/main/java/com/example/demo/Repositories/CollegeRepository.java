package com.example.demo.Repositories;

import com.example.demo.Models.College;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CollegeRepository extends CrudRepository<College, UUID> {
    /**
     * methode to check if is there any college with given name to avoid entering duplicated names in DB
     *
     * @param name the name to check
     * @return the college with the given name
     */
    public Optional<College> findCollegeByName(String name);
}
