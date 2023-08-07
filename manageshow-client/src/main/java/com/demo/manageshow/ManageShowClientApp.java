package com.demo.manageshow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ManageShowClientApp {
	public static void main(String[] args) {
		SpringApplication.run(ManageShowClientApp.class, args);
		System.out.println("client started" + System.getProperty("show") + ", " + System.getProperty("ticket"));
		for(String a: args){
			System.out.println("Cmd line args" + a);
		}
	}

}
