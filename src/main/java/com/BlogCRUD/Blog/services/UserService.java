package com.BlogCRUD.Blog.services;

import com.BlogCRUD.Blog.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
}
