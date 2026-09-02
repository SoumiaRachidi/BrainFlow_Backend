package com.brainflow.brainflow.repository;

import com.brainflow.brainflow.entity.SystemRole;
import com.brainflow.brainflow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    List<User> findBySystemRole(SystemRole systemRole);

    List<User> findBySystemRoleAndApproved(SystemRole systemRole, boolean approved);
}
