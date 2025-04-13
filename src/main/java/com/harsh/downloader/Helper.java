package com.harsh.downloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Helper {
	
	static String FILE_PATH = "./url.txt";
	
	static String getListFromFile() throws IOException {

		List<String> urlList = new ArrayList<String>();
		// String filePath = "./url.txt";

		// Create a new URL.txt file on filepath if it does not exist
		File file = new File(FILE_PATH);
		file.createNewFile();

		// Get Existing URLs from file called URL.txt
		BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
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
