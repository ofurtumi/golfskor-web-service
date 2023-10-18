package is.hi.hugbo.controllers;

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
import jakarta.transaction.Transactional;

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
    model.addAttribute("courses", courses);

    return "courses";
  }

  @GetMapping("/round/{id}")
  public String getRoundForm(
      @ModelAttribute Holes holes,
      Model model,
      HttpSession session,
      @PathVariable("id") long courseId) {

    User user = (User) session.getAttribute("user");
    if (user == null) {
      return "redirect:/login";
    }

    // TODO: take care of this inside thymeleaf template
    model.addAttribute("course_id", courseId);
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

    // TODO: laga þetta cascade ves þannig hægt sé að setja user í round
    roundService.save(courseId, user.getId(), holes.getHoles());
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

  // work in progress 
  
  @GetMapping("/round/update/{id}")
  public String updateRound(
    HttpSession session,
    @PathVariable("id") long roundId) {
      Round roundToUpdate = roundService.findById(roundId);
      if (roundToUpdate != null){
        int[] oldHoles = roundToUpdate.getHoles();
        // need to send oldHoles to user to update and then take the updated holes and post them
        int[] updatedHoles = new int[8]; // temporary, supposerd to pe updated holes
        roundService.update(roundToUpdate, updatedHoles);
        User user = (User) session.getAttribute("user");
        session.setAttribute("user", userService.findUser(user.getUsername()));
      }
      return "redirect:/"; // return back to homepage
    }
    
}
