package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {

    private final List<String> description;

    public ListSection(List<String> description) {
        Objects.requireNonNull(description, "description must not be null");
        this.description = description;
    }

    public List<String> getDescription() {
        return new ArrayList<>(description);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(description);
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "description=" + description +
                '}';
    }
}