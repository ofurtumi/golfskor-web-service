package is.hi.hugbo.interfaces;

import java.util.List;

import is.hi.hugbo.model.Course;

public interface ICourseService {
  /**
   * Method to get all courses
   * 
   * @return A list of all courses
   */
  public List<Course> findAll();

  /**
   * Method to retrieve a course by id
   * 
   * @param id - id of course
   * @return A course with the given id or null if not found
   */
  public Course findById(long id);
}
