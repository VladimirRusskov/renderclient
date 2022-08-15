package com.client.converter;

import com.client.json.User;
import com.client.service.dto.UserDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDtoConverter implements Converter<User, UserDTO> {
    @Override
    public UserDTO convert(User user) {
        return new UserDTO().setId(user.getId()).setEmail(user.getEmail());
    }
}
