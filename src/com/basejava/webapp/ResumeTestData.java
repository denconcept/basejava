package com.basejava.webapp;

import java.util.Arrays;

import com.basejava.webapp.model.Company;
import com.basejava.webapp.model.CompanySection;
import com.basejava.webapp.model.ContactType;
import com.basejava.webapp.model.ListSection;
import com.basejava.webapp.model.Period;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.model.SectionType;
import com.basejava.webapp.model.TextSection;

public class ResumeTestData {
    public static void main(String[] args) {

        Resume resume = new Resume("Григорий Кислин");
        System.out.println("\n" + resume.getFullName() + "\n");

        // Contacts
        String[] contacts = getContacts();
        for (int i = 0; i < ContactType.values().length; i++) {
            resume.addContact(ContactType.values()[i], contacts[i]);
            System.out.println(ContactType.values()[i].getTitle() +
                    resume.getContact(ContactType.values()[i]));
        }

        // Objective, Personal
        String[] textContent = getTexts();
        for (int i = 0; i < textContent.length; i++) {
            createTextSection(resume, textContent[i], SectionType.values()[i]);
            printTextSection(resume, SectionType.values()[i]);
        }

        // Achievements
        resume.addSection(SectionType.ACHIEVEMENT, getAchievements());
        printListSection(resume, SectionType.ACHIEVEMENT);

        // Qualifications
        resume.addSection(SectionType.QUALIFICATIONS, getQualifications());
        printListSection(resume, SectionType.QUALIFICATIONS);

        // Experience
        createCompanySection(resume, getExperience(), SectionType.EXPERIENCE);
        printCompanySection(resume, SectionType.EXPERIENCE);

        // Education
        createCompanySection(resume, getEducation(), SectionType.EDUCATION);
        printCompanySection(resume, SectionType.EDUCATION);
    }

    private static String[] getContacts() {
        return new String[] {
                "+7 (921) 855-0482",
                "skype:grigory.kislin",
                "gkislin@yandex.ru",
                "",
                "",
                "",
                ""
        };
    }

    private static String[] getTexts() {
        return new String[] {
                "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям",
                "Аналитический склад ума, сильная логика, креативность, инициативность. " +
                        "Пурист кода и архитектуры."
        };
    }

    private static void createTextSection(Resume resume, String textContent, SectionType type) {
        TextSection textSection = new TextSection(textContent);
        resume.addSection(type, textSection);
    }

    private static void printTextSection(Resume resume, SectionType type) {
        System.out.println("\n\n" + type.getTitle());
        System.out.println(((TextSection) resume.getSection(type)).getDescription());
    }

    private static ListSection getAchievements() {
        String[] achievement = {
                "– Организация команды и успешная реализация Java проектов для сторонних заказчиков: " +
                        "приложения автопарк на стеке Spring Cloud/микросервисы, " +
                        "система мониторинга показателей спортсменов на Spring Boot, " +
                        "участие в проекте МЭШ на Play-2, многомодульный Spring Boot + " +
                        "Vaadin проект для комплексных DIY смет",
                "– С 2013 года: разработка проектов " +
                        "\"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. " +
                        "Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                        "Удаленное взаимодействие (JMS/AKKA)\"." +
                        "Организация онлайн стажировок и ведение проектов. " +
                        "Более 3500 выпускников.",
                "– Реализация двухфакторной аутентификации для онлайн платформы " +
                        "управления проектами Wrike. " +
                        "Интеграция с Twilio,DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "– Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                        "Интеграция с 1С, Bonita BPM, CMIS, LDAP. " +
                        "Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. " +
                        "Разработка SSO аутентификации и авторизации различных ERP модулей, " +
                        "интеграция CIFS/SMB java сервера.",
                "– Реализация c нуля Rich Internet Application приложения на стеке технологий " +
                        "JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, " +
                        "Highstock для алгоритмического трейдинга.",
                "– Создание JavaEE фреймворка для отказоустойчивого взаимодействия " +
                        "слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). " +
                        "Сбор статистики сервисов и информации о состоянии через систему " +
                        "мониторинга Nagios. Реализация онлайн клиента для администрирования " +
                        "и мониторинга системы по JMX (Jython/Django).",
                "– Реализация протоколов по приему платежей " +
                        "всех основных платежных системы России " +
                        "(Cyberplat, Eport, Chronopay, Сбербанк), " +
                        "Белоруcсии(Erip, Osmp) и Никарагуа."
        };
        return new ListSection(Arrays.asList(achievement));
    }

    private static ListSection getQualifications() {
        String[] qualifications = {
                "– JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "– Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "– DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, " +
                        "Oracle, MySQL, SQLite, MS SQL, HSQLDB",
                "– Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy",
                "– XML/XSD/XSLT, SQL, C/C++, Unix shell scripts",
                "– Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, " +
                        "Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), " +
                        "Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, " +
                        "Eclipse SWT, JUnit, Selenium (htmlelements).",
                "– Python: Django.",
                "– JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js",
                "– Scala: SBT, Play2, Specs2, Anorm, Spray, Akka",
                "– Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, " +
                        "StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, " +
                        "HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.",
                "– Инструменты: Maven + plugin development, Gradle, настройка Ngnix",
                "– Администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, " +
                        "Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer",
                "– Отличное знание и опыт применения концепций ООП, SOA, шаблонов " +
                        "проектрирования, архитектурных шаблонов, UML, функционального программирования",
                "– Родной русский, английский \"upper intermediate\""
        };
        return new ListSection(Arrays.asList(qualifications));
    }

