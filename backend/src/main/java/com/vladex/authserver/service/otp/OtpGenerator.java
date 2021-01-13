package com.vladex.authserver.service.otp;

import org.springframework.stereotype.Service;

@Service
public class OtpGenerator {

  public String generate() {
    return "123ABC78";
  }
}
