package lk.earth.earthuniversity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class HarvestSuperApplication {

	public static void main(String[] args) {
		SpringApplication.run(HarvestSuperApplication.class, args);
	}

}
