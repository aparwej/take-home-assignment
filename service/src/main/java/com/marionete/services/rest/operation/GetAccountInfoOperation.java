package com.marionete.services.rest.operation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;

import com.marionete.services.model.AccountInfo;
import com.marionete.services.rest.support.RestServiceOperation;

public class GetAccountInfoOperation implements RestServiceOperation {
	private static final List<MediaType> acceptMediaTypes = Arrays.asList(MediaType.APPLICATION_JSON);

	@Override
	public String getPath() {
		return "/account/";
	}

	@Override
	public Map<String, String> getParams() {
		return new HashMap<>();
	}

	@Override
	public List<MediaType> getAcceptMediaTypes() {
		return acceptMediaTypes;
	}

	@Override
	public Class<?> getResponseType() {
		return AccountInfo.class;
	}
}