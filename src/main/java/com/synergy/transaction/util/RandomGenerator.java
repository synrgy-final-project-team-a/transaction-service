package com.synergy.transaction.util;

import java.security.SecureRandom;
import java.time.LocalDateTime;

public class RandomGenerator {
    private static final SecureRandom random = new SecureRandom();
    private static final String alphaNumeric = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final LocalDateTime datetime = LocalDateTime.now();

    private static String getCode(Integer length) {
        StringBuilder sb = new StringBuilder(length);

        while (length > 0) {
            sb.append(alphaNumeric.charAt(random.nextInt(alphaNumeric.length())));
            length--;
        }

        return sb.toString();
    }

    public static String getBookCode(Long objectId) {
        String code = getCode(6);
        return objectId + "-" + code + "-" + datetime.getYear();
    }

    public static String getTransactionCode(Long objectId) {
        String code = getCode(8);
        return datetime.getMinute() + "-" + code;
    }
}
