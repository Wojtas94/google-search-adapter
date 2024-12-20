package com.avenga.google_search_adapter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {

	 @ExceptionHandler(RestClientException.class)
	 public ResponseEntity<String> handleNotFoundException(RestClientException ex) {
		log.error(ex.getMessage());
	 	return new ResponseEntity<String>(ex.getMessage(),  HttpStatus.SERVICE_UNAVAILABLE);
	 }
	
}
