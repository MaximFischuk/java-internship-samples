package com.example.demo.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TestRepository extends CrudRepository <Object, Integer> {
    Optional<Object> findByNameAndSurname(String name, String surname);

//    @Query("select * from test where name = :name and surname = :surname")

    @Query("select * from test where custom_column = :str")
    Object custom(String str);
}
