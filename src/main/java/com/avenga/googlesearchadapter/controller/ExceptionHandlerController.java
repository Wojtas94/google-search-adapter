package com.avenga.googlesearchadapter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.avenga.googlesearchadapter.dto.ErrorResponseDto;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Hidden
@ResponseBody
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler{

	 @ExceptionHandler(RestClientException.class)
	 @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	 public ErrorResponseDto handleNotFoundException(RestClientException exception) {
		log.error(exception.getMessage());
		return ErrorResponseDto.builder()
	            .status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
	            .description(exception.getMessage())
	            .build();
	 }
	
}
