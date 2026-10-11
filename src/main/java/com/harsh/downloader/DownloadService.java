package com.harsh.downloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;

@Service
public class DownloadService {

    /**
     * Executes gallery-dl using the current URL list.
     *
     * @return download output
     * @throws Exception if the download process fails
     */
    public String download() throws Exception {

        String filePath = Helper.FILE_PATH;

        // Ensure the URL list exists before starting the download.
        File file = new File(filePath);

        if (!file.exists()) {
            return "No download list found.";
        }

        // Execute gallery-dl
        ProcessBuilder processBuilder = new ProcessBuilder(
                "gallery-dl",
                "-i",
                filePath
        );

        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        StringBuilder output = new StringBuilder();

        // Capture output
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {

            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
                System.out.println(line);
            }
        }

        int exitCode = process.waitFor();

        if (exitCode == 0) {

            // Clear the current list only after a successful execution.
            try (FileWriter fw = new FileWriter(filePath, false)) {
                fw.write("");
            }

            output.append(System.lineSeparator())
                  .append("Download completed successfully. List cleared.");

            System.out.println("Download completed successfully.");

        } else {

            output.append(System.lineSeparator())
                  .append("gallery-dl exited with code: ")
                  .append(exitCode)
                  .append(". URL list preserved.");

            System.err.println(
                    "gallery-dl failed with exit code: " + exitCode
            );
        }

        return output.toString();
    }
}