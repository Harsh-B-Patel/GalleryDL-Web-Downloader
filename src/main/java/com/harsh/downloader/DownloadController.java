package com.harsh.downloader;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class DownloadController {
	
	String FILE_PATH = Helper.FILE_PATH;
	
	
	@PostMapping("/download")
	public String download(Model model) {
		try {

			// Get List from URL.txt
			String exePath = "gallery-dl.exe";
			ProcessBuilder processBuilder = new ProcessBuilder(exePath, "-i", FILE_PATH);
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
			FileWriter fw = new FileWriter(FILE_PATH, false); // 'false' disables append mode
			fw.write(""); // write nothing = clear
			fw.close();
			System.out.println("File cleared successfully.");

		} catch (Exception e) {
			model.addAttribute("output2", "Error: " + e.getMessage());
		}
		return "index2";
	}

}
