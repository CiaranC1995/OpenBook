package com.openBook.test.config.builder;

import com.openBook.model.AccountHolder;
import com.openBook.test.config.util.RandomUtil;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class AccountHolderBuilder {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public AccountHolderBuilder() {
        id = new Random().nextLong();
        firstName = RandomStringUtils.randomAlphabetic(10);
        lastName = RandomStringUtils.randomAlphabetic(10);
        email = RandomUtil.generateComplexEmail();
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
}
