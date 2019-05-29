package event.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Viachaslau Tsitsiankou
 * Date on 05/24/2019.
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "event.service.repository.h2")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
