package com.openBook.service;

import com.openBook.dto.UserDTO;
import com.openBook.mapper.UserMapper;
import com.openBook.model.User;
import com.openBook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(long userId);

    UserDTO updateUser(long userId, UserDTO userDTO);

    void deleteUser(long userId);
}
