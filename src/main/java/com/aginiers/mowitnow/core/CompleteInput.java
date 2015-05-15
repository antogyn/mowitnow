package com.aginiers.mowitnow.core;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Contains the field and the mowers with their commands.
 * <p>
 * Corresponds to an already parsed input.
 * 
 * @author aginiers
 *
 */
public class CompleteInput {

  private Field field;
  private List<Pair<Mower, List<Command>>> mowersAndCommands;

  public CompleteInput(Field field, List<Pair<Mower, List<Command>>> mowersAndCommands) {
    this.field = field;
    this.mowersAndCommands = mowersAndCommands;
  }

  public Field getField() {
    return field;
  }

  public List<Pair<Mower, List<Command>>> getMowersAndCommands() {
    return mowersAndCommands;
  }

}
