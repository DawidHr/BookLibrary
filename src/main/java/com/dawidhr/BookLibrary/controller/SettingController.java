package com.dawidhr.BookLibrary.controller;

import com.dawidhr.BookLibrary.dao.SettingsDAO;
import com.dawidhr.BookLibrary.model.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class SettingController {

    @Autowired
    SettingsDAO settingsDAO;

    @GetMapping("/settings")
    public String getSettingPageAction(Model model) {
        Settings generalForm = settingsDAO.getSettings();
        if(generalForm == null) {
            generalForm = new Settings();
        }
        model.addAttribute("generalForm", generalForm);
        return "setting/main.html";
    }

    @PostMapping("/settings/generalFormProcess")
    public String generalFormProcess(@RequestBody Settings generalForm) {
        //TODO add validation for Settings
        Settings settings = settingsDAO.getSettings();
        if(settings == null) {
            settingsDAO.insertSettings(generalForm);
        } else {
            settingsDAO.updateSettings(generalForm);
        }
        return "setting/main.html";
    }
}
