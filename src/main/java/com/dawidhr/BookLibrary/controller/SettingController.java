package com.dawidhr.BookLibrary.controller;

import com.dawidhr.BookLibrary.model.Settings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller

public class SettingController {

    @GetMapping("/settings")
    public String getSettingPageAction(Model model) {
        Settings generalForm = new Settings();
        model.addAttribute("generalForm", generalForm);
        return "setting/main.html";
    }

    @PostMapping("/settings/generalFormProcess")
    public String generalFormProcess(@RequestBody Settings generalForm) {
        //
        return "setting/main.html";
    }
}
