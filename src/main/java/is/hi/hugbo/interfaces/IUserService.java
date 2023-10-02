package is.hi.hugbo.interfaces;

import is.hi.hugbo.model.Round;
import is.hi.hugbo.model.User;

public interface IUserService {
  /**
   * Method to log in a user
   * 
   * @todo - Encrypt/decrypt password using BCrypt
   * @param username - The username of the user
   * @param password - The password of the user
   * @return The logged in user
   */
  public User login(String username, String password);

  /**
   * Method to create a user on the database
   * 
   * @todo - Encrypt password using BCrypt
   * @param username - The username of the user
   * @param password - The password of the user
   * @return The newly created user
   */
  public User register(String username, String password);

  /**
   * Simple method to check if a user exists on the database
   * 
   * @param username - The username to check
   * @return true if user exists, false otherwise
   */
  public boolean userExists(String username);

  /**
   * Searches for a user on the database
   * 
   * @param username - The username to search for
   * @return The user if found, null otherwise
   */
  public User findUser(String username);

  /**
   * Searches for a user on the database
   * 
   * @param userId - The user id to search for
   * @return The user if found, null otherwise
   */
  public User findUser(long userId);

  /**
   * Method to save round to user, needed for one to many relationship
   * 
   * @param username - The username of the player
   * @param round    - The round to add
   */
  public void addRound(long userId, Round round);
}
