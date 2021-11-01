package com.marionete.services.rest.support;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.marionete.services.exception.ServiceBusinessException;

@Component
public class RestServiceSupport {

	@Autowired
	private RestTemplate restTemplate;

	public Object call(String uri, RestServiceOperation restServiceOperation, String loginToken) throws ServiceBusinessException{
		return makeRestCall(uri, restServiceOperation.getResponseType(), restServiceOperation, loginToken).getBody();
	}

	protected <T> ResponseEntity<T> makeRestCall(String uri, Class<T> responseType,
			RestServiceOperation restServiceOperation, String loginToken) throws ServiceBusinessException{

		ResponseEntity<T> result = null;
		String endpoint = String.format("%s%s", uri, restServiceOperation.getPath());
		HttpEntity<Object> entity = new HttpEntity<>(buildHttpHeaders(loginToken, restServiceOperation.getAcceptMediaTypes()));
		try {
			result = restTemplate.exchange(endpoint, restServiceOperation.getHttpMethod(), entity, responseType);
		} catch (RestClientException e) {
			System.err.println("Service Error!!" +e.getMessage());
			throw new ServiceBusinessException(e.getMessage(), e);
		}
		return result;
	}
	
	private HttpHeaders buildHttpHeaders(String token, List<MediaType> mediaTypes) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setBearerAuth(token);
		httpHeaders.setAccept(mediaTypes);
		return httpHeaders;
	}
}
