package com.aginiers.mowitnow.core.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.apache.commons.lang3.tuple.Pair;

import com.aginiers.mowitnow.core.Command;
import com.aginiers.mowitnow.core.CompleteInput;
import com.aginiers.mowitnow.core.Field;
import com.aginiers.mowitnow.core.IllegalParameterException;
import com.aginiers.mowitnow.core.Mower;

/**
 * Static class providing a method to parse the input given as a Scanner.
 * 
 * @author aginiers
 *
 */
public final class InputParser {

  private static final Pattern REGEX_DIRECTION = Pattern.compile("[NESW]");
  private static final Pattern REGEX_COMMANDS = Pattern.compile("[AGD]+");
  private static final Pattern REGEX_INTEGER = Pattern.compile("[0-9]+");

  private InputParser() {}

  /**
   * Parses the input.
   * 
   * @param input the input given as a Scanner
   * @return the field and a list of tuples, each containing a lawn-mower to be placed and its
   *         commands
   * @throws InputParserException if the given input is incorrect
   * @throws IllegalParameterException if the parser accepted a value even though it was incorrect
   */
  public static CompleteInput parseInput(Scanner input) throws InputParserException,
      IllegalParameterException {

    // parses the field
    int width = Integer.valueOf(safeNext(input, REGEX_INTEGER));
    int height = Integer.valueOf(safeNext(input, REGEX_INTEGER));
    Field field = new Field(width, height);

    List<Pair<Mower, List<Command>>> mowersAndCommands =
        new ArrayList<Pair<Mower, List<Command>>>();
    // iterates through the list of mowers and their commands
    int id = 1;
    while (input.hasNext()) {
      // the mower
      int x = Integer.valueOf(safeNext(input, REGEX_INTEGER));
      int y = Integer.valueOf(safeNext(input, REGEX_INTEGER));
      String directionRepresentation = safeNext(input, REGEX_DIRECTION);
      Mower mower = new Mower(id, x, y, directionRepresentation);
      id++;
      // the commands
      List<Command> commands = new ArrayList<Command>();
      if (input.hasNext(REGEX_COMMANDS)) {
        String commandRepresentations = safeNext(input, REGEX_COMMANDS);
        for (int i = 0; i < commandRepresentations.length(); i++) {
          String commandRepresentation = Character.toString(commandRepresentations.charAt(i));
          commands.add(Command.findByRepresentation(commandRepresentation));
        }
      }
      mowersAndCommands.add(Pair.of(mower, commands));
    }

    return new CompleteInput(field, mowersAndCommands);
  }

  /**
   * Returns the next token of a scanner if it matches the given pattern.
   * <p>
   * Throws an InputParserException otherwise.
   * 
   * @param scanner the scanner
   * @param pattern the pattern to scan for
   * @return the next token of the scanner
   * @throws InputParserException if the next token does not match the pattern
   */
  private static String safeNext(Scanner scanner, Pattern pattern) throws InputParserException {
    if (scanner.hasNext(pattern)) {
      return scanner.next();
    } else {
      throw new InputParserException(scanner.hasNext() ? scanner.next() : "nothing", pattern);
    }
  }

}
