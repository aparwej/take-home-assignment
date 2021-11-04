package com.marionete.services.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marionete.services.model.AccountInfo;
import com.marionete.services.model.UserAccount;
import com.marionete.services.model.UserInfo;
import com.marionete.services.repository.AccountInfoRepository;
import com.marionete.services.repository.UserAccountRepository;

@Service
public class UserAccountServiceImpl implements UserAccountService {

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Autowired
	private AccountInfoRepository accountInfoRepository;
	
	@Autowired
	Executor serviceExecutor;

	@Override
	public UserAccount getUserAccount(String loginToken) {

		UserAccount userAccount = new UserAccount();
		
		ExecutorService serviceExecutor= Executors.newFixedThreadPool(2);
		
		CompletableFuture<AccountInfo> accountInfoFuture = CompletableFuture.supplyAsync(()->{
			//userAccount.setAccountInfo(accountInfoRepository.getAccountInfo(loginToken));
			return accountInfoRepository.getAccountInfo(loginToken);
		});
		
		CompletableFuture<UserInfo> userAccountFuture = CompletableFuture.supplyAsync(()->{
			return userAccountRepository.getUserAccount(loginToken);
		});
		
		//CompletableFuture<Void> allFuture = CompletableFuture.allOf(accountInfoFuture,userAccountFuture);
		//serviceExecutor.submit(accountInfoFuture);
		try {
			serviceExecutor.shutdown();
			userAccount.setAccountInfo(accountInfoFuture.get());
			userAccount.setUserInfo(userAccountFuture.get());
			//allFuture.get();
			serviceExecutor.awaitTermination(30, TimeUnit.SECONDS);
		} catch (InterruptedException|ExecutionException e) {
			System.out.println("Failed to Complete service request");
			e.printStackTrace(System.err);
		} 
		return userAccount;
	}

}
