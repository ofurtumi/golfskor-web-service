package is.hi.hugbo.interfaces;

public interface IUserController {
  public boolean Login(String username, String password);

  public boolean Logout();

  public boolean Register(String username, String password);
}
