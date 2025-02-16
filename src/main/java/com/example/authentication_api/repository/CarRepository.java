package com.example.authentication_api.repository;

import com.example.authentication_api.model.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {
    List<Car> findByName(String name);
}
