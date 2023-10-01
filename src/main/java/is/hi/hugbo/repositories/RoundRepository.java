package is.hi.hugbo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import is.hi.hugbo.model.Course;
import is.hi.hugbo.model.Round;
import is.hi.hugbo.model.User;

public interface RoundRepository extends JpaRepository<Round, Long> {
  Round findById(long id);

  List<Round> findByCourse(Course course);

  List<Round> findByUser(User user);

  void delete(Round round);
}
