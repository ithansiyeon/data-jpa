package study.datajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

@EnableJpaAuditing
@SpringBootApplication
public class DataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}

	@Bean
	public AuditorAware<String> auditorProvider() {
		//Holder에서 걔 id 가져와서 꺼내야돼
		//httpSession에서 꺼내서 userId 넣어야 돼
		//인터페이스에서 메소드가 하나면 람다로 바꿀 수 있음
		return () -> Optional.of(UUID.randomUUID().toString());
	}

}
