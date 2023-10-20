package is.hi.hugbo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "rounds")
public class Round {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne()
  private Course course;

  @ManyToOne()
  private User user;

  private int[] holes;

  public Round() {
  }

  public Round(int hole1, int hole2, int hole3, int hole4, int hole5, int hole6, int hole7, int hole8, int hole9, 
               int hole10, int hole11, int hole12, int hole13, int hole14, int hole15, int hole16, int hole17, int hole18) {
    this.holes = new int[] { hole1, hole2, hole3, hole4, hole5, hole6, hole7, hole8, hole9, 
                             hole10, hole11, hole12, hole13, hole14, hole15, hole16, hole17, hole18};
  }

  public Round(Course course, User user, int[] holes) {
    this.course = course;
    this.user = user;
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

  public void setHoles(int[] holes) {
    this.holes = holes;
  }
}
