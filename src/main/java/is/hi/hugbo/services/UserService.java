package is.hi.hugbo.services;

import is.hi.hugbo.interfaces.IUserService;
import is.hi.hugbo.model.Round;
import is.hi.hugbo.model.User;
import is.hi.hugbo.repositories.UserRepository;

import java.util.Arrays;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
  @Autowired
  UserRepository UR;

  public User register(String username, String password) {
    User newUser = new User(username, password);
    UR.save(newUser);
    return newUser;
  }

  public User login(String username, String password) {
    if (userExists(username)) {

      User user = UR.findByUsername(username);
      if (user.getPassword().equals(password)) {
        return user;
      }
    }
    return null;
  }

  public boolean userExists(String username) {
    return UR.existsByUsername(username);
  }

  public User findUser(String username) {
    User user = UR.findByUsername(username);
    return user;
  }

  public User findUser(long userId) {
    User user = UR.findById(userId).get();
    return user;
  }

  public void addRound(long userId, Round round) {
    User user = UR.findById(userId).get();
    user.getRounds().add(round);
    UR.save(user);
  }

  public void removeRound(Round round) {
    User user = round.getUser();
    user.getRounds().remove(round);
    UR.save(user);
  }
}
