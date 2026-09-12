package com.library.repository;

import com.library.model.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    private final Map<String, User> users = new HashMap<>();

    @Override
    public void save(User user) {
        users.put(user.getUserId(), user);
    }

    @Override
    public Optional<User> findById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public boolean deleteById(String userId) {
        return users.remove(userId) != null;
    }
}