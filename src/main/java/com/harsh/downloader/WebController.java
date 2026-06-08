package com.harsh.downloader;


import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class WebController {

	@GetMapping("/")
	public String index(Model model) throws IOException {
		
		// Output already stored in file
		model.addAttribute("output", Helper.getListFromFileAsString());
		return "index"; // Returns index.html from resources/templates
	}

}
