package com.aginiers.mowitnow.servlet;

import com.google.gson.Gson;

/**
 * A simple class used to send errors in Ajax.
 * 
 * @author aginiers.
 *
 */
public class ErrorJson {

  private String error;
  
  public ErrorJson(String error) {
    this.error = error;
  }

  public String getError() {
    return error;
  }
  
  public String toJson() {
    return new Gson().toJson(this);
  }

}
