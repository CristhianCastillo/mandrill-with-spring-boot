package com.ptesa.demoemailmandril;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DemoEmailMandrilApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoEmailMandrilApplication.class, args);
	}

}
