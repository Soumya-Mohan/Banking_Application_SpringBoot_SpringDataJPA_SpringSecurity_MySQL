package com.AccountService.Model.utility;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Validation {

    public static boolean isValid(String number, String regex) {
        log.info("Inside Validation::isValid ");
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);
        log.debug("Inside Validation::isValid with number : {}and regex : {}", number, regex);
        return matcher.matches();
    }


    public static String generateRandomNumber(int length) {
        String characters = "0123456789";
        StringBuilder number = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 7; i++) {
            number.append(characters.charAt(random.nextInt(characters.length())));
        }
        return number.toString();
    }

}
