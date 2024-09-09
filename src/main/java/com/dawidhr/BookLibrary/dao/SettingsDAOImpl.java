package com.dawidhr.BookLibrary.dao;

import com.dawidhr.BookLibrary.model.Settings;
import com.dawidhr.BookLibrary.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SettingsDAOImpl implements SettingsDAO {
    @Autowired
    SettingRepository settingRepository;

    @Override
    public Settings getSettings() {
        return settingRepository.findAll().stream().findFirst().orElse(null);
    }

    @Override
    public void updateSettings(Settings settings) {
        //TODO
    }

    @Override
    public void insertSettings(Settings settings) {
        //TODO
    }
}
