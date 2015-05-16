package com.aginiers.mowitnow.servlet;

public class ErrorJson {

  private String error;
  
  public ErrorJson(String error) {
    this.error = error;
  }

  public String getError() {
    return error;
  }

}
