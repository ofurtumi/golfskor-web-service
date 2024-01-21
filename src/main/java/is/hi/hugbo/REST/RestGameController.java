package is.hi.hugbo.REST;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import is.hi.hugbo.model.Course;
import is.hi.hugbo.model.Round;
import is.hi.hugbo.model.User;
import is.hi.hugbo.security.jwt.JwtUtils;
import is.hi.hugbo.services.CourseService;
import is.hi.hugbo.services.RoundService;
import is.hi.hugbo.services.UserService;

@RestController
@RequestMapping("/api")
public class RestGameController {
  UserService userService;
  RoundService roundService;
  CourseService courseService;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  public RestGameController(RoundService roundService, CourseService courseService, UserService userService) {
    this.userService = userService;
    this.roundService = roundService;
    this.courseService = courseService;
  }

  @GetMapping("/courses")
  ResponseEntity<List<Course>> getCourses() {
    List<Course> courses = courseService.findAll();
    return new ResponseEntity<>(courses, HttpStatus.OK);
  }

  @PostMapping("/round")
  ResponseEntity<?> postRound(
      @RequestHeader(value = "Authorization") String authHeader,
      @RequestParam(value = "courseId") int courseId,
      @RequestParam(value = "holes") String holes,
      @RequestParam(value = "userId") int userId) {

    String token = authHeader.substring(7);
    String tokenUsername = jwtUtils.getUserNameFromJwtToken(token);
    if (tokenUsername == null) {
      return new ResponseEntity<>("Auth token tilheyrir ekki notanda", HttpStatus.UNAUTHORIZED);
    } else if (!tokenUsername.equals(userService.findUser(userId).getUsername())) {
      return new ResponseEntity<>("Notendanafn og token passa ekki saman", HttpStatus.UNAUTHORIZED);
    }

    int[] parsedHoles = Arrays.stream(holes.split(",")).mapToInt(Integer::parseInt).toArray();

    Round savedRound = roundService.save(courseId, userId, parsedHoles);
    return new ResponseEntity<>(savedRound, HttpStatus.OK);
  }
}
