package is.hi.hugbo.interfaces;

import is.hi.hugbo.model.User;

public interface IUserService {
  public User Login(String username, String password);

  public boolean Logout();

  public User Register(String username, String password);

  public boolean UserExists(String username);
}
