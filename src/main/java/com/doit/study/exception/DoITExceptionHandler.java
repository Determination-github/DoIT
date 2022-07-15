package com.doit.study.exception;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

@Slf4j
@ControllerAdvice
public class DoITExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public void notFoundException(NotFoundException notFoundException, HttpServletResponse response) throws IOException {
        log.error("NotFoundException 실행", notFoundException.getMessage());
        response.sendError(404);
    }

    @ExceptionHandler(NullPointerException.class)
    public void nullPointerException(NullPointerException nullPointerException, HttpServletResponse response) throws IOException {
        log.error("NullPointerException 실행", nullPointerException.getMessage());
        response.sendError(404);
    }

    @ExceptionHandler(IOException.class)
    public void exceptionI0(IOException ioException, HttpServletResponse response) throws IOException {
        log.error("IOException 실행", ioException.getMessage());
        response.sendError(500);
    }

    @ExceptionHandler(ParseException.class)
    public void parseException(ParseException parseException, HttpServletResponse response) throws IOException {
        log.error("ParseException 실행", parseException.getMessage());
        response.sendError(500);
    }


}
