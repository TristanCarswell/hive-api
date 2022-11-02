package carswell.tristan.hive.hiveapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@SpringBootApplication(scanBasePackages = "carswell.tristan.hive")
//@EnableJpaRepositories("carswell.tristan.hive")
//@ComponentScan("carswell.tristan.hive")
//@EntityScan("carswell.tristan.hive")
@SpringBootApplication
@EnableJpaRepositories
public class HiveApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HiveApiApplication.class, args);
	}

}
