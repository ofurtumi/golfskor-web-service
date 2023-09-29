package is.hi.hugbo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import is.hi.hugbo.model.Course;
import is.hi.hugbo.repositories.CourseRepository;

@Service
public class CourseService {
  @Autowired
  CourseRepository CR;

  public List<Course> findAll() {
    return CR.findAll();
  }

  public Course findById(long id) {
    return CR.findById(id);
  }

}
