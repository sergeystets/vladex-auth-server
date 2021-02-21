package com.vladex.authserver.service;

import com.vladex.authserver.entity.PendingVerificationEntity;
import com.vladex.authserver.entity.UserEntity;
import com.vladex.authserver.exception.ApiErrorException;
import com.vladex.authserver.model.OtpToken;
import com.vladex.authserver.model.SignInRequest;
import com.vladex.authserver.model.SignInResponse;
import com.vladex.authserver.repository.PendingVerificationRepository;
import com.vladex.authserver.repository.UserRepository;
import com.vladex.authserver.service.otp.OtpGenerator;
import com.vladex.authserver.service.otp.OtpSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Clock;
import java.time.LocalDateTime;

import static com.vladex.authserver.exception.ErrorCode.PHONE_NUMBER_IS_NOT_REGISTERED;

@Service
@RequiredArgsConstructor
public class SignInService {

  private final Clock clock;
  private final OtpSender otpSender;
  private final OtpGenerator otpGenerator;
  private final UserRepository userRepository;
  private final PendingVerificationRepository pendingVerificationRepository;

  @Value("${app.security.otp.expiration.seconds}")
  private long otpExpirationSeconds;

  @Transactional
  public SignInResponse signIn(SignInRequest signInRequest, boolean fromMobile) {
    final SignInResponse signInResponse = new SignInResponse();

    final String phoneNumber = signInRequest.getPhoneNumber();
    if (!userRepository.existsByPhoneNumber(phoneNumber)) {
      if (fromMobile) {
        // dynamically add a new user (and notify that he still needs to complete registration)
        signInResponse.setRequiresRegistration(true);
        userRepository.saveAndFlush(new UserEntity().setName("").setPhoneNumber(phoneNumber));
      } else {
        throw new ApiErrorException(PHONE_NUMBER_IS_NOT_REGISTERED);
      }
    }

    // delete old OTP codes (if any)
    pendingVerificationRepository.deleteByPhoneNumber(phoneNumber);
    pendingVerificationRepository.flush();

    // generate new OTP code
    final String otp = otpGenerator.generate();

    // save new OTP code
    final PendingVerificationEntity pendingVerification = new PendingVerificationEntity();
    pendingVerification.setExpiration(LocalDateTime.now(clock).plusSeconds(otpExpirationSeconds));
    pendingVerification.setOtp(otp);
    pendingVerification.setPhoneNumber(phoneNumber);
    pendingVerificationRepository.save(pendingVerification);

    // send OTP to a client
    otpSender.sendOtp(new OtpToken(otp, phoneNumber));

    signInResponse.setOtpExpirationSeconds(otpExpirationSeconds);
    return signInResponse;
  }
}
