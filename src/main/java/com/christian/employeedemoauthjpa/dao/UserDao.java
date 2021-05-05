package com.christian.employeedemoauthjpa.dao;

import com.christian.employeedemoauthjpa.entity.User;

public interface UserDao {
    public User findByUserName(String userName);
    public void save(User user);
}
