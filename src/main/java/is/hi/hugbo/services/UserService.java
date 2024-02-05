package is.hi.hugbo.services;

import is.hi.hugbo.interfaces.IUserService;
import is.hi.hugbo.model.Round;
import is.hi.hugbo.model.User;
import is.hi.hugbo.repositories.UserRepository;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
  @Autowired
  UserRepository UR;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public User register(String username, String password) {
    User newUser = new User();
    newUser.setUsername(username);
    newUser.setPassword(passwordEncoder.encode(password));
    UR.save(newUser);
    return newUser;
  }

  /*
   * The matches method will compare the raw password with the encrypted one
   */
  private boolean checkPassword(User user, String rawPassword) {
    return passwordEncoder.matches(rawPassword, user.getPassword());
  }

  public User login(String username, String password) {
    if (userExists(username)) {

      User user = UR.findByUsername(username);
      if (user != null && checkPassword(user, password)) {
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

  public void delete(String username) {
    User user = UR.findByUsername(username);
    if (user != null) {
      UR.delete(user);
    }
  }

  public int[] sortByScore(int[] arrayToSort) {
    for (int i = 1; i < arrayToSort.length; i++) {
      int x = arrayToSort[i];
      int j = Math.abs(Arrays.binarySearch(arrayToSort, 0, i, x) + 1);
      System.arraycopy(arrayToSort, j, arrayToSort, j + 1, i - j);
      arrayToSort[j] = x;
    }
    return arrayToSort;
  }

  public double handicap(User user) {
    int roundsCounter = 0;
    int sum = 0;
    int[] allScores = new int[user.getRounds().size()];
    for (Round UR : user.getRounds()) {
      if (UR.getHoles().length == 9) {
        allScores[roundsCounter] = UR.getScore() * 2;
        roundsCounter++;
        continue;
      }
      allScores[roundsCounter] = UR.getScore();
      roundsCounter++;
    }

    if (roundsCounter > 20) { // only have last 20 rounds played
      for (int i = 0; i < 20; i++) {
        allScores[i] = allScores[i + 1];
      }
    }

    int[] sortedScores = sortByScore(allScores);
    if (roundsCounter < 8) {
      for (int score : sortedScores) {
        sum += score;
      }
    } else {
      int[] bestEight = new int[8];
      for (int i = 0; i < 8; i++) {
        bestEight[i] = sortedScores[i];
      }
      for (int score : bestEight) {
        sum += score;
      }
    }

    double averageScore;
    if (roundsCounter >= 8) {// if user has played more than 8 rounds then best 8 rounds are calculated
      averageScore = (double) sum / 8.0;
    } else if (roundsCounter != 0) { // so handicap is not NaN
      averageScore = (double) sum / (double) roundsCounter;
    } else {
      averageScore = 126; // starting handicap is 54

    }
    return averageScore - 72;
  }
}
