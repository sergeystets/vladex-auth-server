package com.vladex.authserver.config;


import com.vladex.authserver.repository.PendingVerificationRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationFailureCredentialsExpiredEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class AuthenticationEventHandler {

  private final PendingVerificationRepository pendingVerificationRepository;

  @EventListener(AuthenticationSuccessEvent.class)
  @Transactional
  public void processAuthenticationSuccessEvent(AbstractAuthenticationEvent e) {
    if (e.getSource() instanceof UsernamePasswordAuthenticationToken) {
      final String phoneNumber = e.getAuthentication().getName();
      log.info("Authentication success for user {}", phoneNumber);
      deleteOtp(phoneNumber);
    }
  }

  @EventListener(AuthenticationFailureCredentialsExpiredEvent.class)
  @Transactional
  public void processExpiredCredentialsEvent(AbstractAuthenticationEvent e) {
    if (e.getSource() instanceof UsernamePasswordAuthenticationToken) {
      final String phoneNumber = e.getAuthentication().getName();
      log.info("OTP expired for user {}", phoneNumber);
      deleteOtp(phoneNumber);
    }
  }

  private void deleteOtp(String phoneNumber) {
    log.info("Deleting OTP for user {}", phoneNumber);
    pendingVerificationRepository.deleteByPhoneNumber(phoneNumber);
  }
}

