package com.daily_expenses.web.advice;

import com.daily_expenses.domain.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

//    @ExceptionHandler(BusinessException.class)
//    public ResponseEntity<String> handleBusinessException(BusinessException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }
//
//    // Define other exception handlers here
//
////    En este ejemplo, la clase ControllerAdvice maneja una excepción personalizada BusinessException y devuelve una respuesta con un estado HTTP 400 (Bad Request) y el mensaje de la excepción. Puedes agregar más métodos de manejo de excepciones según las necesidades de tu aplicación.
}
