package com.lalalala.s1.home;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorController {
	
	
	@ExceptionHandler(Throwable.class)
	public String test() {
		System.out.println("Exception ---!!!!");
		return "redirect:/";
	}

}
