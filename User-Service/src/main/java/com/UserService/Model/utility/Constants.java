package com.UserService.Model.utility;

public class Constants {

    public static final String AADHAR_REGEX="^[2-9][0-9]{3}[0-9]{4}[0-9]{4}$";
    public static final String PAN_REGEX="[A-Z]{5}[0-9]{4}[A-Z]{1}";
    public static final String EMAIL_REGEX = "^(.+)@(.+)$";
    public static final String INPUT_NULL = "Input data should not be null";
    public static final int INPUT_NULL_CODE = 100;
    public static final String USER_ALREADY_FOUND = "User is Already Exits";
    public static final int USER_ALREADY_FOUND_CODE = 101;

    public static final String SUCCESS = "Success";
    public static final int SUCCESS_CODE = 200;
    public static final String AADHAR_VALIDATION_FAIL = "Invalid Aadhar Number";
    public static final String PAN_VALIDATION_FAIL = "Invalid PAN Number";
    public static final int AADHAR_VALIDATION_FAIL_CODE = 102;
    public static final int PAN_VALIDATION_FAIL_CODE = 103;
    public static final String DATA_NOT_FOUND = "The given data does not exists please Register to get HDFC Benefits";
    public static final int DATA_NOT_FOUND_CODE = 104;


    public static final String NOT_FOUND = "404";
    public static final String CONFLICT = "409";

    public static final String BAD_REQUEST = "400";

    //Kafka Topics
    public static final String CREATE_NEW_ACCOUNT_ID_TOPIC="Create-account";

}
