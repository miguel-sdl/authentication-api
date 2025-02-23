package com.example.authentication_api.model.mapper;

import com.example.authentication_api.model.dto.RegisterDTO;
import com.example.authentication_api.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    public static UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    public abstract User toUser(RegisterDTO dto);
}
