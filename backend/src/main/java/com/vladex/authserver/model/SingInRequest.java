package com.vladex.authserver.model;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SingInRequest {

  @NotNull
  private String phoneNumber;

}
