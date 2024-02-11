package com.openBook.test.config.builder;

import com.openBook.model.User;
import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;
import java.util.Random;

public class UserBuilder {

    private static final String LOWER_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBER_CHARACTERS = "0123456789";
    private static final String EMAIL_DOMAIN = "@mail.com";

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public UserBuilder() {
        id = new Random().nextLong();
        firstName = RandomStringUtils.randomAlphabetic(10);
        lastName = RandomStringUtils.randomAlphabetic(10);
        email = generateComplexEmail();
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

    public String generateComplexEmail() {

        StringBuilder randomPart = new StringBuilder();

        randomPart.append(RandomStringUtils.random(3, LOWER_CHARACTERS))
                .append(RandomStringUtils.random(3, NUMBER_CHARACTERS))
                .append(RandomStringUtils.random(3, UPPER_CHARACTERS));

        SecureRandom secureRandom = new SecureRandom();
        long randomValue = secureRandom.nextLong();

        randomPart.append(System.currentTimeMillis())
                .append(Math.abs(randomValue));

        return randomPart + EMAIL_DOMAIN;
    }
}
