package com.example.day2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


// @RestController
@SpringBootApplication
public class Day2Application {

	public static void main(String[] args) {
		SpringApplication.run(Day2Application.class, args);
		
	}
	// @GetMapping("/hello")
	// public String welcome(@RequestParam(value ="name",defaultValue = "hjh") String name){
	// 	 return String.format("Hello %s!", name);
	// }

}
