package org.nahid.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Some constraints are some violated..")
public class ConstraintsViolationException extends Exception{


    public ConstraintsViolationException(String message) {
        super(message);
    }

}