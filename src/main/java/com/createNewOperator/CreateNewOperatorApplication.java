package com.createNewOperator;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CreateNewOperatorApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(CreateNewOperatorApplication.class, args);
		System.out.println("Server started...........");

	}

}
