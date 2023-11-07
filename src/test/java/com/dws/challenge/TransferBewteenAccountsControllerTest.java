package com.dws.challenge;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.dws.challenge.controller.TransferBewteenAccountsController;
import com.dws.challenge.domain.TransferBetweenAcountResponse;
import com.dws.challenge.service.TransferBetweenAcountService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@WebAppConfiguration
public class TransferBewteenAccountsControllerTest {
	
	private MockMvc mockMvc;

	  @MockBean
	  private TransferBetweenAcountService transferBetweenAcountService;
	  
	  @InjectMocks
	  TransferBewteenAccountsController transferBewteenAccountsController;

	  @Autowired
	  private WebApplicationContext webApplicationContext;

	  @BeforeEach
	  void prepareMockMvc() {
	    this.mockMvc = webAppContextSetup(this.webApplicationContext).build();

	    
	  }
	  
	  @Test
	  public void testTransferSuccessfull()throws Exception{
		  when(transferBetweenAcountService.transferAmountBetweenAccount(any())).thenReturn(generateSuccessResponse());
		  this.mockMvc.perform(post("/transferMoneyBetweenAccounts").contentType(MediaType.APPLICATION_JSON)
			      .content("{\"fromAccountID\":\"Id-123\",\"amount\":100,\"toAccountID\":\"Id-124\"}")).andExpect(status().isOk());
	  }
	  
	  private TransferBetweenAcountResponse generateSuccessResponse() {
		  TransferBetweenAcountResponse response = new TransferBetweenAcountResponse();
		  response.setStatus("Success");
		  return response;
	  }
	  
	  @Test
	  public void testTransferError()throws Exception{
		  when(transferBetweenAcountService.transferAmountBetweenAccount(any())).thenReturn(generateErrorResponse());
		  this.mockMvc.perform(post("/transferMoneyBetweenAccounts").contentType(MediaType.APPLICATION_JSON)
			      .content("{\"fromAccountID\":\"Id-123\",\"toAccountID\":\"Id-124\",\"amount\":1000}")).andExpect(status().is5xxServerError());
	  }
	  
	  private TransferBetweenAcountResponse generateErrorResponse() {
		  TransferBetweenAcountResponse response = new TransferBetweenAcountResponse();
		  response.setStatus("Failure");
		  response.setError("Same Account Id");
		  return response;
	  }
	  
	  @Test
	  public void testTransferSuccessfull2()throws Exception{
		  when(transferBetweenAcountService.transferAmountBetweenAccount(any())).thenReturn(generateSuccessResponse2());
		  this.mockMvc.perform(post("/transferMoneyBetweenAccounts").contentType(MediaType.APPLICATION_JSON)
			      .content("{\"fromAccountID\":\"Id-123\",\"toAccountID\":\"Id-124\",\"amount\":1000}")).andExpect(status().isOk());
	  }
	  
	  @Test
	  public void testTransferBadRequest()throws Exception{
		  when(transferBetweenAcountService.transferAmountBetweenAccount(any())).thenReturn(generateSuccessResponse2());
		  this.mockMvc.perform(post("/transferMoneyBetweenAccounts").contentType(MediaType.APPLICATION_JSON)
			      .content("{\"fromAccountID\":\"Id-123\",\"toAccountID\":\"Id-124\",\"amount\":0}")).andExpect(status().isBadRequest());
	  }
	  
	  @Test
	  public void testTransferBadRequest2()throws Exception{
		  when(transferBetweenAcountService.transferAmountBetweenAccount(any())).thenReturn(generateSuccessResponse2());
		  this.mockMvc.perform(post("/transferMoneyBetweenAccounts").contentType(MediaType.APPLICATION_JSON)
			      .content("{\"fromAccountID\":\"Id-123\",\"toAccountID\":\"Id-124\",\"amount\":-1}")).andExpect(status().isBadRequest());
	  }
	  
	  @Test
	  public void testTransferBadRequest3()throws Exception{
		  when(transferBetweenAcountService.transferAmountBetweenAccount(any())).thenReturn(generateSuccessResponse2());
		  this.mockMvc.perform(post("/transferMoneyBetweenAccounts").contentType(MediaType.APPLICATION_JSON)
			      .content("{\"fromAccountID\":\"Id-123\",\"toAccountID\":\"\",\"amount\":1000}")).andExpect(status().isBadRequest());
	  }
	  
	  @Test
	  public void testTransferBadRequest4()throws Exception{
		  when(transferBetweenAcountService.transferAmountBetweenAccount(any())).thenReturn(generateSuccessResponse2());
		  this.mockMvc.perform(post("/transferMoneyBetweenAccounts").contentType(MediaType.APPLICATION_JSON)
			      .content("{\"fromAccountID\":\"\",\"toAccountID\":\"Id-123\",\"amount\":1000}")).andExpect(status().isBadRequest());
	  }
	  
	  private TransferBetweenAcountResponse generateSuccessResponse2() {
		  TransferBetweenAcountResponse response = new TransferBetweenAcountResponse();
		  response.setError(" ");
		  response.setStatus("Success");
		  return response;
	  }
	  
	  
	  

}
