package is.hi.hugbo.services;

import is.hi.hugbo.interfaces.IUserService;
import is.hi.hugbo.model.User;
import is.hi.hugbo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
  @Autowired
  UserRepository UR;

  public User Register(String username, String password) {
    User newUser = new User(username, password);
    UR.save(newUser);
    return newUser;
  }

  public User Login(String username, String password) {
    if (UserExists(username)) {

      User user = UR.findByUsername(username);
      if (user.getPassword().equals(password)) {
        return user;
      }
    }
    return null;
  }

  public boolean Logout() {
    return false;
  }

  public boolean UserExists(String username) {
    return UR.existsByUsername(username);
  }

  public User findUser(String username) {
    User user = UR.findByUsername(username);
    return user;
  }
}
