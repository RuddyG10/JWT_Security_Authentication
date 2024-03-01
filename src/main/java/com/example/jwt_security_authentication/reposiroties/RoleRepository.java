package com.example.jwt_security_authentication.reposiroties;

import com.example.jwt_security_authentication.models.Role;
import com.example.jwt_security_authentication.models.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByRoleName(RoleName roleName);
}
