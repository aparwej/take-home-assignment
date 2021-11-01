package com.marionete.services.repository;

import com.marionete.services.model.AccountInfo;

public interface AccountInfoRepository {

	public AccountInfo getAccountInfo(String loginToken);
	
}
