package com.dda.transactions.service.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dda.transactions.dao.TransactionDao;
import com.dda.transactions.model.Transaction;
import com.dda.transactions.service.TransactionService;
import com.dda.transactions.soap.BankCatalogClient;
import com.google.gson.Gson;

import bank_web_service.GetBankDetailsResponse;

@Service
public class TransactionServiceImpl implements TransactionService 
{

	@Autowired
	private TransactionDao transactionDao;
	
	@Autowired
	BankCatalogClient bankCatalogClient;

	@Override
	public List<Transaction> fetchList1(String accountId, String status) 
	{
		
		return transactionDao.fetchList1(accountId, status);
	}

	@Override
	public int isAccountIdExists(String accountId) 
	{
		
		return transactionDao.isAccountIdExists(accountId);
	}
	@Override
	public void soapPrint(String customerId)
	{
		GetBankDetailsResponse response = bankCatalogClient.getBankById(customerId);
		Gson gson = new Gson();
		System.out.println(gson.toJson(response));
		try
		{
		    String filename= "MyFile.txt";
		    FileWriter fw = new FileWriter(filename,true); 
		    fw.write(gson.toJson(response));
		    fw.close();
		}
		catch(IOException ioe)
		{
		    System.err.println("IOException: " + ioe.getMessage());
		}
		
	}

	
	

}
