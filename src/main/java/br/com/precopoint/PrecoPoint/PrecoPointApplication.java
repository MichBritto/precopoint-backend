package br.com.precopoint.PrecoPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PrecoPointApplication {

	private static final Logger logger = LoggerFactory.getLogger(PrecoPointApplication.class);
	public static void main(String[] args) {

		logger.info("Iniciando aplicação..");
		SpringApplication.run(PrecoPointApplication.class, args);
		logger.info("Aplicação iniciada.");
	}

}
