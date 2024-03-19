package is.hi.hugbo.REST;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import is.hi.hugbo.security.jwt.JwtUtils;
import is.hi.hugbo.model.Round;
import is.hi.hugbo.model.User;
import is.hi.hugbo.payloads.JwtResponse;
import is.hi.hugbo.services.RoundService;
import is.hi.hugbo.services.UserDetailsImpl;
import is.hi.hugbo.services.UserService;

@RestController
@RequestMapping("/api/user")
public class RestUserController {
  UserService userService;

  RoundService roundService;

  @Autowired
  public RestUserController(UserService userService, RoundService roundService) {
    this.userService = userService;
    this.roundService = roundService;
  }

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

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

  @PostMapping("/reload")
  public ResponseEntity<?> refreshToken(
      @RequestHeader(value = "Authorization") String authHeader,
      @RequestParam(value = "username", defaultValue = "") String username) {

    String token = authHeader.substring(7);
    if (username.equals("") || token.equals("")) {
      return new ResponseEntity<>("Notendanafn og/eða token vantar!", HttpStatus.BAD_REQUEST);
    }

    if (!userService.userExists(username)) {
      return new ResponseEntity<>("Notandi með þetta notendanafn ekki til!", HttpStatus.NOT_FOUND);
    }

    String tokenUsername = jwtUtils.getUserNameFromJwtToken(token);
    if (tokenUsername == null) {
      return new ResponseEntity<>("Token er ekki gilt!", HttpStatus.BAD_REQUEST);
    } else if (!username.equals(tokenUsername)) {
      return new ResponseEntity<>("Notendanafn og token passa ekki saman!", HttpStatus.BAD_REQUEST);
    }

    if (!jwtUtils.validateJwtToken(token)) {
      return new ResponseEntity<>("Token er útrunninn!", HttpStatus.BAD_REQUEST);
    }

    String jwt = jwtUtils.generateTokenFromUsername(username);
    long userId = userService.findUser(username).getId();

    return ResponseEntity.ok(new JwtResponse(jwt,
        userId,
        username));
  }

  @DeleteMapping
  ResponseEntity<?> delete(
      @RequestParam(value = "username", defaultValue = "") String username) {
    if (username.equals("")) {
      return new ResponseEntity<>("Notendanafn vantar!", HttpStatus.BAD_REQUEST);
    } else if (!userService.userExists(username)) {
      return new ResponseEntity<>("Notandi með þetta notendafn ekki til!", HttpStatus.NOT_FOUND);
    } else if (!username.equals("tester")) {
      return new ResponseEntity<>("Í augnablikinu má bara eyða \"tester\" notanda!", HttpStatus.FORBIDDEN);
    }

    // cascade fátæka mannsins
    List<Round> rounds = userService.findUser(username).getRounds();
    Round[] roundArray = new Round[rounds.size()];
    rounds.toArray(roundArray);
    for (int i = 0; i < roundArray.length; i++) {
      roundService.delete(roundArray[i]);
    }

    userService.delete(username);
    return new ResponseEntity<>("User deleted!", HttpStatus.OK);
  }
}
