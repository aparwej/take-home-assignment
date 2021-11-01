package com.marionete.services.repository;

import com.marionete.services.model.UserInfo;

public interface UserAccountRepository {

	public UserInfo getUserAccount(String loginToken);
}
