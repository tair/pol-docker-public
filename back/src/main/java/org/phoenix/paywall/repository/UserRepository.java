package org.phoenix.paywall.repository;

import org.phoenix.paywall.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);
    User findByEmail(String email);

    User findByEmailAndPasswordResetToken(String email, String passwordResetToken);
    
    Integer deleteByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
