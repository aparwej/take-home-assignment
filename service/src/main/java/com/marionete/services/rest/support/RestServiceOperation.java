package com.marionete.services.rest.support;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

public interface RestServiceOperation {

	String getPath();

	Map<String, String> getParams();

	List<MediaType> getAcceptMediaTypes();

	Class<?> getResponseType();

	default HttpMethod getHttpMethod() {
		return HttpMethod.GET;
	}

}
