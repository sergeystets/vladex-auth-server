package com.vladex.authserver.repository;

import com.vladex.authserver.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  boolean existsByPhoneNumber(String phoneNumber);

  UserEntity findOneByPhoneNumber(String phoneNumber);
}
