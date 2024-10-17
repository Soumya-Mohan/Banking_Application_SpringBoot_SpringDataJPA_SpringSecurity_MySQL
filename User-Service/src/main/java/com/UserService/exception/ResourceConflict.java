package com.UserService.exception;

import com.UserService.Model.utility.Constants;

public class ResourceConflict extends GlobalException {

    public ResourceConflict() {
        super("Account already exists", Constants.CONFLICT);
    }

    public ResourceConflict(String message) {
        super(message, Constants.CONFLICT);
    }
}