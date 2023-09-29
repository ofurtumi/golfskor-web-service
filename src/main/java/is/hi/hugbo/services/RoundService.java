package is.hi.hugbo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import is.hi.hugbo.interfaces.IRoundService;
import is.hi.hugbo.model.Course;
import is.hi.hugbo.model.Round;
import is.hi.hugbo.model.User;
import is.hi.hugbo.repositories.RoundRepository;

@Service
public class RoundService implements IRoundService {
  @Autowired
  RoundRepository RR;

  public Round save(User user, Course course, int[] holes) {
    Round newRound = new Round(course, user, holes);
    RR.save(newRound);
    return newRound;
  }

  public Round update(Round round, int[] holes) {
    round.setHoles(holes);
    RR.save(round);
    return round;
  }

  public List<Round> findByCourse(Long courseId) {
    throw new UnsupportedOperationException("Unimplemented method 'findByCourse'");
  }

  public List<Round> findByUser(Long userId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByUser'");
  }

}
