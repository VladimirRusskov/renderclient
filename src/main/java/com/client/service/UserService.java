package com.client.service;

import com.client.service.dto.UserDTO;

public interface UserService {
    UserDTO auth(String email, String password);

    UserDTO registration(String email, String password);
}
