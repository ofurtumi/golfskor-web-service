package is.hi.hugbo.REST;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import is.hi.hugbo.security.jwt.JwtUtils;
import is.hi.hugbo.model.User;
import is.hi.hugbo.payloads.JwtResponse;
import is.hi.hugbo.services.UserDetailsImpl;
import is.hi.hugbo.services.UserService;

@RestController
@RequestMapping("/api/user")
public class RestUserController {
  UserService userService;

  @Autowired
  public RestUserController(UserService userService) {
    this.userService = userService;
  }

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @ResponseBody
  @GetMapping("/info")
  ResponseEntity<?> info(
      @RequestParam(value = "username", defaultValue = "") String username) {
    if (username.equals("")) {
      return new ResponseEntity<>("Notendanafn vantar!", HttpStatus.BAD_REQUEST);
    }

    User user = userService.findUser(username);
    if (user == null) {
      return new ResponseEntity<>("Notandi með þetta notendafn ekki til!", HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @ResponseBody
  @PostMapping("/register")
  ResponseEntity<?> register(
      @RequestParam(value = "username", defaultValue = "") String username,
      @RequestParam(value = "password", defaultValue = "") String password) {

    if (username.equals("") || password.equals("")) {
      return new ResponseEntity<>("Notendanafn og/eða lykilorð vantar!", HttpStatus.BAD_REQUEST);
    }

    if (userService.userExists(username)) {
      return new ResponseEntity<>("Notandi með þetta notendanafn nú þegar til!", HttpStatus.CONFLICT);
    }

    User newUser = userService.register(username, password);

    return new ResponseEntity<>(newUser, HttpStatus.OK);
  }

  @ResponseBody
  ResponseEntity<?> login(
      @RequestParam(value = "username", defaultValue = "") String username,
      @RequestParam(value = "password", defaultValue = "") String password) {
    if (username.equals("") || password.equals("")) {
      return new ResponseEntity<>("Notendanafn og/eða lykilorð vantar!", HttpStatus.BAD_REQUEST);
    }

    if (!userService.userExists(username)) {
      return new ResponseEntity<>("Notandi með þetta notendafn ekki til!", HttpStatus.NOT_FOUND);
    }

    User loggedInUser = userService.login(username, password);

    return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(
      @RequestParam(value = "username", defaultValue = "") String username,
      @RequestParam(value = "password", defaultValue = "") String password) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, password));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    return ResponseEntity.ok(new JwtResponse(jwt,
        userDetails.getId(),
        userDetails.getUsername()));
  }

  @ResponseBody
  @DeleteMapping("/delete")
  ResponseEntity<?> delete(
      @RequestParam(value = "username", defaultValue = "") String username) {
    if (username.equals("")) {
      return new ResponseEntity<>("Notendanafn vantar!", HttpStatus.BAD_REQUEST);
    }
    if (!userService.userExists(username)) {
      return new ResponseEntity<>("Notandi með þetta notendafn ekki til!", HttpStatus.NOT_FOUND);
    }
    User deletedUser = userService.delete(username);
    return new ResponseEntity<>(deletedUser, HttpStatus.OK);
  }
}
