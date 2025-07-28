package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Company {

    private final Link homePage;
    private final List<Period> periods = new ArrayList<>();

    public Company(String title, String url) {
        Objects.requireNonNull(title, "title must not be null");
        this.homePage = new Link(title, url);
    }

    public Link getHomePage() {
        return homePage;
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
        return Objects.equals(homePage, company.homePage) && Objects.equals(periods, company.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, periods);
    }

    @Override
    public String toString() {
        return "Company{" +
                "homePage=" + homePage +
                ", periods=" + periods +
                '}';
    }
}