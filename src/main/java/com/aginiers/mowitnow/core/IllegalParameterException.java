package com.aginiers.mowitnow.core;

/**
 * 
 * @author aginiers
 *
 */
public class IllegalParameterException extends Exception {

  private static final long serialVersionUID = 2978961698664784338L;

  public IllegalParameterException(Object input, String expected) {
    super(IllegalParameterException.getMessage(input, expected));
  }

  private static String getMessage(Object input, String expected) {
    StringBuilder builder = new StringBuilder();

    builder.append("Illegal parameter : expected ")
           .append(expected == null ? "null" : expected)
           .append(" ; got ")
           .append(input == null ? "null" : input);

    return builder.toString();
  }

}
