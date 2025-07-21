package com.basejava.webapp.model;

import java.util.List;

public class ListSection implements Section {

    private List<String> content;

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }
}
