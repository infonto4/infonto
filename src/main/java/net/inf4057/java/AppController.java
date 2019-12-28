package net.inf4057.java;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

public class AppController {
	@RequestMapping("/")
	public String viewHomePage (Model model) {	
		return "index";	
	}
}
