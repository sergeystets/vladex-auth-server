package com.vladex.authserver.controller;

import com.vladex.authserver.model.SignInResponse;
import com.vladex.authserver.model.SignInRequest;
import com.vladex.authserver.service.SignInService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("sign-in")
public class SignInController {

  private final SignInService signInService;

  @PostMapping
  public SignInResponse signIn(@RequestBody @Valid SignInRequest signInRequest) {
    return signInService.signIn(signInRequest);
  }
}
