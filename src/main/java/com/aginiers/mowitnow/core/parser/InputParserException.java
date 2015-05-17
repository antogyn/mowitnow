package com.aginiers.mowitnow.core.parser;

import java.util.regex.Pattern;

/**
 * An exception thrown when the format of the input does not match the patterns defined in the parser.
 * 
 * @author aginiers
 *
 */
public class InputParserException extends Exception {

  private static final long serialVersionUID = -5086622324557612970L;

  public InputParserException(String input, Pattern pattern) {
    super(InputParserException.getMessage(input, pattern));
  }

  private static String getMessage(String input, Pattern pattern) {
    StringBuilder builder = new StringBuilder();

    builder.append("An error occured during the parsing of the input data : ")
           .append("expected input to match pattern ")
           .append(pattern == null ? "null" : pattern)
           .append(" ; got ")
           .append(input == null ? "null" : input)
           .append(" instead.");

    return builder.toString();
  }

}
