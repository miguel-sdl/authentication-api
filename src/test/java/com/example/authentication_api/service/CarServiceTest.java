package com.example.authentication_api.service;

import com.example.authentication_api.repository.CarRepository;
import org.assertj.core.api.Assertions;
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

    @Test
    void findById_ThrowsRuntimeException_WhenCarIsNotFound() {
        BDDMockito.when(carRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> carService.findById(1)).isInstanceOf(RuntimeException.class);
    }
}