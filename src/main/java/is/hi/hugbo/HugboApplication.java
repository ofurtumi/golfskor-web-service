package is.hi.hugbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class HugboApplication {

  /***
   * Starts the Golf Tracker
   * 
   * @param args
   */
  public static void main(String[] args) {
    SpringApplication.run(HugboApplication.class, args);
  }

}
