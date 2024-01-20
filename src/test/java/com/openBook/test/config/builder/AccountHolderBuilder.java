package com.openBook.test.config.builder;

import com.openBook.model.AccountHolder;
import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;
import java.util.Random;

public class AccountHolderBuilder {

    private static final String LOWER_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBER_CHARACTERS = "0123456789";
    private static final String EMAIL_DOMAIN = "@mail.com";

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public AccountHolderBuilder() {
        id = new Random().nextLong();
        firstName = RandomStringUtils.randomAlphabetic(10);
        lastName = RandomStringUtils.randomAlphabetic(10);
        email = generateComplexEmail();
        password = RandomStringUtils.random(20, true, true);
    }

    public AccountHolder build() {
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setId(id);
        accountHolder.setFirstName(firstName);
        accountHolder.setLastName(lastName);
        accountHolder.setEmail(email);
        accountHolder.setPassword(password);
        return accountHolder;
    }

    public AccountHolderBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public AccountHolderBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public AccountHolderBuilder withLastName(String lastName) {
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
