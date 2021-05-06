package com.christian.employeedemoauthjpa.service;

import com.christian.employeedemoauthjpa.entity.User;
import com.christian.employeedemoauthjpa.user.CrmUser;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {
    public User findByUserName(String userName);

    public void save(CrmUser crmUser);
}
