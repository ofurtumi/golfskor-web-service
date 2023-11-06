package is.hi.hugbo.interfaces;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import is.hi.hugbo.model.Holes;
import is.hi.hugbo.model.Course;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public interface IGameController {
  /**
   * GET method for all courses
   *
   * @param model   - The model, will include courses and username (if user is
   *                logged in)
   * @param session - The session object
   * @return courses.html
   */
  public String getCourses(Model model, HttpSession session);

  /**
   * Get method for round score form
   *
   * @param holes    - An empty Holes object
   * @param model    - The model, will include holes and course_id
   * @param session  - The session object
   * @param courseId - The id of the course the round was played on
   * @return round.html
   */
  public String getRoundForm(
      @ModelAttribute Holes holes,
      @ModelAttribute Course course,
      Model model,
      HttpSession session,
      @PathVariable("id") long courseId,
      @RequestParam(name = "holes", required = false) Integer numHoles);

  /**
   * Post method for round score form
   *
   * @param holes    - The holes object with the scores
   * @param model    - The model
   * @param session  - The session object
   * @param courseId - The id of the course the round was played on
   * @return redirect:/courses or if user not logged redirect:/login
   */
  public String postRoundForm(
      @ModelAttribute Holes holes,
      Model model,
      HttpSession session,
      @PathVariable("id") long courseId);

  /**
   * Get method to delete a round
   * 
   * @param session - The session object
   * @param roundId - The id of the round to delete
   * @return redirect:/
   */
  public String deleteRound(
      HttpSession session,
      @PathVariable("id") long roundId);

  /**
   * Get method to update a round
   * 
   * @param session - The session object
   * @param model   - The model
   * @param holes   - The hole object with the score
   * @param roundId - The id of the round to update
   * @param request - A request on the HTTP service
   */
  public String updateRound(
      HttpSession session,
      Model model,
      @ModelAttribute Holes holes,
      @PathVariable("id") long roundId);

  /**
   * Post method to update a round
   * 
   * @param holes   - The hole object with the scores
   * @param model   - The model
   * @param session - The session object
   * @param roundId - The id of the round to update
   * @return
   */
  public String postUpdateForm(
      @ModelAttribute Holes holes,
      Model model,
      HttpSession session,
      @PathVariable("id") long roundId);
}
