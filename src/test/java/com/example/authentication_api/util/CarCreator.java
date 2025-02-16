package com.example.authentication_api.util;

import com.example.authentication_api.model.dto.CarPostDTO;
import com.example.authentication_api.model.dto.CarPutDTO;
import com.example.authentication_api.model.entity.Car;

public class CarCreator {
    public static Car createCarToBeSaved() {
        return Car.builder()
                .name("Honda Civic")
                .price(100_000.00)
                .year(2017).build();
    }

    public static Car createValidCar() {
        return Car.builder()
                .id(1)
                .name("Honda Civic")
                .price(100_000.00)
                .year(2017).build();
    }

    public static CarPostDTO createValidCarPostDTO() {
        return new CarPostDTO("Honda Civic", 100_000.00, 2017);
    }

    public static CarPutDTO createValidCarPutDTO() {
        return new CarPutDTO(1, "Honda Civic", 100_000.00, 2017);
    }
}
