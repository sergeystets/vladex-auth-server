package com.vladex.authserver.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ApiErrorException extends RuntimeException {

  private final ErrorCode errorCode;

}
