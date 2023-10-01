package is.hi.hugbo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import is.hi.hugbo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);

  boolean existsByUsername(String username);
}
