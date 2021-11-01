package com.marionete.services.grpc;

import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import services.LoginRequest;
import services.LoginResponse;
import services.LoginServiceGrpc;

public class LoginClient {

    private String host;
    private Integer port;
    
    private ManagedChannel channel;
	private LoginServiceGrpc.LoginServiceBlockingStub blockingStub;

	public LoginClient(String host, Integer port) {
		this.host = host;
		this.port=port;
		channel = ManagedChannelBuilder.forTarget(getTarget()).usePlaintext().build();
        blockingStub = LoginServiceGrpc.newBlockingStub(channel);
	}

	public LoginResponse login(LoginRequest loginRequest) {

		LoginResponse response = LoginResponse.getDefaultInstance();
		try {
			response = blockingStub.login(loginRequest);
		} catch (StatusRuntimeException e) {
			return response;
		}
		return response;
	}
	
	
    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
    }

    public LoginServiceGrpc.LoginServiceBlockingStub getStub() {
        return this.blockingStub;
    }
    
    private String getTarget() {
    	return host.concat(":").concat(String.valueOf(port));
    }

}
