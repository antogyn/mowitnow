package com.aginiers.mowitnow.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.aginiers.mowitnow.core.parser.InputParserException;

/**
 * Tests for the whole solution.
 * 
 * Depends on the rest of the classes.
 * 
 * @author aginiers
 *
 */
public class SolutionComputerTest {

  @Test
  public void exampleInInstructions() throws IllegalParameterException, InputParserException {
    StringBuilder inputString = new StringBuilder();
    inputString.append("5 5")
               .append("\n")
               .append("1 2 N")
               .append("\n")
               .append("GAGAGAGAA")
               .append("\n")
               .append("3 3 E")
               .append("\n")
               .append("AADAADADDA");

    Solution solution = SolutionComputer.getSolution(inputString.toString());

    String expectedOutput = "1 3 N\n5 1 E";
    String output = solution.getFinalSolution();

    assertEquals(expectedOutput, output);
  }
  
  @Test
  public void inputWithoutCommands() throws IllegalParameterException, InputParserException {
    StringBuilder inputString = new StringBuilder();
    inputString.append("5 5")
               .append("\n")
               .append("1 2 N")
               .append("\n")
               .append("3 3 E");

    Solution solution = SolutionComputer.getSolution(inputString.toString());

    String expectedOutput = "1 2 N\n3 3 E";
    String output = solution.getFinalSolution();

    assertEquals(expectedOutput, output);
  }
  
  
  
}
