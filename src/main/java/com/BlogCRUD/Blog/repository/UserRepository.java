package com.BlogCRUD.Blog.repository;

import com.BlogCRUD.Blog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}