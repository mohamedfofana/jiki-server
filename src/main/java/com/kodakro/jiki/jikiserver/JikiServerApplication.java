package com.kodakro.jiki.jikiserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(scanBasePackages = "com.kodakro.jiki")
//(exclude = { SecurityAutoConfiguration.class })
@EnableCaching
public class JikiServerApplication {

	/**
	 * The second problem, the missing BCryptPasswordEncoder instance, 
	 * we solve by implementing a method that generates an instance of BCryptPasswordEncoder. 
	 * This method must be annotated with @Bean and we will add it in the Application
	 * @return
	 */
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	public static void main(String[] args) {
		SpringApplication.run(JikiServerApplication.class, args);
	}

}
