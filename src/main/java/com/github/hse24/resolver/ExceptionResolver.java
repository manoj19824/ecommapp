package com.github.hse24.resolver;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.github.hse24.exception.NotFoundException;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
 * Exception Resolver.
 * Unhandled exceptions in Controller will be formatted here.
 *
 * @author Manoj Mohapatro
 */
@ControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler(value = NotFoundException.class)
    public void handleNotFoundException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    public void handleIllegalArgumentException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

}