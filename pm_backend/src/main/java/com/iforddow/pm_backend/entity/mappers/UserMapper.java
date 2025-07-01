package com.iforddow.pm_backend.entity.mappers;

import com.iforddow.pm_backend.dto.user.UserDTO;
import com.iforddow.pm_backend.entity.entity.user.User;
import com.iforddow.pm_backend.entity.jpa.entity.UserAuth;
import com.iforddow.pm_backend.entity.jpa.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Conversion methods between User and UserEntity
    @Mapping(source = "authEntity.id", target = "id")
    User toUser(UserAuth authEntity, UserProfile profileEntity);

    // Database entity conversions
    UserAuth toAuthEntity(User user);
    UserProfile toProfileEntity(User user);

    // Conversion methods for DTOs
    UserDTO toDTO(User user);

}
