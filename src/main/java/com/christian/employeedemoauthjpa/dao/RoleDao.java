package com.christian.employeedemoauthjpa.dao;

import com.christian.employeedemoauthjpa.entity.Role;

public interface RoleDao {
    public Role findRoleByName(String theRoleName);
}
