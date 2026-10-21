package com.basejava;

import com.basejava.model.Company;
import com.basejava.model.CompanySection;
import com.basejava.model.ContactType;
import com.basejava.model.ListSection;
import com.basejava.model.Resume;
import com.basejava.model.SectionType;
import com.basejava.model.TextSection;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static com.basejava.util.DateUtil.NOW;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume resume = createResume("uuid1", "Name");
        printResume(resume);
    }

    public static Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        ContactType[] contactTypes = ContactType.values();
        for (int i = 0; i < contactTypes.length; i++) {
            resume.setContact(contactTypes[i], getContacts()[i]);
        }
        String[] textContent = getTexts();
        for (int i = 0; i < textContent.length; i++) {
            resume.setSection(SectionType.values()[i], new TextSection(textContent[i]));
        }
        resume.setSection(SectionType.ACHIEVEMENT, getAchievements());
        resume.setSection(SectionType.QUALIFICATIONS, getQualifications());
        resume.setSection(SectionType.EXPERIENCE, createCompanySection(getExperience()));
        resume.setSection(SectionType.EDUCATION, createCompanySection(getEducation()));
        return resume;
    }

    public static void printResume(Resume resume) {
        System.out.println("\n" + resume.getFullName() + "\n");
        for (ContactType type : ContactType.values()) {
            System.out.println(type.getTitle() + resume.getContact(type));
        }
        for (int i = 0; i < getTexts().length; i++) {
            printTextSection(resume, SectionType.values()[i]);
        }
        printListSection(resume, SectionType.ACHIEVEMENT);
        printListSection(resume, SectionType.QUALIFICATIONS);
        printCompanySection(resume, SectionType.EXPERIENCE);
        printCompanySection(resume, SectionType.EDUCATION);
    }

    private static String[] getContacts() {
        return new String[]{
                "+7 (912) 345-678",
                "skype:name",
                "name@gmail.com",
                "",
                "",
                "",
                ""
        };
    }

    private static String[] getTexts() {
        return new String[]{
                "Objective info",
                "Personal info"
        };
    }

    private static ListSection getAchievements() {
        String[] achievement = {
                "Achievement",
                "Achievement",
                "Achievement"
        };
        return new ListSection(Arrays.asList(achievement));
    }

    private static ListSection getQualifications() {
        String[] qualifications = {
                "Qualification",
                "Qualification",
                "Qualification"
        };
        return new ListSection(Arrays.asList(qualifications));
    }

    private static CompanySection createCompanySection(String[][] companies) {
        CompanySection companySection = new CompanySection();
        for (String[] items : companies) {
            Company company = new Company(items[0], items[1]);
            for (int i = 0; items.length - i != 2; i += 4) {
                Company.Period period = new Company.Period(
                        formatDate(items[i + 2]),
                        items[i + 3].equals("Сейчас") ? NOW : formatDate(items[i + 3]),
                        items[i + 4], items[i + 5]);
                company.setPeriod(period);
            }
            companySection.addCompany(company);
        }
        return companySection;
    }

    private static String[][] getExperience() {
        return new String[][]{
                {"Experience", "https://example.com", "01/2020", "Сейчас", "Position", "Description"},
                {"Experience", "https://example.com", "01/2015", "01/2020", "Position", "Description"},
                {"Experience", "https://example.com", "01/2010", "01/2015", "Position", "Description"}
        };
    }

    private static String[][] getEducation() {
        return new String[][]{
                {"Education", "https://example.com", "01/2015", "01/2020", "Position", "", "01/2010", "01/2015", "Position", ""},
                {"Education", "https://example.com", "01/2000", "01/2005", "Position", "",},
                {"Education", "https://example.com", "01/1995", "01/2000", "Position", "",},
        };
    }

    private static void printTextSection(Resume resume, SectionType type) {
        System.out.println("\n\n" + type.getTitle());
        System.out.println(((TextSection) resume.getSection(type)).getDescription());
    }

    private static void printListSection(Resume resume, SectionType type) {
        System.out.println("\n\n" + type.getTitle());
        for (String item : ((ListSection) resume.getSection(type)).getDescription()) {
            System.out.println(item);
        }
    }

    private static void printCompanySection(Resume resume, SectionType type) {
        System.out.println("\n\n" + type.getTitle());
        for (Company company : ((CompanySection) resume.getSection(type)).getCompanies()) {
            System.out.println(company.getHomePage().getTitle());
            for (Company.Period period : company.getPeriods()) {
                System.out.print(period.getPeriod());
                System.out.println(period.getTitle());
                System.out.print(period.getDescription());
            }
        }
    }

    private static LocalDate formatDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        YearMonth ym = YearMonth.parse(date, formatter);
        return ym.atDay(1);
    }
}
