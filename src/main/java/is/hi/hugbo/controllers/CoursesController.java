package is.hi.hugbo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import is.hi.hugbo.model.Course;
import is.hi.hugbo.model.Round;
import is.hi.hugbo.services.CourseService;
import is.hi.hugbo.services.RoundService;
import jakarta.servlet.http.HttpSession;

@Controller
public class CoursesController {
  RoundService roundService;
  CourseService courseService;

  @Autowired
  public CoursesController(RoundService roundService, CourseService courseService) {
    this.roundService = roundService;
    this.courseService = courseService;
  }

  @GetMapping("/courses")
  public String courses(Model model) {
    List<Course> courses = courseService.findAll();
    model.addAttribute("courses", courses);

    return "courses";
  }

  @GetMapping("/round/{id}")
  public String getRoundForm(
      @ModelAttribute Round round,
      Model model,
      HttpSession session,
      @PathVariable("id") long courseId) {

    String username = (String) session.getAttribute("user");
    if (username == null) {
      return "redirect:/login";
    }
    Course course = courseService.findById(courseId);
    model.addAttribute("current_course", course);
    return "round";
  }

  @PutMapping("/round/{id}")
  public String postRoundForm(
      @ModelAttribute Round round,
      Model model,
      HttpSession session,
      @PathVariable("id") long courseId) {
    String username = (String) session.getAttribute("user");
    if (username == null) {
      return "redirect:/login";
    }
    Course course = (Course) session.getAttribute("current_course");
    roundService.save(course, username, round.getHoles());
    return "round";
  }
}
