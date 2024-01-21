package is.hi.hugbo.model;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "rounds")
public class Round {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne()
  @JsonIgnore
  private Course course;
  private String courseName;

  @ManyToOne()
  @JsonIgnore
  private User user;
  private String username;

  private int[] holes;

  public Round() {
  }

  public Round(Course course, User user, int[] holes) {
    this.course = course;
    this.courseName = course.getCourseName();
    this.user = user;
    this.username = user.getUsername();
    this.holes = holes;
  }

  public long getId() {
    return id;
  }

  public Course getCourse() {
    return course;
  }

  public User getUser() {
    return user;
  }

  public int[] getHoles() {
    return holes;
  }

  public int getScore() {
    return Arrays.stream(holes).sum();
  }

  public void setHoles(int[] holes) {
    this.holes = holes;
  }

  public String getCourseName() {
    return courseName;
  }

  public String getUsername() {
    return username;
  }
}
