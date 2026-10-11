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

    static final String DATA_DIR = "./data";
    static final String FILE_PATH = DATA_DIR + "/url.txt";
    static final String HISTORY_FILE_PATH = DATA_DIR + "/history.txt";

    /**
     * Adds a URL to the current list and records it in history.
     * Both files avoid duplicate URLs.
     *
     * @param url URL to add
     * @throws IOException if a file operation fails
     */
    static void addToList(String url) throws IOException {

        if (url == null || url.trim().isEmpty()) {
            return;
        }

        url = url.trim();

        // Add URL to the current list
        addUniqueUrlToFile(FILE_PATH, url);

        // Add URL to the history list
        addUniqueUrlToFile(HISTORY_FILE_PATH, url);

        System.out.println("URL processed: " + url);
    }

    /**
     * Adds a URL to the top of a file if it does not already exist.
     * Existing URLs retain their positions.
     *
     * @param filePath path of the file to update
     * @param url URL to add
     * @throws IOException if a file operation fails
     */
    private static void addUniqueUrlToFile(String filePath, String url)
            throws IOException {

        ensureDataDirectoryExists();

        List<String> urlList = new ArrayList<>();

        // Create file if it does not exist
        File file = new File(filePath);
        file.createNewFile();

        // Read existing URLs
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                urlList.add(currentLine);
            }
        }

        // Add new URL to the top if it does not already exist
        if (!urlList.contains(url)) {
            urlList.add(0, url);
        }

        // Remove duplicates while preserving order
        Set<String> uniqueUrls = new LinkedHashSet<>(urlList);

        // Rewrite the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            for (String line : uniqueUrls) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    /**
     * Reads URLs from a specified file.
     *
     * @param filePath path of the file to read
     * @return URLs separated by newlines
     * @throws IOException if a file operation fails
     */
    private static String getListFromFile(String filePath) throws IOException {

        ensureDataDirectoryExists();

        List<String> urlList = new ArrayList<>();

        // Create file if it does not exist
        File file = new File(filePath);
        file.createNewFile();

        // Read URLs
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                urlList.add(currentLine);
            }
        }

        // Convert URLs into a string
        StringBuilder sb = new StringBuilder();

        for (String item : urlList) {
            sb.append(item).append(System.lineSeparator());
        }

        return sb.toString();
    }

    /**
     * Gets the current URL list.
     */
    static String getListFromFile() throws IOException {
        return getListFromFile(FILE_PATH);
    }

    /**
     * Gets the URL history.
     */
    static String getHistoryFromFile() throws IOException {
        return getListFromFile(HISTORY_FILE_PATH);
    }

    /**
     * Ensures the data directory exists.
     */
    private static void ensureDataDirectoryExists() throws IOException {

        File directory = new File(DATA_DIR);

        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException(
                    "Failed to create data directory: " + DATA_DIR);
        }

        if (!directory.isDirectory()) {
            throw new IOException(
                    "Data path is not a directory: " + DATA_DIR);
        }
    }
}