package com.BlogCRUD.Blog.service;

import com.BlogCRUD.Blog.dto.UserRegistrationDto;
import com.BlogCRUD.Blog.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
}
