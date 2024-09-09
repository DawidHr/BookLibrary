package com.dawidhr.BookLibrary.dao;

import com.dawidhr.BookLibrary.model.Settings;

public interface SettingsDAO {
    Settings getSettings();
    void updateSettings(Settings settings);
    void insertSettings(Settings settings);
}
