package is.hi.hugbo.controllers;

import is.hi.hugbo.services.UserService;
import jakarta.servlet.http.HttpSession;
import is.hi.hugbo.interfaces.IUserController;
import is.hi.hugbo.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController implements IUserController {
  UserService userService;
  String error = "";

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/register")
  public String getRegister(Model model, HttpSession session) {
    if (session.getAttribute("user") != null) {
      return "redirect:/";
    }

    model.addAttribute("user", new User());
    model.addAttribute("error", error);
    error = "";
    return "register";
  }

  @PostMapping("/register")
  public String postRegister(
      @ModelAttribute User user,
      Model model) {
    if (userService.userExists(user.getUsername())) {
      System.out.println("error: User exists");
      error = "Username already exists";
      return "redirect:/register";
    }

    if (user.getUsername().equals("") || user.getPassword().equals("")) {
      System.out.println("error: Username or password empty");
      error = "Username or password cannot be empty";
      return "redirect:/register";
    }

    User olduser = userService.findUser(user.getUsername());
    if (olduser != null) {
      System.out.println("error: User already exists");
      error = "User already exists";
      return "redirect:/register";
    }
    userService.register(user.getUsername(), user.getPassword());
    return "redirect:/login";
  }

  @GetMapping("/login")
  public String getLogin(User user, HttpSession session) {
    if (session.getAttribute("user") != null) {
      return "redirect:/";
    }

    return "login";
  }

  @PostMapping("/login")
  public String postLogin(User user, HttpSession session, Model model) {
    if (!userService.userExists(user.getUsername())) {
      model.addAttribute("error", "Username does not exist");
      return "redirect:/login";
    }

    User loginuser = userService.login(user.getUsername(), user.getPassword());
    if (loginuser == null) {
      model.addAttribute("error", "Wrong username or password");
      return "redirect:/login";
    }
    session.setAttribute("user", user.getUsername());
    model.addAttribute("user", user.getUsername());
    return "redirect:/";
  }

  @GetMapping("/logout")
  public String getLogout(HttpSession session) {
    session.invalidate();
    return "redirect:/";
  }
}
