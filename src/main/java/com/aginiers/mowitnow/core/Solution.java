package com.aginiers.mowitnow.core;

import java.util.ArrayList;
import java.util.List;

/**
 * A POJO containing the final solution.
 * <p>
 * It also stores a list of mowers for each step of the computation.
 * <p>
 * A new step corresponds to :
 * <ul>
 * <li>The initialization of the field (empty list)</li>
 * <li>The addition of a mower</li>
 * <li>The dispatch of a command to a mower</li>
 * </ul>
 * 
 * @author aginiers
 *
 */
public class Solution {

  private int width;
  private int height;
  private List<List<Mower>> mowersAtStep;
  private String finalSolution;
  
  public Solution() {
    this.mowersAtStep = new ArrayList<List<Mower>>();
  };

  public Solution(int width, int height, List<List<Mower>> mowersAtStep, String finalSolution) {
    this.width = width;
    this.height = height;
    this.mowersAtStep = mowersAtStep;
    this.finalSolution = finalSolution;
  }
  
  public void addStep(List<Mower> mowers) {
    this.mowersAtStep.add(mowers);
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public List<List<Mower>> getMowersAtStep() {
    return mowersAtStep;
  }

  public void setMowersAtStep(List<List<Mower>> mowersAtStep) {
    this.mowersAtStep = mowersAtStep;
  }

  public String getFinalSolution() {
    return finalSolution;
  }

  public void setFinalSolution(String finalSolution) {
    this.finalSolution = finalSolution;
  }

}
