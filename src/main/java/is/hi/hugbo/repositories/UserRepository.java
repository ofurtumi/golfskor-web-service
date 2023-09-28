package is.hi.hugbo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import is.hi.hugbo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
  List<User> findByUsername(String username);

  Boolean existsByUsername(String username);
}
