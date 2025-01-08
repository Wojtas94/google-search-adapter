package com.avenga.googlesearchadapter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Hidden
@RestControllerAdvice
public class ExceptionHandlerController {

	 @ExceptionHandler(RestClientException.class)
	 public ResponseEntity<String> handleNotFoundException(RestClientException ex) {
		log.error(ex.getMessage());
	 	return new ResponseEntity<String>(ex.getMessage(),  HttpStatus.SERVICE_UNAVAILABLE);
	 }
	
}
