package com.aginiers.mowitnow.core;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

/**
 * Tests for the Field class.
 * 
 * @author aginiers
 *
 */
public class FieldTest {

  @Test(expected = IllegalParameterException.class)
  public void negativeX() throws IllegalParameterException {
    new Field(-1, 1);
  }

  @Test(expected = IllegalParameterException.class)
  public void negativeY() throws IllegalParameterException {
    new Field(1, -1);
  }

  @Test(expected = IllegalParameterException.class)
  public void mowerOutOfFieldX() throws IllegalParameterException {
    new Field(1, 1).addMower(new Mower(1, 2, 1, "N"));
  }

  @Test(expected = IllegalParameterException.class)
  public void mowerOutOfFieldY() throws IllegalParameterException {
    new Field(1, 1).addMower(new Mower(1, 1, 2, "N"));
  }

  @Test
  public void addMowersNoException() throws IllegalParameterException {
    Field field = new Field(5, 5);
    Mower mower1 = new Mower(1, 1, 1, "N");
    Mower mower2 = new Mower(2, 2, 2, "S");

    field.addMower(mower1);
    field.addMower(mower2);

    List<Mower> mowers = field.getMowers();

    assertEquals(mower1, mowers.get(0));
    assertEquals(mower2, mowers.get(1));
  }
}
