package com.amp.service;

import com.amp.domain.UserInfo;
import com.amp.request.LoginRequest;

public interface ICommonService {

    UserInfo login(LoginRequest request);

}
