package is.hi.hugbo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import is.hi.hugbo.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
  Course findById(long id);

  List<Course> findAll();
}
