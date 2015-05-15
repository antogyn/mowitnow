package com.aginiers.mowitnow.core;

/**
 * An enum representing a command.
 * 
 * @author aginiers
 *
 */
public enum Command {

  FORWARD("A"),
  LEFT("G"),
  RIGHT("D");

  private String representation;

  private Command(String representation) {
    this.representation = representation;
  }

  /**
   * Finds a command by its representation.
   * 
   * @param representation the representation of the command to find
   * @return the command with this representation
   * @throws IllegalParameterException if no command exists for this representation
   */
  public static Command findByRepresentation(String representation)
      throws IllegalParameterException {
    Command[] commands = Command.values();
    for (Command command : commands) {
      if (command.representation.equals(representation)) {
        return command;
      }
    }
    throw new IllegalParameterException(representation, "a command");
  }

  @Override
  public String toString() {
    return this.representation;
  }
}
