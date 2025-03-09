package com.example.authentication_api.controller;

import com.example.authentication_api.model.dto.CarPostDTO;
import com.example.authentication_api.model.dto.CarPutDTO;
import com.example.authentication_api.model.entity.Car;
import com.example.authentication_api.service.CarService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quando retorna todos carros."),
            @ApiResponse(responseCode = "401", description = "Quando nao esta autenticado.")
    })
    public ResponseEntity<List<Car>> findAll() {
        return ResponseEntity.ok(carService.findAll());
    }

    @GetMapping("/find")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quando encontra os carros pelo nome."),
            @ApiResponse(responseCode = "401", description = "Quando nao esta autenticado.")
    })
    public ResponseEntity<List<Car>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(carService.findByName(name));
    }

    @GetMapping("{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quando encontra o carro pelo  id."),
            @ApiResponse(responseCode = "400", description = "Quando nao encontra o carro pelo id."),
            @ApiResponse(responseCode = "401", description = "Quando nao esta autenticado.")
    })
    public ResponseEntity<Car> findById(@PathVariable int id) {
        return ResponseEntity.ok(carService.findById(id));
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Quando salva o carro."),
            @ApiResponse(responseCode = "400", description = "Quando a validacao de campos falha."),
            @ApiResponse(responseCode = "401", description = "Quando nao esta autenticado.")
    })
    public ResponseEntity<Car> save(@RequestBody @Valid CarPostDTO dto) {
        return new ResponseEntity<>(carService.save(dto), HttpStatus.CREATED);
    }

    @PutMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Quando atualiza o carro."),
            @ApiResponse(responseCode = "400", description = "Quando nao encontra o carro para atualizar ou a validacao de campos falha."),
            @ApiResponse(responseCode = "401", description = "Quando nao esta autenticado."),
            @ApiResponse(responseCode = "403", description = "Quando esta autenticado, mas nao como admin.")
    })
    public ResponseEntity<Void> update(@RequestBody @Valid CarPutDTO dto) {
        carService.update(dto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    @PutMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Quando remove o carro."),
            @ApiResponse(responseCode = "400", description = "Quando nao encontra o carro para remover."),
            @ApiResponse(responseCode = "401", description = "Quando nao esta autenticado."),
            @ApiResponse(responseCode = "403", description = "Quando esta autenticado, mas nao como admin.")
    })
    public ResponseEntity<Void> delete(@PathVariable int id) {
        carService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
