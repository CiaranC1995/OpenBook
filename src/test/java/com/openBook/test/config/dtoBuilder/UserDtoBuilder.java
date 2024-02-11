package com.openBook.test.config.dtoBuilder;

import com.openBook.dto.UserDTO;
import com.openBook.test.config.util.RandomUtil;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class UserDtoBuilder {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public UserDtoBuilder() {
        id = new Random().nextLong();
        firstName = RandomStringUtils.randomAlphabetic(10);
        lastName = RandomStringUtils.randomAlphabetic(10);
        email = RandomUtil.generateComplexEmail();
        password = RandomStringUtils.random(20, true, true);
    }

    public UserDTO build() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setEmail(email);
        userDTO.setPassword(password);
        return userDTO;
    }

    public UserDtoBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UserDtoBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserDtoBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}
