package com.example.authentication_api.controller;


import com.example.authentication_api.model.entity.Car;
import com.example.authentication_api.service.CarService;
import com.example.authentication_api.util.CarCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
class CarControllerTest {
    @InjectMocks
    private CarController carController;
    @Mock
    private CarService carService;

    @BeforeEach
    void setUp() {
        BDDMockito.when(carService.findAll()).thenReturn(List.of(CarCreator.createValidCar()));
        BDDMockito.when(carService.findByName(ArgumentMatchers.anyString())).thenReturn(List.of(CarCreator.createValidCar()));
        BDDMockito.when(carService.findById(ArgumentMatchers.anyInt())).thenReturn(CarCreator.createValidCar());
        BDDMockito.when(carService.save(ArgumentMatchers.any())).thenReturn(CarCreator.createValidCar());
        BDDMockito.doNothing().when(carService).update(CarCreator.createValidCarPutDTO());
        BDDMockito.doNothing().when(carService).delete(ArgumentMatchers.anyInt());
    }

    @Test
    void findAll_Return200_WhenSuccessful() {
        ResponseEntity<List<Car>> response = carController.findAll();

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        Assertions.assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void findByName_Return200_WhenSuccessful() {
        ResponseEntity<List<Car>> response = carController.findByName("test");

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        Assertions.assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void findById_Return200_WhenSuccessful() {
        ResponseEntity<Car> response = carController.findById(1);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        Assertions.assertThat(response.getBody()).isNotNull();
    }

    @Test
    void save_Return201_WhenSuccessful() {
        ResponseEntity<Car> response = carController.save(CarCreator.createValidCarPostDTO());

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        Assertions.assertThat(response.getBody()).isEqualTo(CarCreator.createValidCar());
    }

    @Test
    void update_Return204_WhenSuccessful() {
        ResponseEntity<Void> response = carController.update(CarCreator.createValidCarPutDTO());

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
    }

    @Test
    void delete_Return204_WhenSuccessful() {
        ResponseEntity<Void> response = carController.delete(1);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
    }


}