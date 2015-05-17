package com.aginiers.mowitnow.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aginiers.mowitnow.core.IllegalParameterException;
import com.aginiers.mowitnow.core.Solution;
import com.aginiers.mowitnow.core.SolutionComputer;
import com.aginiers.mowitnow.core.parser.InputParserException;


/**
 * The entry point of the application.
 * <p>
 * Expects a post request with a parameter "input".
 * 
 * @author aginiers
 *
 */
@WebServlet("/Mow")
public class MowServlet extends HttpServlet {

  private static final long serialVersionUID = -1195547733871963335L;

  public MowServlet() {}

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {}

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    try {
      String input = request.getParameter("input");
      Solution solution = SolutionComputer.getSolution(input);
      response.getWriter()
              .write(solution.toJson());
    } catch (InputParserException e) {
      e.printStackTrace();
      response.getWriter()
              .write(new ErrorJson(e.getMessage()).toJson());
    } catch (IllegalParameterException e) {
      e.printStackTrace();
      response.getWriter()
              .write(new ErrorJson(e.getMessage()).toJson());
    }
  }

}
