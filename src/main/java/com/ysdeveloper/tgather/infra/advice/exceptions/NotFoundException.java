package com.ysdeveloper.tgather.infra.advice.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException ( String message ) {
        super( message );
    }

    public NotFoundException ( String message, Throwable cause ) {
        super( message, cause );
    }

}