package is.hi.hugbo.services;

import is.hi.hugbo.interfaces.IUserService;
import is.hi.hugbo.model.Round;
import is.hi.hugbo.model.User;
import is.hi.hugbo.repositories.UserRepository;
import is.hi.hugbo.security.PasswordEncoder;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
  @Autowired
  UserRepository UR;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public User register(String username, String password) { 
    User newUser = new User();
    newUser.setUsername(username);
    newUser.setPassword(bCryptPasswordEncoder.encode(password));
    UR.save(newUser);
    return newUser;
  }
  /*
   * The matches method will compare the raw password with the encrypted one
   */
  private boolean checkPassword(User user, String rawPassword) {
    return bCryptPasswordEncoder.matches(rawPassword, user.getPassword());
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
  
  public int[] sortByScore(int[] arrayToSort){
    for( int i = 1; i < arrayToSort.length; i++){
      int x = arrayToSort[i];
      int j = Math.abs(Arrays.binarySearch(arrayToSort, 0, i, x) + 1);
      System.arraycopy(arrayToSort, j, arrayToSort, j + 1, i - j);
      arrayToSort[j] = x;
    }
    return arrayToSort;
  }

  public double handicap(User user){
    int counter = 0;
    int sum = 0;
    int[] allScores = new int[user.getRounds().size()];
    for (Round UR : user.getRounds()) {
      if(UR.getHoles().length == 9){
        allScores[counter] = UR.getScore()*2;
        counter++;
        break;
      }
      allScores[counter] = UR.getScore();
      counter++;
    }

    if(counter > 20){
      for(int i = 0; i < 20; i++){
        allScores[i] = allScores[i+1];
      }
    }
    allScores = sortByScore(allScores);
    if(counter <= 8){
      for(int score:allScores){
        sum += score;
      }
    } else {
      int[] bestEight = new int[8];
      for(int i = 0; i < 8; i++){
        bestEight[i] = allScores[i];
      }
      for(int score:bestEight){
        sum += score;
      }
    }
    double averageScore;
    if(counter >= 8){
      averageScore = (double)sum / 8.0; 
    }
    else{
      averageScore = (double)sum/(double)counter;
    }
    return averageScore-72;
  }
}
