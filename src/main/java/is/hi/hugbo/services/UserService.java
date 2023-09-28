package is.hi.hugbo.services;

import is.hi.hugbo.repositories.UserRepository;
import is.hi.hugbo.controllers.UserController;
import is.hi.hugbo.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/user")
public class UserService {
  @Autowired
  UserRepository UR;

  @PostMapping("/register")
  public ResponseEntity<User> registerUser(@RequestBody User user) {
    try {
      if (UR.existsByUsername(user.getUsername())) {
        return new ResponseEntity<>(null, HttpStatus.CONFLICT);
      }

      User tempUser = UR.save(new User(user.getUsername(), user.getPassword()));
      return new ResponseEntity<>(tempUser, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/login")
  public ResponseEntity<User> loginUser(@RequestBody User user) {
    try {
      if (UR.existsByUsername(user.getUsername())) {

        List<User> users = UR.findByUsername(user.getUsername());
        if (users.get(0).getPassword().equals(user.getPassword())) {
          return new ResponseEntity<>(users.get(0), HttpStatus.OK);
        }
      }
      return new ResponseEntity<>(null, HttpStatus.CONFLICT);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
