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
	public String clearList(Model model) throws IOException {
		// Purge List
		FileWriter fw = new FileWriter(FILE_PATH, false); // 'false' disables append mode
		fw.write(""); // write nothing = clear
		fw.close();

		FileWriter fw2 = new FileWriter(FOLDER_PATH, false); // 'false' disables append mode
		fw2.write(""); // write nothing = clear
		fw2.close();

		System.out.println("File cleared successfully.");
		model.addAttribute("output", "File cleared successfully");
		return "index";
	}

	@PostMapping("/addToList")
	public String addToList(@RequestParam(name = "url") String url, @RequestParam(name = "folder") String folder,
			Model model) throws IOException {

		// String filePath = "./url.txt";



		try {

			// Create a new URL.txt file on filepath if it does not exist
			File file = new File(FILE_PATH);
			file.createNewFile();
			
			List<String> urlListOld = new ArrayList<String>();
			List<String> urlList = new ArrayList<String>();

			// get URL from Front end
			System.out.println("WebController.download:- URL to be added is : " + url);

			// Get Existing URLs from file called URL.txt
			BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
			String currentLine;
			while ((currentLine = reader.readLine()) != null) {
				urlListOld.add(currentLine);
			}
			reader.close();

			// Add new URl to top of file., Dont add if Duplicate
			if (!urlListOld.contains(url)) {
				urlListOld.add(0, url);
				// urlListOld.addFirst(url);
			}

			// Remove duplicate - Dont add if duplicate.
			Set<String> set = new LinkedHashSet<>(urlListOld);
			urlList = new ArrayList<>(set);

			// Clear file and write arraylist to it.
			BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
			for (String line : urlList) {
				writer.write(line);
				writer.newLine();
			}
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

		try {
			// Create a new URL.txt file on filepath if it does not exist
			File file = new File(FOLDER_PATH);
			file.createNewFile();
			
			List<String> urlListOld = new ArrayList<String>();
			List<String> urlList = new ArrayList<String>();

			// get URL from Front end
			System.out.println("WebController.download:- URL to be added is : " + folder);

			// Get Existing URLs from file called URL.txt
			BufferedReader reader = new BufferedReader(new FileReader(FOLDER_PATH));
			String currentLine;
			while ((currentLine = reader.readLine()) != null) {
				urlListOld.add(currentLine);
			}
			reader.close();

			// Add new folder name to top of file.
			urlListOld.add(0, folder);

			// Clear file and write arraylist to it.
			BufferedWriter writer = new BufferedWriter(new FileWriter(FOLDER_PATH));
			for (String line : urlListOld) {
				writer.write(line);
				writer.newLine();
			}
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

		// print URL list from ArrayList back on Front end
		model.addAttribute("output", Helper.getListFromFile());

		return "index";
	}

}
