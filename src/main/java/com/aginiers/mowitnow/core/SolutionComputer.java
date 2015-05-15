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

    solution.setWidth(field.getWidth());
    solution.setHeight(field.getHeight());

    solution.addStep(field.getCopyOfMowers());

    for (Pair<Mower, List<Command>> mowerAndCommands : completeInput.getMowersAndCommands()) {
      Mower mower = mowerAndCommands.getLeft();

      field.addMower(mower);
      solution.addStep(field.getCopyOfMowers());

      List<Command> commands = mowerAndCommands.getRight();
      for (Command command : commands) {
        field.moveLastMower(command);
        solution.addStep(field.getCopyOfMowers());
      }
    }

    solution.setFinalSolution(field.toString());

    return solution;
  }

}
