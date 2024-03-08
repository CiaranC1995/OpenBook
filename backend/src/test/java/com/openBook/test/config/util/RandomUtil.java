package com.openBook.test.config.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;

public class RandomUtil {

    private static final String LOWER_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBER_CHARACTERS = "0123456789";
    private static final String EMAIL_DOMAIN = "@mail.com";

    public static String generateComplexEmail() {

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
