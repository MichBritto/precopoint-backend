package br.com.precopoint.PrecoPoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PrecoPointApplication {

	//private static final Logger logger = LoggerFactory.getLogger(PrecoPointApplication.class);
	private static Logger logger = LogManager.getLogger(PrecoPointApplication.class);
	public static void main(String[] args) {

		logger.info("Iniciando aplicação..");
		SpringApplication.run(PrecoPointApplication.class, args);
		System.setProperty("isThreadContextMapInheritable", "true");
		logger.info("Aplicação iniciada.");
	}

}
