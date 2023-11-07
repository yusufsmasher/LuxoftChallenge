package com.dws.challenge;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.dws.challenge.domain.Account;
import com.dws.challenge.domain.TransferBetweenAcountRequest;
import com.dws.challenge.domain.TransferBetweenAcountResponse;
import com.dws.challenge.repository.AccountsRepositoryInMemory;
import com.dws.challenge.service.EmailNotificationService;
import com.dws.challenge.service.TransferBetweenAcountService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TransferBetweenAccountServiceTest {

	@Mock
	  private AccountsRepositoryInMemory accountsRepositoryInMemory;
	
	@Mock
	  private EmailNotificationService emailNotificationService;
	@InjectMocks
	TransferBetweenAcountService  transferBetweenAcountService;
	
	
	@Test
	void testSuccess() throws Exception{
		
		when(accountsRepositoryInMemory.getAccount("Id-123")).thenReturn(new Account("Id-123", new BigDecimal(600)));
		when(accountsRepositoryInMemory.getAccount("Id-124")).thenReturn(new Account("Id-124", new BigDecimal(400)));
		TransferBetweenAcountRequest request = new TransferBetweenAcountRequest();
		request.setFromAccountID("Id-123");
		request.setToAccountID("Id-124");
		request.setAmount(new BigDecimal(300));
		TransferBetweenAcountResponse response = transferBetweenAcountService.transferAmountBetweenAccount(request);
		assertThat(response.getStatus()).isEqualTo("Success");
		assertThat(response.getError()).isEqualTo(null);
	}
	
	@Test
	void testError() throws Exception{
		
		when(accountsRepositoryInMemory.getAccount("Id-123")).thenReturn(new Account("Id-123", new BigDecimal(600)));
		
		TransferBetweenAcountRequest request = new TransferBetweenAcountRequest();
		request.setFromAccountID("Id-123");
		request.setToAccountID("Id-123");
		request.setAmount(new BigDecimal(300));
		TransferBetweenAcountResponse response = transferBetweenAcountService.transferAmountBetweenAccount(request);
		assertThat(response.getError()).isEqualTo("Same Account ID in From And TO Account Used Please Check The Request");
		assertThat(response.getStatus()).isEqualTo("Failure");
	}
	
	@Test
	void testError2() throws Exception{
		
		when(accountsRepositoryInMemory.getAccount("Id-123")).thenReturn(new Account("Id-123", new BigDecimal(600)));
		when(accountsRepositoryInMemory.getAccount("Id-124")).thenReturn(new Account("Id-124", new BigDecimal(400)));
		TransferBetweenAcountRequest request = new TransferBetweenAcountRequest();
		request.setFromAccountID("Id-123");
		request.setToAccountID("Id-124");
		request.setAmount(new BigDecimal(700));
		TransferBetweenAcountResponse response = transferBetweenAcountService.transferAmountBetweenAccount(request);
		assertThat(response.getError()).isEqualTo("In Sufficient Balance in From Account ID: Id-123");
		assertThat(response.getStatus()).isEqualTo("Failure");
	}
	
	
	@Test
	void testError3() throws Exception{
		
		when(accountsRepositoryInMemory.getAccount("Id-123")).thenThrow(new RuntimeException());
		when(accountsRepositoryInMemory.getAccount("Id-124")).thenReturn(new Account("Id-124", new BigDecimal(400)));
		TransferBetweenAcountRequest request = new TransferBetweenAcountRequest();
		request.setFromAccountID("Id-123");
		request.setToAccountID("Id-124");
		request.setAmount(new BigDecimal(700));
		Exception exception = assertThrows(RuntimeException.class, () -> {
			transferBetweenAcountService.transferAmountBetweenAccount(request);
	    });
		
	}
	
	@Test
	void testError4() throws Exception{
		
		when(accountsRepositoryInMemory.getAccount("Id-123")).thenReturn(null);
		when(accountsRepositoryInMemory.getAccount("Id-124")).thenReturn(new Account("Id-124", new BigDecimal(400)));
		TransferBetweenAcountRequest request = new TransferBetweenAcountRequest();
		request.setFromAccountID("Id-123");
		request.setToAccountID("Id-124");
		request.setAmount(new BigDecimal(700));
		TransferBetweenAcountResponse response = transferBetweenAcountService.transferAmountBetweenAccount(request);
		assertThat(response.getError()).isEqualTo("The From Account is Not Valid");
		assertThat(response.getStatus()).isEqualTo("Failure");
	}
	
	@Test
	void testError5() throws Exception{
		
		when(accountsRepositoryInMemory.getAccount("Id-123")).thenReturn(new Account("Id-123", new BigDecimal(400)));
		when(accountsRepositoryInMemory.getAccount("Id-124")).thenReturn(null);
		TransferBetweenAcountRequest request = new TransferBetweenAcountRequest();
		request.setFromAccountID("Id-123");
		request.setToAccountID("Id-124");
		request.setAmount(new BigDecimal(700));
		TransferBetweenAcountResponse response = transferBetweenAcountService.transferAmountBetweenAccount(request);
		assertThat(response.getError()).isEqualTo("The To Account is Not Valid");
		assertThat(response.getStatus()).isEqualTo("Failure");
	}
	
	
	
}

	