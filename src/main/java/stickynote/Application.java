package stickynote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Spring boot application class to start the application.
 *
 * @author Niek Palm
  */
@ComponentScan
@EnableAutoConfiguration
@Configuration
public class Application {

    /**
     * Default main method to start the application
     *
     * @param args parameters to pass
     * @throws Exception any exception
     */
    public static void main(final String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}
