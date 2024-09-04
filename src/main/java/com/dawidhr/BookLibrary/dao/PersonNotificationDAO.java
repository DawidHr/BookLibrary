package com.dawidhr.BookLibrary.dao;

public interface PersonNotificationDAO {
    boolean isPersonNotifiedIn7Days(long personId);
}
