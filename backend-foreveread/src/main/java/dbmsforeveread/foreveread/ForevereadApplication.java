package dbmsforeveread.foreveread;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ForevereadApplication {
	public static void main(String[] args) {
		SpringApplication.run(ForevereadApplication.class, args);
	}

}
