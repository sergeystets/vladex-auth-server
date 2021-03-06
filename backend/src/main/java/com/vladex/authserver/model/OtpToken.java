package com.vladex.authserver.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class OtpToken {

  private final String otp;
  private final String phoneNumber;

}
