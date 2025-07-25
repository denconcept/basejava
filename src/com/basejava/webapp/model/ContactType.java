package com.basejava.webapp.model;

public enum ContactType {

    PHONE("Тел.: "),
    SKYPE("Skype: "),
    MAIL("Почта: "),
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль StackOverflow"),
    HOME("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
