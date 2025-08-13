package com.basejava.webapp.model;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompanySection extends AbstractSection {

    @Serial
    private static final long serialVersionUID = 1L;
    private final List<Company> companies = new ArrayList<>();

    public List<Company> getCompanies() {
        return new ArrayList<>(companies);
    }

    public void addCompany(Company company) {
        companies.add(company);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CompanySection that = (CompanySection) o;
        return Objects.equals(companies, that.companies);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(companies);
    }

    @Override
    public String toString() {
        return "CompanySection{" +
                "companies=" + companies +
                '}';
    }
}