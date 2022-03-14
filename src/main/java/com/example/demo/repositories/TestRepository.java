package com.example.demo.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TestRepository extends CrudRepository <TestData, Integer> {
    Optional<TestData> findByNameAndSurname(String name, String surname);

}
