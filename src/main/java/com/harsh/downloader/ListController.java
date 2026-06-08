package com.harsh.downloader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ListController {

	String FILE_PATH = Helper.FILE_PATH;
	String FOLDER_PATH = Helper.FOLDER_PATH;

	@PostMapping("/clearList")
	public String clearList(Model model) throws Exception {
		
		// Purge List
		Helper.clearFile(FILE_PATH);
		Helper.clearFile(FOLDER_PATH);

		System.out.println("File cleared successfully.");
		model.addAttribute("output", "File cleared successfully");
		return "index";
	}

	@PostMapping("/addToList")
	public String addToList(@RequestParam(name = "url") String url, @RequestParam(name = "folder") String folder,
			Model model) throws IOException {

		try {
			System.out.println("Adding URL: " + url);
			Helper.addLineToFile(FILE_PATH, url, true); // prevent duplicate

			System.out.println("Adding Folder: " + folder);
			Helper.addLineToFile(FOLDER_PATH, folder, false); // allow duplicates

		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("output", Helper.getListFromFileAsString());
		return "index";
	}


}
