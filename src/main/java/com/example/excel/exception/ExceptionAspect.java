package com.example.excel.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice(annotations = {Controller.class, RestController.class})
public class ExceptionAspect {

    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Object handle(HttpServletRequest request, HttpServletResponse response,
                         MethodArgumentNotValidException e) throws IOException {
        log.error("invoke params error: url={}, queryString={}",
                getPath(request), request.getQueryString());
        e.getBindingResult();
        return response;
    }

    @ResponseBody
    @ExceptionHandler({ConstraintViolationException.class})
    public Object handleParamsValidate(HttpServletRequest request, HttpServletResponse response,
                                       ConstraintViolationException e) throws IOException {
        log.error("invoke params validate fail: url={}, queryString={}",
                getPath(request), request.getQueryString());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        final List<String> msgs = e.getConstraintViolations().stream()
                .map(p -> p.getMessage())
                .collect(Collectors.toList());
        return response;
    }

    @ResponseBody
    @ExceptionHandler(Throwable.class)
    public Object handleException(HttpServletRequest request, HttpServletResponse response, Throwable throwable)
            throws IOException {
        log.error("invoke fail: url={}, queryString={}", getPath(request), request.getQueryString(), throwable);
        response.setStatus(HttpStatus.OK.value());
        return response;
    }

    private String getPath(HttpServletRequest request) {
        return request.getRequestURI().substring(request.getContextPath().length());
    }


}
