package com.marionete.services.rest.support;

import org.springframework.http.HttpMethod;

public interface RestServicePOSTOperation {

	default HttpMethod getHttpMethod() {
		return HttpMethod.POST;
	}
}
