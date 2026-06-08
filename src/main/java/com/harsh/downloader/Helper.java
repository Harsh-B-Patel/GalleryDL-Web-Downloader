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

public class Helper {

	static String FILE_PATH = "./url.txt";
	static String FOLDER_PATH = "./folder.txt";
	
	static List<String> getListFromFile(String path) throws IOException {

		List<String> list = new ArrayList<String>();

		// Create a new .txt file on filepath if it does not exist
		File file = new File(path);
		file.createNewFile();

		// Get Existing from file called .txt
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String currentLine;
		while ((currentLine = reader.readLine()) != null) {
			list.add(currentLine);
		}
		reader.close();

		return list;

	}

	static String getListFromFileAsString() throws IOException {
		

		List<String> urlList = getListFromFile(FILE_PATH);
		List<String> folderList = getListFromFile(FOLDER_PATH);
		
		// print list from file back on Front end
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < urlList.size(); i++) {
			sb.append(folderList.get(i)).append("  :  ").append(urlList.get(i)).append("\n");
		}

		String result = sb.toString();
		System.out.println(result);
		return result;

	}
	

	static void addLineToFile(String filePath, String value, boolean preventDuplicate) throws IOException {

		System.out.println("Staring addLineToFile  for file " + filePath + "  for " + value );
		File file = new File(filePath);
		file.createNewFile();

		List<String> existingLines = new ArrayList<>();

		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String currentLine;
		while ((currentLine = reader.readLine()) != null) {
			existingLines.add(currentLine);
		}
		reader.close();

		// Add to top (if not duplicate)
		if (!preventDuplicate || !existingLines.contains(value)) {
			existingLines.add(0, value);
		}

		// Remove duplicates if needed
		if (preventDuplicate) {
			Set<String> set = new LinkedHashSet<>(existingLines);
			existingLines = new ArrayList<>(set);
		}

		BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
		for (String line : existingLines) {
			writer.write(line);
			writer.newLine();
		}
		writer.close();
		System.out.println("Wrote addLineToFile  for file " + filePath + "  for " + value );

	}
	
	static void clearFile(String path) throws Exception {
	    try (FileWriter fw = new FileWriter(path, false)) {
	        fw.write("");
	        fw.close();
	    }
	}

	
	

	
	

}
