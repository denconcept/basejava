package com.basejava.webapp.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Link implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private final String title;
    private final String url;

    public Link(String title, String url) {
        Objects.requireNonNull(title, "title must not be null");
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(title, link.title) && Objects.equals(url, link.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, url);
    }

    @Override
    public String toString() {
        return "Link{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
