package com.marionete.services.grpc;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.grpc.Server;
import io.grpc.ServerBuilder;

@Component
public class LoginServer {

	@Value("${grpc.server.port}")
	private int port;
	
	@Autowired
	LoginServiceImpl loginService;
	
	private Server server;

	public void start() throws IOException {
		server = ServerBuilder.forPort(port).addService(loginService).build().start();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					LoginServer.this.stop();
				} catch (InterruptedException e) {
					e.printStackTrace(System.err);
				}
				System.err.println("!!Server Terminating!!");
			}
		});
	}

	private void stop() throws InterruptedException {
		if (server != null) {
			server.shutdown();//.awaitTermination(30, TimeUnit.SECONDS);
		}
	}

	public void block() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
}
