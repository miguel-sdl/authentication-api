package com.example.authentication_api.controller;

import com.example.authentication_api.model.dto.CarPostDTO;
import com.example.authentication_api.model.dto.CarPutDTO;
import com.example.authentication_api.model.entity.Car;
import com.example.authentication_api.service.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<List<Car>> findAll() {
        return ResponseEntity.ok(carService.findAll());
    }

    @PostMapping
    public ResponseEntity<Car> save(@RequestBody @Valid CarPostDTO dto) {
        return new ResponseEntity<>(carService.save(dto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid CarPutDTO dto) {
        carService.update(dto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        carService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
