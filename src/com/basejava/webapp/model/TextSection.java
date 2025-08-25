package com.basejava.webapp.model;

import java.io.Serial;
import java.util.Objects;

public class TextSection extends Section {

    @Serial
    private static final long serialVersionUID = 1L;
    private String description;

    @SuppressWarnings("unused")
    public TextSection() {}

    public TextSection(String description) {
        Objects.requireNonNull(description, "description must not be null");
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(description);
    }

    @Override
    public String toString() {
        return "TextSection{" +
                "description='" + description + '\'' +
                '}';
    }
}