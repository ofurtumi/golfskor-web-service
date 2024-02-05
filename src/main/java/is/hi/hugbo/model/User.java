package is.hi.hugbo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "username")
})
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String username;

  private String password;

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
  private List<Round> rounds;

  public User() {
  }

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  @JsonIgnore
  public String getPassword() {
    return password;
  }

  public List<Round> getRounds() {
    return rounds;
  }

  public void setRounds(List<Round> rounds) {
    this.rounds = rounds;
  }
}
