package com.aginiers.mowitnow.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests for the Mower class.
 * 
 * @author aginiers
 *
 */
public class MowerTest {

  @Test(expected = IllegalParameterException.class)
  public void negativeX() throws IllegalParameterException {
    new Mower(1, -1, 0, "N");
  }

  @Test(expected = IllegalParameterException.class)
  public void negativeY() throws IllegalParameterException {
    new Mower(1, 0, -1, "N");
  }

  @Test(expected = IllegalParameterException.class)
  public void impossibleDirection() throws IllegalParameterException {
    new Mower(1, 0, 0, "M");
  }

  @Test
  public void turn() throws IllegalParameterException {
    Mower mower = new Mower(1, 0, 0, "N");

    mower.move(Command.LEFT, 5, 5);
    assertEquals(Direction.WEST, mower.getDirection());
    mower.move(Command.LEFT, 5, 5);
    assertEquals(Direction.SOUTH, mower.getDirection());
    mower.move(Command.LEFT, 5, 5);
    assertEquals(Direction.EAST, mower.getDirection());
    mower.move(Command.LEFT, 5, 5);
    assertEquals(Direction.NORTH, mower.getDirection());

    mower.move(Command.RIGHT, 5, 5);
    assertEquals(Direction.EAST, mower.getDirection());
    mower.move(Command.RIGHT, 5, 5);
    assertEquals(Direction.SOUTH, mower.getDirection());
    mower.move(Command.RIGHT, 5, 5);
    assertEquals(Direction.WEST, mower.getDirection());
    mower.move(Command.RIGHT, 5, 5);
    assertEquals(Direction.NORTH, mower.getDirection());
  }

  @Test
  public void forward() throws IllegalParameterException {
    Mower mower;

    mower = new Mower(1, 0, 0, "N");
    mower.move(Command.FORWARD, 1, 1);
    assertEquals(Direction.NORTH, mower.getDirection());
    assertEquals(new XYCoordinate(0, 1), mower.getCoordinate());

    mower = new Mower(1, 0, 0, "E");
    mower.move(Command.FORWARD, 1, 1);
    assertEquals(Direction.EAST, mower.getDirection());
    assertEquals(new XYCoordinate(1, 0), mower.getCoordinate());

    mower = new Mower(1, 1, 1, "S");
    mower.move(Command.FORWARD, 1, 1);
    assertEquals(Direction.SOUTH, mower.getDirection());
    assertEquals(new XYCoordinate(1, 0), mower.getCoordinate());

    mower = new Mower(1, 1, 1, "W");
    mower.move(Command.FORWARD, 1, 1);
    assertEquals(Direction.WEST, mower.getDirection());
    assertEquals(new XYCoordinate(0, 1), mower.getCoordinate());
  }

  @Test
  public void forwardBelowZero() throws IllegalParameterException {
    Mower mower = new Mower(1, 0, 0, "W");
    mower.move(Command.FORWARD, 1, 1);
    assertEquals(Direction.WEST, mower.getDirection());
    assertEquals(new XYCoordinate(0, 0), mower.getCoordinate());
  }

  @Test
  public void forwardAboveMaximum() throws IllegalParameterException {
    Mower mower = new Mower(1, 4, 4, "E");
    mower.move(Command.FORWARD, 4, 4);
    assertEquals(Direction.EAST, mower.getDirection());
    assertEquals(new XYCoordinate(4, 4), mower.getCoordinate());
  }

  @Test
  public void mowerToString() throws IllegalParameterException {
    Mower mower = new Mower(1, 1, 2, "N");
    assertEquals("1 2 N", mower.toString());
  }

}
