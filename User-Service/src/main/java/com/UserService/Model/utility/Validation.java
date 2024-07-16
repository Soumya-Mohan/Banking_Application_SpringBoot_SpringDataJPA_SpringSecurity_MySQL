package com.UserService.Model.utility;

import lombok.extern.slf4j.Slf4j;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Validation {

    public static boolean isValid(String number,String regex) {
        log.info("Inside Validation::isValid ");
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);
        log.debug("Inside Validation::isValid with number : {}and regex : {}", number, regex);
        return matcher.matches();
    }

}
