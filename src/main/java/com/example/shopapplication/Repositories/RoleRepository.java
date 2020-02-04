package com.example.shopapplication.Repositories;

import com.example.shopapplication.Model.ERole;
import com.example.shopapplication.Model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(ERole role);
}
