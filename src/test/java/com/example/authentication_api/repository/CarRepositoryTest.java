package com.example.authentication_api.repository;

import com.example.authentication_api.model.entity.Car;
import com.example.authentication_api.util.CarCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;


@DataJpaTest
class CarRepositoryTest {
    @Autowired
    CarRepository carRepository;

    @Test
    void save_PersistCar_WhenSuccessful() {
        Car carToBeSaved = CarCreator.createCarToBeSaved();
        Car savedCar = carRepository.save(carToBeSaved);

        Assertions.assertThat(savedCar).isNotNull();
        Assertions.assertThat(savedCar).isEqualTo(carToBeSaved);
    }

    @Test
    void save_UpdatesCar_WhenCarAlreadyExists() {
        Car carToBeSaved = CarCreator.createCarToBeSaved();
        Car savedCar = carRepository.save(carToBeSaved);

        savedCar.setName("nome atualizado");
        Car updatedCar = carRepository.save(savedCar);

        Assertions.assertThat(updatedCar).isNotNull();
        Assertions.assertThat(updatedCar.getId()).isEqualTo(savedCar.getId());
        Assertions.assertThat(updatedCar.getName()).isEqualTo("nome atualizado");
    }

    @Test
    void findAll_ReturnsAllCars_WhenSuccessful() {
        Car saved = carRepository.save(CarCreator.createCarToBeSaved());
        carRepository.save(CarCreator.createCarToBeSaved());

        List<Car> all = carRepository.findAll();

        Assertions.assertThat(all).isNotNull().isNotEmpty();
        Assertions.assertThat(all.size()).isEqualTo(2);
        Assertions.assertThat(all).contains(saved);
    }

    @Test
    void findByName_ReturnsEmptyList_WhenCarIsNotFound() {
        List<Car> carsByName = carRepository.findByName("");

        Assertions.assertThat(carsByName).isEmpty();
    }

    @Test
    void findByName_ReturnsListOfCars_WhenSuccessful() {
        Car saved = carRepository.save(CarCreator.createCarToBeSaved());

        List<Car> carsByName = carRepository.findByName(saved.getName());

        Assertions.assertThat(carsByName).isNotEmpty();
        Assertions.assertThat(carsByName).contains(saved);
    }

    @Test
    void findAll_ReturnsEmptyList_WhenCarIsNotFound() {
        List<Car> all = carRepository.findAll();

        Assertions.assertThat(all).isEmpty();
    }

    @Test
    void delete_RemovesCar_WhenSuccessful() {
        Car savedCar = carRepository.save(CarCreator.createCarToBeSaved());
        carRepository.delete(savedCar);

        Assertions.assertThat(carRepository.findAll()).isEmpty();
    }
}