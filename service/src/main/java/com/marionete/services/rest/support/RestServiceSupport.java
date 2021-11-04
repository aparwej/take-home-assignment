package com.marionete.services.rest.support;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${service.retry.count}")
	Integer retryCount;

	public Object call(String uri, RestServiceOperation restServiceOperation, String loginToken)
			throws ServiceBusinessException {
		return makeRestCall(uri, restServiceOperation.getResponseType(), restServiceOperation, loginToken).getBody();
	}

	protected <T> ResponseEntity<T> makeRestCall(String uri, Class<T> responseType,
			RestServiceOperation restServiceOperation, String loginToken) throws ServiceBusinessException {

		ResponseEntity<T> result = null;
		String endpoint = String.format("%s%s", uri, restServiceOperation.getPath());
		HttpEntity<Object> entity = new HttpEntity<>(
				buildHttpHeaders(loginToken, restServiceOperation.getAcceptMediaTypes()));

		for (int retry = 1; retry <= retryCount; retry++) {
			try {
				result = restTemplate.exchange(endpoint, restServiceOperation.getHttpMethod(), entity, responseType);
				break;
			} catch (RestClientException e) {
				System.err.println(e.getLocalizedMessage());
				if(e.getMessage().startsWith("503 Service Unavailable")){
					System.err.println("Service Call Failed - Retrying - "+ retry);
				}else {
					System.err.println("Service Error!!" + e.getMessage());
					throw new ServiceBusinessException(e.getMessage(), e);
				}
			}
		}
		return result;
	}

	private HttpHeaders buildHttpHeaders(String token, List<MediaType> mediaTypes) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", token);
		httpHeaders.setAccept(mediaTypes);
		return httpHeaders;
	}
}
