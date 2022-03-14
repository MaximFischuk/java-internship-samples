package com.example.demo.repositories;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public record TestData(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) Integer id, String name, String surname) {
}
