package com.BlogCRUD.Blog.services;

import com.BlogCRUD.Blog.dto.UserRegistrationDto;
import com.BlogCRUD.Blog.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
}
