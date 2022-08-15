package com.client.service;

import com.client.converter.UserToUserDtoConverter;
import com.client.json.User;
import com.client.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static com.client.service.constant.Path.AUTHORIZATION;
import static com.client.service.constant.Path.REGISTRATION;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RestTemplate restTemplate;
    private final HttpHeaders headers;
    private final UserToUserDtoConverter converter;


    private UserDTO getUserDTO(String email, String password, String uri) {
        User user = new User().setEmail(email).setPassword(password);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<User> userResponseEntity = restTemplate.postForEntity(uri, request, User.class);
        return userResponseEntity.hasBody() ? converter.convert(Objects.requireNonNull(userResponseEntity.getBody())) : null;
    }

    @Override
    public UserDTO auth(String email, String password) {
        return getUserDTO(email, password, AUTHORIZATION);
    }

    @Override
    public UserDTO registration(String email, String password) {
        return getUserDTO(email, password, REGISTRATION);
    }
}
