package com.example.demo.services;

import com.example.demo.Exceptions.GeneralException;
import com.example.demo.Models.Person;
import com.example.demo.Models.messages.PersonMessages;
import com.example.demo.Repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    private boolean isPersonPresentByNationalCode(String nationalCode) {
        return personRepository.findPersonByNationalKey(nationalCode).isEmpty();
    }


    public void insertPerson(Person person) throws GeneralException {
        if (isPersonPresentByNationalCode(person.getNationalKey())) {
            person.setId(UUID.randomUUID());
            personRepository.save(person);
        } else {
            throw new GeneralException(PersonMessages.DUPLICATED_NATIONAL_CODE);
        }
    }
}
