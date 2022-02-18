package com.java.sunny.exceptionHandler;

import java.time.LocalDateTime;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.java.sunny.entity.EmployeeResponse;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {

	@Override
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		EmployeeResponse errorResponse = new EmployeeResponse();

		ex.getBindingResult().getAllErrors().forEach((error) -> {
			errorResponse.setTimeStamp(LocalDateTime.now().toString());
			errorResponse.setResponseCode(HttpStatus.BAD_REQUEST.toString());
			errorResponse.setErrorMessage(((FieldError) error).getField()+" field is invalid"+error.getDefaultMessage());
			errorResponse.setEmployee(null);

		});
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<Object> handleNumberFormatException(NumberFormatException ex) {

		EmployeeResponse errorResponse = new EmployeeResponse();

		errorResponse.setTimeStamp(LocalDateTime.now().toString());
		errorResponse.setResponseCode(HttpStatus.BAD_REQUEST.toString());
		errorResponse.setErrorMessage("Invalid number format. "+ex.getLocalizedMessage());
		errorResponse.setEmployee(null);

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Object> handleUnexpectedTypeException(MethodArgumentTypeMismatchException ex) {
		EmployeeResponse errorResponse = new EmployeeResponse();

		errorResponse.setTimeStamp(LocalDateTime.now().toString());
		errorResponse.setResponseCode(HttpStatus.BAD_REQUEST.toString());
		errorResponse.setErrorMessage(ex.getName()+" field type is not correct. "+ex.getLocalizedMessage());
		errorResponse.setEmployee(null);

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<Object> handleBusinessException(BusinessException ex) {
		EmployeeResponse errorResponse = new EmployeeResponse();

		errorResponse.setTimeStamp(ex.getTimeStamp());
		errorResponse.setResponseCode(ex.getError());
		errorResponse.setErrorMessage(ex.getLocalizedMessage());
		errorResponse.setEmployee(null);

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<Object> handleNullPointerException(NullPointerException ex) {
		EmployeeResponse errorResponse = new EmployeeResponse();

		errorResponse.setTimeStamp(LocalDateTime.now().toString());
		errorResponse.setResponseCode(HttpStatus.BAD_REQUEST.toString());
		errorResponse.setErrorMessage(ex.getLocalizedMessage());
		errorResponse.setEmployee(null);


		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

}
