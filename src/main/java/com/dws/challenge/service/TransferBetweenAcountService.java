package com.dws.challenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dws.challenge.domain.Account;
import com.dws.challenge.domain.TransferBetweenAcountRequest;
import com.dws.challenge.domain.TransferBetweenAcountResponse;
import com.dws.challenge.repository.AccountsRepositoryInMemory;

import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransferBetweenAcountService {
	
	private AccountsRepositoryInMemory accountsRepositoryInMemory;
	private EmailNotificationService emailNotificationService;
	
	private static final String Common_Message="Your Account: ";
	private static final String Debit_Message="Has been Success Fully Debited with Amount: ";
	private static final String Credit_Message="Has been Success Fully Credited with Amount: ";
	private static final String Insufficient_Balance="In Sufficient Balance in From Account ID: ";
	
	@Autowired
	public TransferBetweenAcountService(AccountsRepositoryInMemory accountsRepositoryInMemory,EmailNotificationService emailNotificationService) {
		this.accountsRepositoryInMemory=accountsRepositoryInMemory;
		this.emailNotificationService=emailNotificationService;
	}
	
	@Synchronized
	public TransferBetweenAcountResponse transferAmountBetweenAccount(TransferBetweenAcountRequest request) {
		log.info("transferAmountBetweenAccount Started");
		
		TransferBetweenAcountResponse response = new TransferBetweenAcountResponse();
		Account accountFrom = null;
		Account accountTo =null;
		try {
			
		 accountFrom = accountsRepositoryInMemory.getAccount(request.getFromAccountID());
		 accountTo = accountsRepositoryInMemory.getAccount(request.getToAccountID());
		}catch (Exception e) {
			// TODO: handle exception
			log.error("transferAmountBetweenAccount ".concat(e.getMessage()));
			response.setStatus("Failure: ".concat(e.getMessage()));
			response.setError("Either From or To Is Invalid Account Please Check".concat(accountFrom.getAccountId()).concat(" ").concat(request.getToAccountID()));
			
		}
		
		if(accountFrom!=null && accountTo!=null && accountFrom.getBalance().compareTo(request.getAmount())!=-1) {
			
			response.setStatus("Success");
			
			emailNotificationService.notifyAboutTransfer(accountFrom, Common_Message.concat(accountFrom.getAccountId()).concat(Debit_Message)
					.concat(request.getAmount().toString()));
			emailNotificationService.notifyAboutTransfer(accountTo, Common_Message.concat(accountTo.getAccountId()).concat(Credit_Message)
					.concat(request.getAmount().toString()));
		}else {
			response.setStatus("Failure");
			response.setError(Insufficient_Balance.concat(accountFrom.getAccountId()));
		}
		log.info("transferAmountBetweenAccount Ended");
		return response;
	}
	
	
	
	
	

}
