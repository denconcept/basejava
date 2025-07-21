package com.basejava.webapp.model;

public enum Contacts {

    PHONE("Тел.: "),
    SKYPE("Skype: "),
    MAIL("Почта: "),
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль StackOverflow"),
    HOME("Домашняя");

    private final String title;

    Contacts(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
