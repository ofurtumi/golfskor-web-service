package is.hi.hugbo.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "courses", uniqueConstraints = {
    @UniqueConstraint(columnNames = "courseName")
})
public class Course {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String courseName;

  @OneToMany(mappedBy = "course")
  private List<Round> rounds;

  public Course() {
  }

  public Course(String courseName) {
    this.courseName = courseName;
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
}
