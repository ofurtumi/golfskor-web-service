package is.hi.hugbo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import is.hi.hugbo.model.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
  List<Player> findByAge(Integer age);

  List<Player> findByName(String name);

  List<Player> findByActive(Boolean active);
}
