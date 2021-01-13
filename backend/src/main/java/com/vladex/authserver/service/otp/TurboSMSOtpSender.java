package com.vladex.authserver.service.otp;

import com.vladex.authserver.model.OtpToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@ConditionalOnProperty(name = "app.security.otp.sender", havingValue = "turbosms")
public class TurboSMSOtpSender implements OtpSender {

  @Override
  public void sendOtp(OtpToken otpToken) {
    log.info("Sending OTP {} to phone number: {}", otpToken.getOtp(), otpToken.getPhoneNumber());
  }
}
