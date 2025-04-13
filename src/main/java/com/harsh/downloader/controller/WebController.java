package com.harsh.downloader.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {


	String filePath = "./url.txt";

	@GetMapping("/")
	public String index(Model model) throws IOException {
		model.addAttribute("output", getListFromFile());
		return "index"; // Returns index.html from resources/templates
	}

	@PostMapping("/clearList")
	public String clearList(Model model) throws IOException {
		// Purge List
		FileWriter fw = new FileWriter(filePath, false); // 'false' disables append mode
		fw.write(""); // write nothing = clear
		fw.close();
		System.out.println("File cleared successfully.");	
		model.addAttribute("output", "File cleared successfully");
		return "index";		
	}

	@PostMapping("/download")
	public String download(Model model) {
		try {

			// Get List from URL.txt
			String exePath = "gallery-dl.exe";
			ProcessBuilder processBuilder = new ProcessBuilder(exePath, "-i", filePath);
			processBuilder.redirectErrorStream(true);
			Process process = processBuilder.start();

			// model.addAttribute("output1", "Starting Download...");
			StringBuilder output = new StringBuilder();
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
				String line;
				while ((line = reader.readLine()) != null) {
					output.append(line).append("\n");
					System.out.print(line);
				}
			}
			int exitCode = process.waitFor();
			// model.addAttribute("output2", "Exit Code: " + exitCode + "\n" +
			// output.toString());
			model.addAttribute("output2", output.toString());

			// Purge List
			FileWriter fw = new FileWriter(filePath, false); // 'false' disables append mode
			fw.write(""); // write nothing = clear
			fw.close();
			System.out.println("File cleared successfully.");

		} catch (Exception e) {
			model.addAttribute("output2", "Error: " + e.getMessage());
		}
		return "index2";
	}

	@PostMapping("/addToList")
	public String addToList(@RequestParam(name = "url") String url, Model model) throws IOException {
		List<String> urlListOld = new ArrayList<String>();
		List<String> urlList = new ArrayList<String>();
		// String filePath = "./url.txt";

		// Create a new URL.txt file on filepath if it does not exist
		File file = new File(filePath);
		file.createNewFile();

		try {

			// get URL from Front end
			System.out.println("WebController.download:- URL to be added is : " + url);

			// Get Existing URLs from file called URL.txt
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			String currentLine;
			while ((currentLine = reader.readLine()) != null) {
				urlListOld.add(currentLine);
			}
			reader.close();

			// Add new URl to top of file., Dont add if Duplicate
			if (!urlListOld.contains(url)) {
				urlListOld.add(0, url);
				//urlListOld.addFirst(url);
			}

			// Remove duplicate - Dont add if duplicate.
			Set<String> set = new LinkedHashSet<>(urlListOld);
			urlList = new ArrayList<>(set);

			// Clear file and write arraylist to it.
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
			for (String line : urlList) {
				writer.write(line);
				writer.newLine();
			}
			writer.close();

			// print URL list from ArrayList back on Front end
			model.addAttribute("output", getListFromFile());

		} catch (Exception e) {

		}
		return "index";
	}

	public String getListFromFile() throws IOException {

		List<String> urlList = new ArrayList<String>();
		// String filePath = "./url.txt";

		// Create a new URL.txt file on filepath if it does not exist
		File file = new File(filePath);
		file.createNewFile();

		// Get Existing URLs from file called URL.txt
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String currentLine;
		while ((currentLine = reader.readLine()) != null) {
			urlList.add(currentLine);
		}
		reader.close();

		// print URL list from file back on Front end
		StringBuilder sb = new StringBuilder();
		for (String item : urlList) {
			sb.append(item).append("\n");
		}
		String result = sb.toString();
		System.out.println(result);
		return result;

	}
	
	
	
	
	
}
