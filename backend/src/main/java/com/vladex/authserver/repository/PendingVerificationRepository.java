package com.vladex.authserver.repository;

import com.vladex.authserver.entity.PendingVerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PendingVerificationRepository extends JpaRepository<PendingVerificationEntity, Long> {

  void deleteByPhoneNumber(String phoneNumber);

  PendingVerificationEntity findOneByOtp(String otp);

  void deleteByOtp(String otp);

  PendingVerificationEntity findOneByPhoneNumber(String userPhone);
}
