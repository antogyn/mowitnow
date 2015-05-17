package com.aginiers.mowitnow.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * Represents a field.
 * <p>
 * A field has a list of mowers and a certain width and height.
 * 
 * @author aginiers
 *
 */
public class Field {

  private List<Mower> mowers;
  private int width;
  private int height;

  public Field(int width, int height) throws IllegalParameterException {
    if (width < 0) {
      throw new IllegalParameterException(width, "a positive Integer");
    } else if (height < 0) {
      throw new IllegalParameterException(height, "a positive Integer");
    }

    this.mowers = new ArrayList<Mower>();
    this.width = width;
    this.height = height;
  }

  /**
   * Appends a mower to the end of the list of mowers.
   * 
   * @param mower the mower to add
   * @throws IllegalParameterException if the coordinates of the mower are impossible
   */
  public void addMower(Mower mower) throws IllegalParameterException {
    int x = mower.getCoordinate()
                 .getX();
    int y = mower.getCoordinate()
                 .getY();

    if (x > width) {
      throw new IllegalParameterException(x, "a value below the width of the field");
    }

    if (y > height) {
      throw new IllegalParameterException(y, "a value below the height of the field");
    }

    this.mowers.add(mower);
  }

  /**
   * Moves the last mower of the list of mowers.
   * @param command the command to dispatch
   * @throws IllegalParameterException
   */
  public void moveLastMower(Command command) throws IllegalParameterException {
    mowers.get(mowers.size() - 1)
          .move(command, width, height);
  }

  /**
   * Returns a deep copy of the mowers on this field.
   * 
   * @return the copy
   * @throws IllegalParameterException
   */
  public List<Mower> getCopyOfMowers() throws IllegalParameterException {
    List<Mower> mowers = new ArrayList<Mower>();
    for (Mower mower : this.mowers) {
      mowers.add(new Mower(mower));
    }
    return mowers;
  }
  
  List<Mower> getMowers() {
    return mowers;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  @Override
  public String toString() {
    return StringUtils.join(mowers, "\n");
  }

}
