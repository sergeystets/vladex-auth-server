package com.vladex.authserver.service.otp;


import com.vladex.authserver.model.OtpToken;

public interface OtpSender {

  void sendOtp(OtpToken otpToken);

}
