package com.openBook.mapper;

import com.openBook.dto.UserDTO;
import com.openBook.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "id", source = "user.id"),
            @Mapping(target = "firstName", source = "user.firstName"),
            @Mapping(target = "lastName", source = "user.lastName"),
            @Mapping(target = "email", source = "user.email"),
            @Mapping(target = "password", source = "user.password")
    })
    UserDTO userToUserDTO(User user);

    @Mappings({
            @Mapping(target = "id", source = "userDTO.id"),
            @Mapping(target = "firstName", source = "userDTO.firstName"),
            @Mapping(target = "lastName", source = "userDTO.lastName"),
            @Mapping(target = "email", source = "userDTO.email"),
            @Mapping(target = "password", source = "userDTO.password")
    })
    User userDTOToUser(UserDTO userDTO);

    List<UserDTO> userListToUserDTOList(List<User> userList);
}