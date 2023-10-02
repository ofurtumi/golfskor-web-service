package is.hi.hugbo.interfaces;

import java.util.List;

import is.hi.hugbo.model.Course;
import is.hi.hugbo.model.Round;

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

  /**
   * Method to save round to user, needed for one to many relationship
   * 
   * @param courseId - The id the course to update
   * @param round    - The round to add
   */
  public void addRound(long courseId, Round round);

}
