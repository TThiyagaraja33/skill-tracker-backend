package com.cts.skilltracker.persist.service;

import com.cts.skilltracker.persist.apimodel.UserRequest;

public interface UserService {

    public void createUser(UserRequest request);

    public void updateUser(String id, UserRequest request);
}
