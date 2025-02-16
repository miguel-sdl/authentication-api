package com.example.authentication_api.service;

import com.example.authentication_api.model.dto.CarPostDTO;
import com.example.authentication_api.model.dto.CarPutDTO;
import com.example.authentication_api.model.entity.Car;
import com.example.authentication_api.model.mapper.CarMapper;
import com.example.authentication_api.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Car findById(int id) {
        return carRepository.findById(id).orElseThrow(() -> new RuntimeException("Carro nao encontrado"));
    }

    public Car save(CarPostDTO dto) {
        Car car = CarMapper.INSTANCE.toCar(dto);
        return carRepository.save(car);
    }

    public void update(CarPutDTO dto) {
        findById(dto.id());
        Car car = CarMapper.INSTANCE.toCar(dto);
        carRepository.save(car);
    }

    public void delete(int id) {
        carRepository.delete(findById(id));
    }
}
