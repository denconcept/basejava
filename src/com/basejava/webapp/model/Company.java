package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Company {

    private final String title;
    private final List<Period> periods = new ArrayList<>();

    public Company(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public List<Period> getPeriods() {
        return new ArrayList<>(periods);
    }

    public void setPeriod(Period period) {
        periods.add(period);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(title, company.title) && periods.equals(company.periods);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(title);
        result = 31 * result + periods.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Company{" +
                "title='" + title + '\'' +
                ", periods=" + periods +
                '}';
    }
}