package com.example.authentication_api.model.mapper;

import com.example.authentication_api.model.dto.CarPostDTO;
import com.example.authentication_api.model.dto.CarPutDTO;
import com.example.authentication_api.model.entity.Car;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class CarMapper {
    public static CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    public abstract Car toCar(CarPostDTO dto);
    public abstract Car toCar(CarPutDTO dto);
}
