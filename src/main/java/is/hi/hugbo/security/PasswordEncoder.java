package is.hi.hugbo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*
 * Configuration class that sets up password encoding with BCrypt.
 * This class creates a bean that can be used throughout the application
 * to encode passwords before storing them and to verify passwords during authentication.
 */
@Configuration
public class PasswordEncoder {
  /*
   * Creates a BCryptPasswordEncoder bean.
   * The BCryptPasswordEncoder uses the BCrypt hashing function for password
   * encoding.
   * 
   * @return A BCryptPasswordEncoder instance that can be used for password
   * hashing and verification.
   */
  @Bean
  public BCryptPasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

}
