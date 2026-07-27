package io.github.yellowfantt.libaryApi;

import io.github.yellowfantt.libaryApi.model.Autor;
import io.github.yellowfantt.libaryApi.repository.AutorRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.naming.Context;
import java.time.LocalDate;

@SpringBootApplication
@EnableJpaAuditing
public class ApiApplication {

	public static void main(String[] args) {

		  SpringApplication.run(ApiApplication.class, args);



	}



}
