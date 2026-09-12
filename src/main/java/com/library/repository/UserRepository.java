package com.library.repository;

import com.library.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void save(User user);

    Optional<User> findById(String userId);

    List<User> findAll();

    boolean deleteById(String userId);
}