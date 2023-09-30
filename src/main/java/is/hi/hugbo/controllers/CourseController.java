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
import is.hi.hugbo.services.CourseService;
import is.hi.hugbo.services.RoundService;
import jakarta.servlet.http.HttpSession;

@Controller
public class CourseController implements ICourseController {
  RoundService roundService;
  CourseService courseService;

  @Autowired
  public CourseController(RoundService roundService, CourseService courseService) {
    this.roundService = roundService;
    this.courseService = courseService;
  }

  @GetMapping("/courses")
  public String getCourses(Model model, HttpSession session) {
    String username = (String) session.getAttribute("user");
    if (username != null) {
      model.addAttribute("user", username);
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

    String username = (String) session.getAttribute("user");
    if (username == null) {
      return "redirect:/login";
    }
    model.addAttribute("course_id", courseId);
    return "round";
  }

  @PostMapping("/round/{id}")
  public String postRoundForm(
      @ModelAttribute Holes holes,
      Model model,
      HttpSession session,
      @PathVariable("id") long courseId) {
    String username = (String) session.getAttribute("user");
    if (username == null) {
      return "redirect:/login";
    }
    roundService.save(username, courseId, holes.getHoles());
    return "redirect:/courses";
  }
}