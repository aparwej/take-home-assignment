package com.marionete.services.rest.operation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;

import com.marionete.services.model.UserInfo;
import com.marionete.services.rest.support.RestServiceOperation;

public class GetUserAccountOperation implements RestServiceOperation {

	private static final List<MediaType> acceptMediaTypes = Arrays.asList(MediaType.APPLICATION_JSON);

	@Override
	public String getPath() {
		return "/user/";
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
		return UserInfo.class;
	}

}
