package com.harsh.downloader;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DownloadController {

    private final DownloadService downloadService;

    public DownloadController(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    /**
     * Manually trigger downloads through the web interface.
     */
    @PostMapping("/download")
    public String download(Model model) {

        try {
            model.addAttribute(
                    "output2",
                    downloadService.download()
            );

        } catch (Exception e) {

            e.printStackTrace();

            model.addAttribute(
                    "output2",
                    "Error during download: " + e.getMessage()
            );
        }

        return "index2";
    }

    /**
     * Automatically download the current URL list every night
     * at 2:30 AM Toronto time.
     */
    @Scheduled(
        cron = "0 30 2 * * *",
        zone = "America/Toronto"
    )
    public void nightlyDownload() {

        System.out.println("======================================");
        System.out.println("Starting scheduled nightly download...");
        System.out.println("======================================");

        try {

            String output = downloadService.download();

            System.out.println("Nightly download output:");
            System.out.println(output);

        } catch (Exception e) {

            System.err.println("Nightly download failed!");
            e.printStackTrace();
        }

        System.out.println("Nightly download job finished.");
    }
}