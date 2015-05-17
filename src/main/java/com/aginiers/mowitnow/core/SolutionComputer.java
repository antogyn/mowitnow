package com.aginiers.mowitnow.core;

import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.tuple.Pair;

import com.aginiers.mowitnow.core.parser.InputParser;
import com.aginiers.mowitnow.core.parser.InputParserException;

/**
 * A static class providing a method to compute the final solution from the input.
 * 
 * @author aginiers
 *
 */
public final class SolutionComputer {

  private SolutionComputer() {}

  /**
   * Returns the solution from the input.
   * 
   * @param inputString the input as a String
   * @return
   * @throws InputParserException
   * @throws IllegalParameterException
   * @see com.aginiers.mowitnow.core.Solution
   */
  public static Solution getSolution(String inputString) throws InputParserException,
      IllegalParameterException {
    
    Solution solution = new Solution();
    Scanner inputScanner = new Scanner(inputString);
    CompleteInput completeInput = InputParser.parseInput(inputScanner);
    Field field = completeInput.getField();
    
    addField(completeInput, solution, field);
    addMowers(completeInput, solution, field);

    solution.setFinalSolution(field.toString());

    return solution;
  }
  
  private static void addField(CompleteInput input, Solution solution, Field field) throws IllegalParameterException {
    solution.setWidth(field.getWidth());
    solution.setHeight(field.getHeight());
    solution.addStep(field.getCopyOfMowers());
  }
  
  private static void addMowers(CompleteInput input, Solution solution, Field field) throws IllegalParameterException {
    for (Pair<Mower, List<Command>> mowerAndCommands : input.getMowersAndCommands()) {
      Mower mower = mowerAndCommands.getLeft();

      field.addMower(mower);
      solution.addStep(field.getCopyOfMowers());

      List<Command> commands = mowerAndCommands.getRight();
      dispatchCommands(commands, solution, field);
    }
  }
  
  private static void dispatchCommands(List<Command> commands, Solution solution, Field field) throws IllegalParameterException {
    for (Command command : commands) {
      field.moveLastMower(command);
      solution.addStep(field.getCopyOfMowers());
    }
  }
  

}
