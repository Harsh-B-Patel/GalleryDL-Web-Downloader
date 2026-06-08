package com.harsh.downloader;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class DownloadController {

	String FILE_PATH = Helper.FILE_PATH;
	String FOLDER_PATH = Helper.FOLDER_PATH;

	@PostMapping("/download")
	public String download(Model model) {
		try {
					
			/* Run windows EXE
			 * String exePath = "./gallery-dl.exe";
			 * 
			 * String command = "gallery-dl"; ProcessBuilder processBuilder = new
			 * ProcessBuilder(command, "-i", FILE_PATH);
			 */
			
			List<String> urls = Helper.getListFromFile(FILE_PATH);
			List<String> folders = Helper.getListFromFile(FOLDER_PATH);
			
			if (folders.size() != urls.size()) {
	            throw new IllegalStateException("Folder list and URL list size mismatch");
			}
			
			
			StringBuilder output = new StringBuilder();
	        for (int i = 0; i < urls.size(); i++) {
	            String folderName = folders.get(i);
	            String url = urls.get(i);

	            String downloadDir = FOLDER_PATH + "/" + folderName;

	            // Build the process with gallery-dl and custom Bunkr endpoint ProcessBuilder
	            ProcessBuilder processBuilder = new ProcessBuilder(
	                "gallery-dl",
	                "-o", "extractor.bunkr.endpoint=/api/_001_v2",
	                "-D", downloadDir,
	                url
	            );

	            processBuilder.redirectErrorStream(true);
	            Process process = processBuilder.start();

	            try (BufferedReader reader =
	                     new BufferedReader(new InputStreamReader(process.getInputStream()))) {
	                String line;
	                while ((line = reader.readLine()) != null) {
	                    output.append(line).append("\n");
	                }
	            }

	            int exitCode = process.waitFor();
	            output.append("Finished: ").append(url)
	                  .append(" (exit ").append(exitCode).append(")\n\n");
	        }
			
			
			
	
			/*
			 * // Build the process with gallery-dl and custom Bunkr endpoint ProcessBuilder
			 * processBuilder = new ProcessBuilder( "gallery-dl", "-o",
			 * "extractor.bunkr.endpoint=/api/_001_v2", "-D", FOLDER_PATH, "-i", FILE_PATH
			 * );
			 * 
			 * processBuilder.redirectErrorStream(true); Process process =
			 * processBuilder.start();
			 * 
			 * // model.addAttribute("output1", "Starting Download..."); // Capture output
			 * StringBuilder output = new StringBuilder(); try (BufferedReader reader = new
			 * BufferedReader(new InputStreamReader(process.getInputStream()))) { String
			 * line; while ((line = reader.readLine()) != null) {
			 * output.append(line).append("\n"); System.out.print(line); } } int exitCode =
			 * process.waitFor();
			 */
			
			
			model.addAttribute("output2", output.toString());

			
			// Purge List file
			Helper.clearFile(FILE_PATH);
			Helper.clearFile(FOLDER_PATH);
			
			System.out.println("File cleared successfully.");

		} catch (Exception e) {
			model.addAttribute("output2", "Error: " + e.getMessage());
		}
		return "index2";
	}

}
