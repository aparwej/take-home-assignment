package com.marionete.services.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marionete.services.model.UserAccount;
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
		userAccount.setAccountInfo(accountInfoRepository.getAccountInfo(loginToken));
		userAccount.setUserInfo(userAccountRepository.getUserAccount(loginToken));
		return userAccount;
	}

}
