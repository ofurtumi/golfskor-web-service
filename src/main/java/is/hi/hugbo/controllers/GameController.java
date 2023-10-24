package is.hi.hugbo.controllers;

import java.util.Collections;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import is.hi.hugbo.interfaces.IGameController;
import is.hi.hugbo.model.Course;
import is.hi.hugbo.model.Holes;
import is.hi.hugbo.model.User;
import is.hi.hugbo.model.Round;
import is.hi.hugbo.services.CourseService;
import is.hi.hugbo.services.RoundService;
import is.hi.hugbo.services.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
public class GameController implements IGameController {
  RoundService roundService;
  CourseService courseService;
  UserService userService;

  @Autowired
  public GameController(RoundService roundService, CourseService courseService, UserService userService) {
    this.roundService = roundService;
    this.courseService = courseService;
    this.userService = userService;
  }

  @GetMapping("/courses")
  public String getCourses(Model model, HttpSession session) {
    User user = (User) session.getAttribute("user");
    if (user != null) {
      model.addAttribute("user", user);
    }
    List<Course> courses = courseService.findAll();
    List<Round> rounds = roundService.findAll();
    Collections.reverse(rounds);
    model.addAttribute("courses", courses);
    model.addAttribute("rounds", rounds);

    return "courses";
  }

  @GetMapping("/round/{id}")
  public String getRoundForm(
      @ModelAttribute Holes holes,
      Model model,
      HttpSession session,
      @PathVariable("id") long courseId,
      @RequestParam(name = "holes", required = false) Integer numHoles) {

    User user = (User) session.getAttribute("user");
    if (user == null) {
      return "redirect:/login";
    }

    model.addAttribute("endpoint", "/round/" + courseId);
    if (numHoles != null && numHoles == 9) {
      holes.setSize(9);
    }
    return "round";
  }

  @PostMapping("/round/{id}")
  public String postRoundForm(
      @ModelAttribute Holes holes,
      Model model,
      HttpSession session,
      @PathVariable("id") long courseId) {
    User user = (User) session.getAttribute("user");
    if (user == null) {
      return "redirect:/login";
    }

    int[] holesToSave = holes.getHoles();
    roundService.save(courseId, user.getId(), holesToSave);
    session.setAttribute("user", userService.findUser(user.getUsername()));
    return "redirect:/courses";
  }

  @GetMapping("/round/delete/{id}")
  public String deleteRound(
      HttpSession session,
      @PathVariable("id") long roundId) {
    Round roundToDelete = roundService.findById(roundId);
    if (roundToDelete != null) {
      roundService.delete(roundToDelete);
      User user = (User) session.getAttribute("user");
      session.setAttribute("user", userService.findUser(user.getUsername()));
    }
    return "redirect:/";
  }

  @GetMapping("/round/update/{id}")
  public String updateRound(
      HttpSession session,
      Model model,
      @ModelAttribute Holes holes,
      @PathVariable("id") long roundId) {

    Round roundToUpdate = roundService.findById(roundId);
    String roundUsername = roundToUpdate.getUser().getUsername();
    String sessionUsername = ((User) session.getAttribute("user")).getUsername();

    if (roundToUpdate != null && roundUsername.equals(sessionUsername)) {
      model.addAttribute("endpoint", "/round/update/" + roundId);
      holes.setHoles(roundToUpdate.getHoles());
      return "round";
    }
    return "redirect:/"; // return back to homepage
  }

  @PostMapping("/round/update/{id}")
  public String postUpdateForm(
      @ModelAttribute Holes holes,
      Model model,
      HttpSession session,
      @PathVariable("id") long roundId) {

    Round roundToUpdate = roundService.findById(roundId);
    String roundUsername = roundToUpdate.getUser().getUsername();
    String sessionUsername = ((User) session.getAttribute("user")).getUsername();

    if (roundToUpdate == null || !roundUsername.equals(sessionUsername)) {
      return "redirect:/login";
    }

    int[] holesToSave = holes.getHoles();
    roundService.update(roundToUpdate, holesToSave);
    session.setAttribute("user", userService.findUser(sessionUsername));
    return "redirect:/";
  }
}
