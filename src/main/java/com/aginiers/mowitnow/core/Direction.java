package com.aginiers.mowitnow.core;

/**
 * An enum representing a direction.
 * <p>
 * A direction knows the direction on its left and on its right.
 * 
 * @author aginiers
 *
 */
public enum Direction {

  NORTH("N", "WEST", "EAST", 0, 1),
  EAST("E", "NORTH", "SOUTH", 1, 0),
  SOUTH("S", "EAST", "WEST", 0, -1),
  WEST("W", "SOUTH", "NORTH", -1, 0);

  private String representation;
  private String directionLeft;
  private String directionRight;
  private int x;
  private int y;

  private Direction(String representation, String directionLeft, String directionRight, int x, int y) {
    this.representation = representation;
    this.directionLeft = directionLeft;
    this.directionRight = directionRight;
    this.x = x;
    this.y = y;
  }

  /**
   * Finds a direction by its representation.
   * 
   * @param representation the representation of the direction to find
   * @return the direction with this representation
   * @throws IllegalParameterException if no direction exists for this representation
   */
  public static Direction findByRepresentation(String representation)
      throws IllegalParameterException {
    Direction[] directions = Direction.values();
    for (Direction direction : directions) {
      if (direction.representation.equals(representation)) {
        return direction;
      }
    }
    throw new IllegalParameterException(representation, "a direction");
  }

  /**
   * Returns a new direction by turning left or right.
   * 
   * @param command the command to turn left or right
   * @return the new direction
   * @throws IllegalParameterException if the command is not a turn command
   */
  public Direction turn(Command command) throws IllegalParameterException {
    if (Command.LEFT.equals(command)) {
      return Direction.valueOf(this.directionLeft);
    } else if (Command.RIGHT.equals(command)) {
      return Direction.valueOf(this.directionRight);
    } else {
      throw new IllegalParameterException(command, "a command to go left or right");
    }
  }
  
  public String getRepresentation() {
    return representation;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  @Override
  public String toString() {
    return this.representation;
  }

}
