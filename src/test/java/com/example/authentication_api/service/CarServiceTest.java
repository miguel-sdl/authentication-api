package com.example.authentication_api.service;

import com.example.authentication_api.exception.BadRequestException;
import com.example.authentication_api.model.entity.Car;
import com.example.authentication_api.repository.CarRepository;
import com.example.authentication_api.util.CarCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
class CarServiceTest {
    @InjectMocks
    private CarService carService;
    @Mock
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        BDDMockito.when(carRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(CarCreator.createValidCar()));
        BDDMockito.when(carRepository.save(ArgumentMatchers.any())).thenReturn(CarCreator.createValidCar());
        BDDMockito.doNothing().when(carRepository).deleteById(ArgumentMatchers.anyInt());
    }

    @Test
    void findById_ReturnsCar_WhenSuccessful() {
        Car car = carService.findById(1);

        Assertions.assertThat(car).isNotNull();
    }

    @Test
    void findById_ThrowsBadRequestException_WhenCarIsNotFound() {
        BDDMockito.when(carRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> carService.findById(1)).isInstanceOf(BadRequestException.class);
    }

    @Test
    void save_ReturnCar_WhenSuccessful() {
        Car saved = carService.save(CarCreator.createValidCarPostDTO());

        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved.getName()).isEqualTo(CarCreator.createValidCar().getName());
    }

    @Test
    void update_UpdatesCar_WhenSuccessful() {
        Assertions.assertThatCode(() -> carService.update(CarCreator.createValidCarPutDTO()))
                .doesNotThrowAnyException();
    }

    @Test
    void delete_RemovesCar_WhenSuccessful() {
        Assertions.assertThatCode(() -> carService.delete(1))
                .doesNotThrowAnyException();
    }
}