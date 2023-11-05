package com.dws.challenge.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dws.challenge.domain.TransferBetweenAcountRequest;
import com.dws.challenge.domain.TransferBetweenAcountResponse;
import com.dws.challenge.service.TransferBetweenAcountService;

@RestController
public class TransferBewteenAccountsController {
	
	private TransferBetweenAcountService transferBetweenAcountService;
	
	public TransferBewteenAccountsController(TransferBetweenAcountService transferBetweenAcountService){
		this.transferBetweenAcountService=transferBetweenAcountService;
	}
	
	 @PostMapping(value="/transferMoneyBetweenAccounts")
	    public ResponseEntity<TransferBetweenAcountResponse> getTestData(@Valid @RequestBody TransferBetweenAcountRequest request) {
		 	TransferBetweenAcountResponse response = transferBetweenAcountService.transferAmountBetweenAccount(request);
		 	
	        if(response.getError().isBlank()) {
	        	return ResponseEntity.status(500).body(response);
	        	
	        }else {
	        	 return ResponseEntity.of(Optional.of(response));
	        	
	        }
	        
	        
	    }

	 
	 @ResponseStatus(HttpStatus.BAD_REQUEST)
	 @ExceptionHandler(MethodArgumentNotValidException.class)
	 public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
	    
	  return ex.getBindingResult().getAllErrors().stream().collect(Collectors.toMap(error -> ((FieldError) error).getField(),error->error.getDefaultMessage()));
	     
	 }
}



