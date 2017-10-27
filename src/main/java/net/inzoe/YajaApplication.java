package net.inzoe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class YajaApplication {

	public static void main(String[] args) {
		SpringApplication.run(YajaApplication.class, args);
	}
}
