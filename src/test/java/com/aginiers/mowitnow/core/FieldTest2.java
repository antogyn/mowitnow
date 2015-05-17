package com.aginiers.mowitnow.core;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Other tests for the Field class.
 * 
 * @author aginiers
 *
 */
public class FieldTest2 {

  private Field field;
  private Mower mower1;
  private Mower mower2;

  @Before
  public void before() throws IllegalParameterException {
    field = new Field(5, 5);
    mower1 = new Mower(1, 1, 1, "N");
    mower2 = new Mower(2, 2, 2, "S");
    field.addMower(mower1);
    field.addMower(mower2);
  }

  @Test
  public void addMowers() throws IllegalParameterException {
    List<Mower> mowers = field.getMowers();
    assertEquals(mower1, mowers.get(0));
    assertEquals(mower2, mowers.get(1));
  }

  @Test
  public void copyMowers() throws IllegalParameterException {
    List<Mower> mowersCopy = field.getCopyOfMowers();
    Assert.assertNotSame(mower1, mowersCopy.get(0));
    Assert.assertNotSame(mower2, mowersCopy.get(1));
    assertEquals(mower1, mowersCopy.get(0));
    assertEquals(mower2, mowersCopy.get(1));
  }

  @Test
  public void moveLast() throws IllegalParameterException {
    field.moveLastMower(Command.FORWARD);
    field.moveLastMower(Command.RIGHT);

    List<Mower> mowersCopy = field.getCopyOfMowers();

    assertEquals(new Mower(1, 1, 1, "N"), mowersCopy.get(0));
    assertEquals(new Mower(2, 2, 1, "W"), mowersCopy.get(1));
  }

  @Test
  public void testToString() throws IllegalParameterException {
    assertEquals("1 1 N\n2 2 S", field.toString());
  }

}
