package com.openBook.mapper;

import com.openBook.dto.UserDTO;
import com.openBook.model.User;
import com.openBook.test.config.builder.UserBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private static User testUserOne;
    private static User testUserTwo;

    @BeforeAll
    public static void beforeAll() {
        testUserOne = new UserBuilder()
                .build();

        testUserTwo = new UserBuilder()
                .build();
    }

    @Test
    void userToUserDTOTest() {
        // Given User as defined above, When
        UserDTO userDTO = userMapper.userToUserDTO(testUserOne);

        // Then
        assertEquals(testUserOne.getId(), userDTO.getId());
        assertEquals(testUserOne.getFirstName(), userDTO.getFirstName());
        assertEquals(testUserOne.getLastName(), userDTO.getLastName());
        assertEquals(testUserOne.getEmail(), userDTO.getEmail());
        assertEquals(testUserOne.getPassword(), userDTO.getPassword());
    }

    @Test
    void userDTOToUserTest() {
        // Given User as defined above, When
        UserDTO userDTO = userMapper.userToUserDTO(testUserOne);

        // Act
        User user = userMapper.userDTOToUser(userDTO);

        // Assert
        assertEquals(userDTO.getId(), user.getId());
        assertEquals(userDTO.getFirstName(), user.getFirstName());
        assertEquals(userDTO.getLastName(), user.getLastName());
        assertEquals(userDTO.getEmail(), user.getEmail());
        assertEquals(userDTO.getPassword(), user.getPassword());
    }

    @Test
    void userListToUserDTOListTest() {
        // Arrange
        List<User> userList = Arrays.asList(testUserOne,testUserTwo);

        // Act
        List<UserDTO> userDTOList = userMapper.userListToUserDTOList(userList);

        // Assert
        assertEquals(userList.size(), userDTOList.size());

        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            UserDTO userDTO = userDTOList.get(i);
            assertEquals(user.getId(), userDTO.getId());
            assertEquals(user.getFirstName(), userDTO.getFirstName());
            assertEquals(user.getLastName(), userDTO.getLastName());
            assertEquals(user.getEmail(), userDTO.getEmail());
            assertEquals(user.getPassword(), userDTO.getPassword());
        }
    }
}
