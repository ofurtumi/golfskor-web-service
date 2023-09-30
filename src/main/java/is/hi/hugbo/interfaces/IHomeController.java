package is.hi.hugbo.interfaces;

import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;

public interface IHomeController {
  /**
   * Very simple get method for the home page
   * 
   * @return home.html
   */
  public String home(Model model, HttpSession session);
}
