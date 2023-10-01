package is.hi.hugbo.interfaces;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import is.hi.hugbo.model.User;
import jakarta.servlet.http.HttpSession;

public interface IUserController {
  /**
   * Very simple get method for the home page
   * 
   * @return home.html
   */
  public String getHome(Model model, HttpSession session);

  /**
   * Get method that returns the register page
   * 
   * @param model   - The model, will include empty user and possible errors
   * @param session - The session object, used to check if user is logged in
   * @return register.html or redirect:/ if user is logged in
   */
  public String getRegister(Model model, HttpSession session);

  /**
   * Post method that registers a user
   * 
   * @param user  - New user to register
   * @param model - The model, will include user and possible errors
   * @return redirect:/login if successful, redirect:/register otherwise
   */
  public String postRegister(
      @ModelAttribute User user,
      Model model);

  /**
   * Get method that returns the login page
   *
   * @param model   - The model, will include empty user and possible errors
   * @param session - The session object, used to check if user is logged in
   * @return login.html or redirect:/ if user is logged in
   */
  public String getLogin(Model model, HttpSession session);

  /**
   * Post method that logs in a user
   *
   * @param user    - User to log in
   * @param session - The session object
   * @param model   - The model, will include user and possible errors
   * @return redirect:/ if successful, redirect:/login with errors otherwise
   */
  public String postLogin(User user, HttpSession session, Model model);

  /**
   * Simple get method to log out a user by invalidating the session
   * 
   * @param session - The session object
   * @return
   */
  public String getLogout(HttpSession session);
}
