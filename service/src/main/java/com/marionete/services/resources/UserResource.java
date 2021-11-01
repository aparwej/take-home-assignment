package com.marionete.services.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.marionete.services.grpc.LoginClient;
import com.marionete.services.model.UserAccount;
import com.marionete.services.model.UserAccountRequest;
import com.marionete.services.service.UserAccountService;

import services.LoginRequest;

@RestController
public class UserResource {

	@Autowired
	UserAccountService userAccountService;
	
	@Autowired
	LoginClient loginClient;

	@PostMapping("/marionete/useraccount")
	public ResponseEntity<UserAccount> getUserAccount(@RequestBody UserAccountRequest loginRequest) {
		
		String loginToken = getLoginToken(loginRequest);
		System.out.println("Token - " + loginToken);
		
		if (!loginToken.isBlank()) {
			return ResponseEntity.status(HttpStatus.OK).body(userAccountService.getUserAccount(loginToken));
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	public String getLoginToken(UserAccountRequest request) {
		LoginRequest loginReq = LoginRequest.newBuilder().setUsername(request.getUserName())
				.setPassword(request.getPassword()).build();
		return loginClient.login(loginReq).getToken();
	}
}
