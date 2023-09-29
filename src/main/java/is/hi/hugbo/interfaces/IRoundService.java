package is.hi.hugbo.interfaces;

import java.util.List;

import is.hi.hugbo.model.Course;
import is.hi.hugbo.model.Round;
import is.hi.hugbo.model.User;

public interface IRoundService {
  public Round save(User user, Course course, int[] holes);

  public Round update(Round round, int[] holes);

  public List<Round> findByCourse(Long courseId);

  public List<Round> findByUser(Long userId);
}
