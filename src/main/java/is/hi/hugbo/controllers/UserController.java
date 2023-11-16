package is.hi.hugbo.controllers;

import is.hi.hugbo.services.UserService;
import jakarta.servlet.http.HttpSession;
import is.hi.hugbo.interfaces.IUserController;
import is.hi.hugbo.model.Round;
import is.hi.hugbo.model.User;

import java.util.Collections;
import java.util.List;

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

  @GetMapping("/")
  public String getHome(Model model, HttpSession session) {
    User user = (User) session.getAttribute("user");
    if (user != null) {
      model.addAttribute("user", user);
      List<Round> rounds = user.getRounds();
      Collections.reverse(rounds);
      model.addAttribute("rounds", rounds);
      model.addAttribute("handicap", userService.handicap(user));
    }
    return "home";
  }

  @GetMapping("/register")
  public String getRegister(Model model, HttpSession session) {
    if (session.getAttribute("user") != null) {
      return "redirect:/";
    }

    model.addAttribute("user", new User());
    if (error != "") {
      model.addAttribute("error", error);
    }
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
  public String getLogin(Model model, HttpSession session) {
    if (session.getAttribute("user") != null) {
      return "redirect:/";
    }

    model.addAttribute("user", new User());
    if (error != "") {
      model.addAttribute("error", error);
    }
    error = "";

    return "login";
  }

  @PostMapping("/login")
  public String postLogin(User user, HttpSession session, Model model) {
    if (!userService.userExists(user.getUsername())) {
      error = "Username does not exist";
      return "redirect:/login";
    }

    User loginUser = userService.login(user.getUsername(), user.getPassword());
    if (loginUser == null) {
      error = "Wrong username or password";
      return "redirect:/login";
    }

    session.setAttribute("user", loginUser);
    return "redirect:/";
  }

  @GetMapping("/logout")
  public String getLogout(HttpSession session) {
    session.invalidate();
    return "redirect:/";
  }
}
