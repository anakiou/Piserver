package com.anakiou.web;

import com.anakiou.error.InputNotFoundException;
import com.anakiou.error.OutputNotFoundException;
import com.anakiou.error.PifaceNotReadyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllersExceptionHandler {

    @ExceptionHandler(InputNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The input specified does not exist")
    public String inputNotFound(final InputNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(OutputNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The output specified does not exist")
    public String outputNotFound(final OutputNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(PifaceNotReadyException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "There is a hardware problem, try again later")
    public String pifaceNotReady(final PifaceNotReadyException e) {
        return e.getMessage();
    }
}
