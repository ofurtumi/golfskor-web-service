package is.hi.hugbo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import is.hi.hugbo.interfaces.ICourseController;
import is.hi.hugbo.model.Course;
import is.hi.hugbo.model.Holes;
import is.hi.hugbo.model.User;
import is.hi.hugbo.services.CourseService;
import is.hi.hugbo.services.RoundService;
import is.hi.hugbo.services.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
public class CourseController implements ICourseController {
  RoundService roundService;
  CourseService courseService;
  UserService userService;

  @Autowired
  public CourseController(RoundService roundService, CourseService courseService, UserService userService) {
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
    roundService.save(courseId, user.getUsername(), holes.getHoles());
    session.setAttribute("user", userService.findUser(user.getUsername()));
    return "redirect:/courses";
  }
}
