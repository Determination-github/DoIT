package com.doit.study.exception;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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


}
