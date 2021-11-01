package com.marionete.services.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.marionete.services.grpc.LoginServer;

@Component
public class LoginGrpcCommandLineRunner implements CommandLineRunner{
	@Autowired
	LoginServer loginServer;

	@Override
    public void run(String... args) throws Exception {
    	loginServer.start();
    	//loginServer.block();
    }
}
