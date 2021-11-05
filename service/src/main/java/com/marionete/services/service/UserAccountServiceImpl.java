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

	@Override
	public UserAccount getUserAccount(String loginToken) {

		UserAccount userAccount = new UserAccount();

		CompletableFuture<AccountInfo> accountInfoFuture = CompletableFuture.supplyAsync(()->{
			return accountInfoRepository.getAccountInfo(loginToken);
		});
		
		CompletableFuture<UserInfo> userAccountFuture = CompletableFuture.supplyAsync(()->{
			return userAccountRepository.getUserAccount(loginToken);
		});

		CompletableFuture<Void> allFutures = CompletableFuture.allOf(accountInfoFuture,userAccountFuture);
		try {
			userAccount.setAccountInfo(accountInfoFuture.get());
			userAccount.setUserInfo(userAccountFuture.get());
		} catch (InterruptedException|ExecutionException e) {
			System.out.println("Failed to Complete service request");
			e.printStackTrace(System.err);
		} 
		return userAccount;
	}

}
