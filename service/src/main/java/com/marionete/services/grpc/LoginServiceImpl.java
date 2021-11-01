package com.marionete.services.grpc;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.marionete.services.util.TakeHomeAssignmentServiceUtil;

import io.grpc.stub.StreamObserver;
import services.LoginRequest;
import services.LoginResponse;
import services.LoginServiceGrpc;

@Service
class LoginServiceImpl extends LoginServiceGrpc.LoginServiceImplBase {

	@Override
	public void login(LoginRequest req, StreamObserver<LoginResponse> responseObserver) {
		System.out.println("Request for User - " + req.getUsername());
		LoginResponse reply = LoginResponse.newBuilder().setToken(isValidUser(req) ? UUID.randomUUID().toString() : "")
				.build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}


	//Implementation Assumption for valid user
	// 1. Password field is Base 64 encoding String
	// 2. User and password having same value will be treated as valid user.
	private boolean isValidUser(LoginRequest req) {
		return req.getUsername().equalsIgnoreCase(TakeHomeAssignmentServiceUtil.decodeString(req.getPassword()));

	}
}
