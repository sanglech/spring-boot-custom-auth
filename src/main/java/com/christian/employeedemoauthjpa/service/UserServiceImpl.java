package com.christian.employeedemoauthjpa.service;

import com.christian.employeedemoauthjpa.dao.RoleDao;
import com.christian.employeedemoauthjpa.dao.UserDao;
import com.christian.employeedemoauthjpa.entity.Role;
import com.christian.employeedemoauthjpa.entity.User;
import com.christian.employeedemoauthjpa.user.CrmUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    // need to inject user dao
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public User findByUserName(String userName) {
        return userDao.findByUserName(userName);
    }

    @Override
    @Transactional
    public void save(CrmUser crmUser) {
        User tempUser= new User();
        tempUser.setEmail(crmUser.getEmail());
        tempUser.setUserName(crmUser.getUserName());
        tempUser.setFirstName(crmUser.getFirstName());
        tempUser.setPassword(passwordEncoder.encode(crmUser.getPassword()));
        tempUser.setLastName(crmUser.getLastName());

        //Add roles


        userDao.save(tempUser);
    }

    //THIS METHOD MUST BE IMPLEMENTED WHEN USING CUSTOM TABLES
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userDao.findByUserName(userName);
        user.getRoles().size();
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}
