package is.hi.hugbo.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import is.hi.hugbo.interfaces.IRoundService;
import is.hi.hugbo.model.Round;
import is.hi.hugbo.repositories.RoundRepository;

@Service
public class RoundService implements IRoundService {
  @Autowired
  RoundRepository RR;

  @Autowired
  CourseService CS;

  @Autowired
  UserService US;

  public Round findById(long id) {
    return RR.findById(id);
  }

  public List<Round> findAll() {
    return RR.findAll();
  }

  public Round save(long courseId, long userId, int[] holes) {
    Round newRound = new Round(
        CS.findById(courseId),
        US.findUser(userId),
        holes);
    RR.save(newRound);
    CS.addRound(courseId, newRound);
    US.addRound(userId, newRound);
    return newRound;
  }

  public Round update(Round round, int[] holes) {
    round.setHoles(holes);
    RR.save(round);
    return round;
  }

  public void delete(Round round) {
    RR.delete(round);
    US.removeRound(round);
  }

  public int[] parseHoles(String holes) {
    int[] parsedHoles = Arrays.stream(holes.split(",")).mapToInt(Integer::parseInt).toArray();
    return parsedHoles;
  }
}
