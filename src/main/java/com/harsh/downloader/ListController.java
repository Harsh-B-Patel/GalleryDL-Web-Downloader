package com.harsh.downloader;

import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ListController {

    @PostMapping("/clearList")
    public String clearList(Model model) throws IOException {

        // Clear the current URL list only
        try (FileWriter fw = new FileWriter(Helper.FILE_PATH, false)) {
            fw.write("");
        }

        model.addAttribute("output", "File cleared successfully");

        return "index";
    }

    @PostMapping("/addToList")
    public String addToList(
            @RequestParam(name = "url") String url,
            Model model) throws IOException {

        // Update current list and history
        Helper.addToList(url);

        // Display the current URL list
        model.addAttribute("output", Helper.getListFromFile());

        return "index";
    }


    @GetMapping("/viewCurrentList")
public String viewCurrentList(Model model) throws IOException {

    model.addAttribute("output", Helper.getListFromFile());
    model.addAttribute("listTitle", "Current Download List:");

    return "index";
}

@GetMapping("/viewHistory")
public String viewHistory(Model model) throws IOException {

    model.addAttribute("output", Helper.getHistoryFromFile());
    model.addAttribute("listTitle", "URL History:");

    return "index";
}
}