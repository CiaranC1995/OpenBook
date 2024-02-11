package com.openBook.test.config.builder;

import com.openBook.model.User;
import com.openBook.test.config.util.RandomUtil;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class UserBuilder {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public UserBuilder() {
        id = new Random().nextLong();
        firstName = RandomStringUtils.randomAlphabetic(10);
        lastName = RandomStringUtils.randomAlphabetic(10);
        email = RandomUtil.generateComplexEmail();
        password = RandomStringUtils.random(20, true, true);
    }

    public User build() {
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    public UserBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}