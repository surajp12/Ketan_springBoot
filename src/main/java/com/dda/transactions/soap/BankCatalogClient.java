package com.dda.transactions.soap;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import bank_web_service.GetBankDetailsRequest;
import bank_web_service.GetBankDetailsResponse;

public class BankCatalogClient extends WebServiceGatewaySupport {

	public GetBankDetailsResponse getBankById(String id) {
		GetBankDetailsRequest request = new GetBankDetailsRequest();
		request.setCustomerId(id);
		GetBankDetailsResponse response = (GetBankDetailsResponse) getWebServiceTemplate()
				.marshalSendAndReceive(request, new SoapActionCallback("http://34.236.109.151:8000/ws/"));
		return response;
	}
}