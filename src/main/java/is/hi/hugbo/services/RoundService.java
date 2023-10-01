package is.hi.hugbo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import is.hi.hugbo.interfaces.IRoundService;
import is.hi.hugbo.model.Round;
import is.hi.hugbo.model.User;
import is.hi.hugbo.repositories.RoundRepository;

@Service
public class RoundService implements IRoundService {
  @Autowired
  RoundRepository RR;

  @Autowired
  CourseService CS;

  public Round save(Long courseId, User user, int[] holes) {

    Round newRound = new Round(
        CS.findById(courseId),
        user,
        holes);
    RR.save(newRound);
    return newRound;
  }

  public Round update(Round round, int[] holes) {
    round.setHoles(holes);
    RR.save(round);
    return round;
  }

  public void delete(Round round) {
    RR.delete(round);
  }
}
