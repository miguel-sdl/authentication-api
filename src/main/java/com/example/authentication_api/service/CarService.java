package com.example.authentication_api.service;

import com.example.authentication_api.model.dto.CarPostDTO;
import com.example.authentication_api.model.dto.CarPutDTO;
import com.example.authentication_api.model.entity.Car;
import com.example.authentication_api.model.mapper.CarMapper;
import com.example.authentication_api.repository.CarRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
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

    public List<Car> findByName(String name) {
        return carRepository.findByName(name);
    }

    public Car save(CarPostDTO dto) {
        Car car = CarMapper.INSTANCE.toCar(dto);
        log.info("Salvando carro no db");
        return carRepository.save(car);
    }

    public void update(CarPutDTO dto) {
        findById(dto.id());
        Car car = CarMapper.INSTANCE.toCar(dto);
        carRepository.save(car);
        log.info("Carro id {} atualizado no db", dto.id());
    }

    public void delete(int id) {
        carRepository.delete(findById(id));
        log.info("Carro id {} removido do db", id);
    }
}
