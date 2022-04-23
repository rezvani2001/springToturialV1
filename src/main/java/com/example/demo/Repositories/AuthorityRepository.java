package com.example.demo.Repositories;

import com.example.demo.Models.security.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, UUID> {
}