    private static void printListSection(Resume resume, SectionType type) {
        System.out.println("\n\n" + type.getTitle());
        for (String item : ((ListSection) resume.getSection(type)).getDescription()) {
            System.out.println(item);
        }
    }

    private static String[][] getExperience() {
        return new String[][] {
                {"– Java Online Projects", "https://javaops.ru", "10/2013", "Сейчас", "Автор проекта.",
                        "Создание, организация и проведение Java онлайн проектов и стажировок."},
                {"\n\n– Wrike", "https://www.wrike.com", "10/2014", "01/2016", "Старший разработчик (backend).",
                        "Проектирование и разработка онлайн платформы управления проектами Wrike" +
                                "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis)." +
                                "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."},
                {"\n\n– RIT Center", "https://www.ritcenter.com", "04/2012", "10/2014", "Java архитектор.",
                        "Организация процесса разработки системы ERP для разных окружений: " +
                                "релизная политика, версионирование, ведение CI (Jenkins), " +
                                "миграция базы (кастомизация Flyway), конфигурирование системы " +
                                "(pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы." +
                                "Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), " +
                                "сервисов общего назначения (почта, экспорт в pdf, doc, html). " +
                                "Интеграция Alfresco JLAN для online редактирование из браузера " +
                                "документов MS Office. Maven + plugin development, Ant, Apache Commons, " +
                                "Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python " +
                                "scripting, Unix shell remote scripting via ssh tunnels, PL/Python"},
                {"\n\n– Luxoft (Deutsche Bank)", "https://luxoft.ru", "12/2010", "04/2012", "Ведущий программист.",
                        "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, " +
                                "Spring MVC, SmartGWT, GWT, Jasper, Oracle). " +
                                "Реализация клиентской и серверной части CRM." +
                                "Реализация RIA-приложения для администрирования, " +
                                "мониторинга и анализа результатов в области алгоритмического трейдинга. " +
                                "JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."},
                {"\n\n– Yota", "https://www.yota.ru", "06/2008", "12/2010", "Ведущий специалист.",
                        "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" " +
                                "(GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4," +
                                "JSP, JMX, JMS, Maven2). Реализация администрирования, статистики " +
                                "и мониторинга фреймворка. Разработка online JMX клиента " +
                                "(Python/ Jython, Django, ExtJS)"},
                {"\n\n– Enkata", "https://www.enkata.ru", "03/2007", "06/2008", "Разработчик ПО.",
                        "Реализация клиентской (Eclipse RCP) и серверной " +
                                "(JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного " +
                                "J2EE приложения (OLAP, Data mining)."},
                {"\n\n– Siemens AG", "https://www.siemens.com", "01/2005", "02/2007", "Разработчик ПО.",
                        "Разработка информационной модели, проектирование интерфейсов, " +
                                "реализация и отладка ПО на мобильной IN платформе Siemens " +
                                "@vantage (Java, Unix)."},
                {"\n\n– Alcatel", "https://www.alcatel.ru", "09/1997", "01/2005",
                        "Инженер по аппаратному и программному тестированию.",
                        "Создание, организация и проведение Java онлайн проектов и стажировок.\n"}
        };
    }

    private static String[][] getEducation() {
        return new String[][] {
                {"– Coursera", "https://www.coursera.org", "03/2013", "05/2013",
                        "'Functional Programming Principles in Scala' by Martin Odersky", ""},
                {"\n– Luxoft", "https://luxoft.ru", "03/2011", "04/2011",
                        "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'",
                        ""},
                {"\n– Siemens AG", "https://www.siemens.com", "01/2005", "04/2005",
                        "3 месяца обучения мобильным IN сетям (Берлин)", ""},
                {"\n– Alcatel", "https://www.alcatel.ru", "09/1997", "03/1998",
                        "6 месяцев обучения цифровым телефонным сетям (Москва)", ""},
                {"\n– Санкт-Петербургский национальный исследовательский университет" +
                        "информационных технологий, механики и оптики", "https://itmo.ru",
                        "09/1993", "07/1996", "Аспирантура (программист С, С++)", "",
                        "09/1987", "07/1993", "Инженер (программист Fortran, C)", ""},
                {"\n– Заочная физико-техническая школа при МФТИ", "https://mipt.ru",
                        "09/1984", "06/1987",
                        "Закончил с отличием", ""},
        };
    }

    private static void createCompanySection(Resume resume, String[][] companies, SectionType type) {
        CompanySection companySection = new CompanySection();
        for (String[] items : companies) {
            Company company = new Company(items[0], items[1]);
            for (int i = 0; items.length - i != 2; i += 4) {
                Period period = new Period(items[i + 2], items[i + 3], items[i + 4], items[i + 5]);
                company.setPeriod(period);
            }
            companySection.addCompany(company);
        }
        resume.addSection(type, companySection);
    }

    private static void printCompanySection(Resume resume, SectionType type) {
        System.out.println("\n\n" + type.getTitle());
        for (Company company : ((CompanySection) resume.getSection(type)).getCompanies()) {
            System.out.println(company.getHomePage().getTitle());
            for (Period period : company.getPeriods()) {
                System.out.print(period.getPeriod());
                System.out.println(period.getTitle());
                System.out.print(period.getDescription());
            }
        }
    }
}
