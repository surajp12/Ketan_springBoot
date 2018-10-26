
package com.dda.transactions.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dda.transactions.ampq.SimpleRpcProducerRabbitApplication;
import com.dda.transactions.exception.AccountNotFoundException;
import com.dda.transactions.exception.BadRequestException;
import com.dda.transactions.exception.CustomizedResponseEntityExceptionHandler;
import com.dda.transactions.exception.StatusBadRequestException;
import com.dda.transactions.model.Transaction;
import com.dda.transactions.service.TransactionService;
import com.dda.transactions.soap.BankCatalogClient;


/**
 * No description
 * (Generated with springmvc-raml-parser v.2.0.0)
 * 
 */
@RestController
@RequestMapping(value = "/api/v1/dda", produces = "application/json")
@Validated
public class StatusController 
{


	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private SimpleRpcProducerRabbitApplication rmpq;
	
	@Autowired
	BankCatalogClient bankCatalogClient;

	/**
	 * No description
	 * 
	 */
	
	List<Transaction> transactionDetails;


	@RequestMapping(value = "/account/{accountId}/transaction/{status}", method = RequestMethod.GET)
	public ResponseEntity<?> getTransactionsByAccountId(
			@PathVariable
			String accountId,@PathVariable
			String status) 
	{
		
		int checkId= transactionService.isAccountIdExists(accountId);
		
		String special = "[!@#$%&*()_+=|<>?{}\\[\\]~-]";
		try
		{
			if ((!(accountId.matches("[0-9]{10}"))) || accountId.matches(special)) 
				throw new BadRequestException("Invalid Account ID");

			else if(checkId==0)
				throw new AccountNotFoundException("Account not found");

			else if(!(status.equalsIgnoreCase("PENDING")))
		
				throw new StatusBadRequestException("Please Check Your Status..(Status Should be PENDING)");

			
			else
 
				//calling to database
				transactionDetails=transactionService.fetchList1(accountId, status);
			
				//calling to soap service
				transactionService.soapPrint(transactionDetails.get(0).getCustomerId());
			
				//clling to AMQP
				rmpq.sendMessage(transactionDetails);
				
				return ResponseEntity.ok(transactionService.fetchList1(accountId, status));

		}
		catch (BadRequestException bd) 
		{

			return new CustomizedResponseEntityExceptionHandler().handleBadRequest(bd);

		}
		
		catch(AccountNotFoundException ae)
		{
			return new CustomizedResponseEntityExceptionHandler().handleLoanAccountNotFoundException(ae);
		}
		
		catch(StatusBadRequestException sb)
		{
			return new CustomizedResponseEntityExceptionHandler().handleStatusBadRequest(sb);
		}
		
		catch (Exception ec) 
		{

			return new CustomizedResponseEntityExceptionHandler().handleAllExceptions(ec);

		} 


	}
}








