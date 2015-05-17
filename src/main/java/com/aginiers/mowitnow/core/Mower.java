package com.aginiers.mowitnow.core;

/**
 * Represents a lawn-mower.
 * <p>
 * A lawn-mower has a direction and an (x,y) coordinate, and can move with commands.
 * 
 * @author aginiers
 *
 */
public class Mower {

  private int id;
  private XYCoordinate coordinate;
  private Direction direction;

  public Mower(int id, int x, int y, String directionRepresentation) throws IllegalParameterException {
    if (x < 0) {
      throw new IllegalParameterException(x, "a positive Integer");
    } else if (y < 0) {
      throw new IllegalParameterException(y, "a positive Integer");
    }
    this.id = id;
    this.coordinate = new XYCoordinate(x, y);
    this.direction = Direction.findByRepresentation(directionRepresentation);
  }
  
  public Mower(Mower mower) {
    this.setId(mower.getId());
    this.coordinate = new XYCoordinate(mower.getCoordinate());
    this.direction = mower.getDirection();
  }

  /**
   * Moves the mower on the field, depending on the command.
   * 
   * @param command the command, to go forward or to turn
   * @param maxX the width of the field
   * @param maxY the height of the field
   * @throws IllegalParameterException
   */
  public void move(Command command, int maxX, int maxY) throws IllegalParameterException {
    if (Command.FORWARD.equals(command)) {
      // 0 <= X|Y <= maxX|maxY
      coordinate.setX(Math.max(Math.min(coordinate.getX() + direction.getX(), maxX), 0));
      coordinate.setY(Math.max(Math.min(coordinate.getY() + direction.getY(), maxY), 0));
    } else {
      direction = direction.turn(command);
    }
  }
  
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public XYCoordinate getCoordinate() {
    return coordinate;
  }

  public Direction getDirection() {
    return direction;
  }

  @Override
  public String toString() {
    return coordinate + " " + direction;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((coordinate == null) ? 0 : coordinate.hashCode());
    result = prime * result + ((direction == null) ? 0 : direction.hashCode());
    result = prime * result + id;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Mower other = (Mower) obj;
    if (coordinate == null) {
      if (other.coordinate != null)
        return false;
    } else if (!coordinate.equals(other.coordinate))
      return false;
    if (direction != other.direction)
      return false;
    if (id != other.id)
      return false;
    return true;
  }
  
}
