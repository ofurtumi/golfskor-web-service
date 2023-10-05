package is.hi.hugbo.interfaces;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import is.hi.hugbo.model.Holes;
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
      Model model,
      HttpSession session,
      @PathVariable("id") long courseId);

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
}
