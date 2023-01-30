package com.ysdeveloper.tgather.infra.advice.exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException () {super();}

    public BadRequestException ( String msg ) {super( msg );}

}
