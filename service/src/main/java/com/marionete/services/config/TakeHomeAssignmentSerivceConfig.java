package com.marionete.services.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import com.marionete.services.grpc.LoginClient;

@Configuration
public class TakeHomeAssignmentSerivceConfig {

	@Value("${grpc.server.host}")
    private String host;

    @Value("${grpc.server.port}")
    private Integer port;
    
    @Bean
    LoginClient LoginClient() {
    	return new LoginClient(host, port);
    }

    @Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
		messageConverters.add(converter);
		builder.messageConverters(messageConverters);
		RestTemplate restTemplate = builder.build();
		restTemplate.setMessageConverters(messageConverters);
		return restTemplate;
	}
}
