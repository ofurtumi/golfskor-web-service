package is.hi.hugbo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import is.hi.hugbo.interfaces.IHomeController;
import is.hi.hugbo.model.User;
import is.hi.hugbo.services.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController implements IHomeController {
  private UserService userService;

  @Autowired
  public HomeController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/")
  public String home(Model model, HttpSession session) {
    String username = (String) session.getAttribute("user");
    if (username != null) {
      User user = userService.findUser(username);
      // Add some data to the Model
      model.addAttribute("user", user);
    }
    return "home";
  }
}
