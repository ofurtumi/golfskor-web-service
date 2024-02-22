package is.hi.hugbo.REST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import is.hi.hugbo.model.Course;
import is.hi.hugbo.model.Round;
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
  ResponseEntity<?> createRound(
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

    int[] parsedHoles = roundService.parseHoles(holes);

    Round savedRound = roundService.save(courseId, userId, parsedHoles);
    return new ResponseEntity<>(savedRound, HttpStatus.OK);
  }

  @PatchMapping("/round")
  ResponseEntity<?> updateRound(
      @RequestHeader(value = "Authorization") String authHeader,
      @RequestParam(value = "roundId") int roundId,
      @RequestParam(value = "holes") String holes,
      @RequestParam(value = "userId") int userId) {

    Round roundToUpdate = roundService.findById(roundId);

    if (roundToUpdate == null) {
      return new ResponseEntity<>("Round not found", HttpStatus.NOT_FOUND);
    }

    String token = authHeader.substring(7);
    String tokenUsername = jwtUtils.getUserNameFromJwtToken(token);
    if (tokenUsername == null) {
      return new ResponseEntity<>("Auth token tilheyrir ekki notanda", HttpStatus.UNAUTHORIZED);
    } else if (!tokenUsername.equals(userService.findUser(userId).getUsername())) {
      return new ResponseEntity<>("Notendanafn og token passa ekki saman", HttpStatus.UNAUTHORIZED);
    } else if (roundToUpdate.getUser().getId() != userId) {
      return new ResponseEntity<>("Round tilheyrir ekki user", HttpStatus.UNAUTHORIZED);
    }

    int[] parsedHoles = roundService.parseHoles(holes);
    Round updatedRound = roundService.update(roundToUpdate, parsedHoles);

    return new ResponseEntity<>(updatedRound, HttpStatus.OK);
  }

  @GetMapping("/getround")
  ResponseEntity<?> getRound(
      @RequestParam(value = "id") int id) {

    Round round = roundService.findById(id);

    if (round == null) {
      return new ResponseEntity<>("Round not found", HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(round, HttpStatus.OK);

  }

  @DeleteMapping("/round")
  ResponseEntity<?> updateRound(
      @RequestHeader(value = "Authorization") String authHeader,
      @RequestParam(value = "roundId") int roundId,
      @RequestParam(value = "userId") int userId) {

    Round roundToDelete = roundService.findById(roundId);

    if (roundToDelete == null) {
      return new ResponseEntity<>("Round not found", HttpStatus.NOT_FOUND);
    }

    String token = authHeader.substring(7);
    String tokenUsername = jwtUtils.getUserNameFromJwtToken(token);
    if (tokenUsername == null) {
      return new ResponseEntity<>("Auth token tilheyrir ekki notanda", HttpStatus.UNAUTHORIZED);
    } else if (!tokenUsername.equals(userService.findUser(userId).getUsername())) {
      return new ResponseEntity<>("Notendanafn og token passa ekki saman", HttpStatus.UNAUTHORIZED);
    } else if (roundToDelete.getUser().getId() != userId) {
      return new ResponseEntity<>("Round tilheyrir ekki user", HttpStatus.UNAUTHORIZED);
    }

    roundService.delete(roundToDelete);

    return new ResponseEntity<>("Umferð eytt!", HttpStatus.OK);
  }
}
