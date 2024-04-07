package dbmsforeveread.foreveread;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCaching
//@EnableAsync
public class ForevereadApplication {
	public static void main(String[] args) {
		SpringApplication.run(ForevereadApplication.class, args);
	}

}
