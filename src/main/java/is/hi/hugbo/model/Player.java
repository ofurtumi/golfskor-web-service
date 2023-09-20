package is.hi.hugbo.model;

import jakarta.persistence.*;

/**
 * 
 */
@Entity
@Table(name = "players")
public class Player {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "name")
  private String name;

  @Column(name = "age")
  private Integer age;

  @Column(name = "active")
  private Boolean active;

  public Player() {
  }

  public Player(String name, Integer age) {
    this.name = name;
    this.age = age;
    this.active = true;
  }

  public Integer getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAge() {
    return this.age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public Boolean isActive() {
    return this.active;
  }

  public void toggleActive() {
    this.active = !this.active;
  }
}
