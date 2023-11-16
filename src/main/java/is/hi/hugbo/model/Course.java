package is.hi.hugbo.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "courses", uniqueConstraints = {
    @UniqueConstraint(columnNames = "courseName"),

})
public class Course {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String courseName;

  private Integer[] coursePars;

  @OneToMany(mappedBy = "course")
  private List<Round> rounds;

  public Course() {
  }

  public Course(String courseName, Integer[] pars) {
    this.courseName = courseName;
    this.coursePars = pars;
  }

  public long getId() {
    return id;
  }

  public List<Round> getRounds() {
    return rounds;
  }

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public Integer[] getCoursePars() {

    return coursePars;
  }

  public void setCoursePars(Integer[] coursePars) {
    this.coursePars = coursePars;
  }

  public Integer calculateSumOfPars(boolean front) {
    Integer sum = 0;
    if (this.coursePars == null) {
      return sum;
    }
    if (front) {
      for (Integer i = 0; i < 9; i++) {
        sum = sum + this.coursePars[i];
      }
    } else {
      for (Integer i = 0; i < this.coursePars.length; i++) {
        sum = sum + this.coursePars[i];
      }
    }
    return sum;
  }
}
