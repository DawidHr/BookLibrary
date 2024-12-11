package com.dawidhr.BookLibrary.dao;

import com.dawidhr.BookLibrary.model.Settings;
import com.dawidhr.BookLibrary.repository.SettingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
        Optional<Settings> settingsFromDb = settingRepository.findById(settings.getSettingsId());
        if (settingsFromDb.isPresent()) {
            Settings dbSetting = settingsFromDb.get();
            dbSetting.setNotificationRate(settings.getNotificationRate());
            dbSetting.setPrice(settings.getPrice());
            dbSetting.setNotificationRate(settings.getNotificationRate());
            settingRepository.save(dbSetting);
        }
        throw new EntityNotFoundException();
    }

    @Override
    public void insertSettings(Settings settings) {
        //TODO
        settingRepository.save(settings);
    }
}
