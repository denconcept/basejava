package com.basejava.webapp;

import java.util.Arrays;

import com.basejava.webapp.model.Block;
import com.basejava.webapp.model.Contacts;
import com.basejava.webapp.model.ListSection;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.model.SectionType;
import com.basejava.webapp.model.StageSection;
import com.basejava.webapp.model.TextSection;

public class ResumeTestData {
    public static void main(String[] args) {

        Resume resume = new Resume("Григорий Кислин");
        System.out.println("\n" + resume.getFullName() + "\n");

        // Contacts
        String[] contactsData = {
                "+7 (921) 855-0482",
                "skype:grigory.kislin",
                "gkislin@yandex.ru",
                "",
                "",
                "",
                ""
        };
        for (int i = 0; i < Contacts.values().length; i++) {
            resume.addContact(Contacts.values()[i], contactsData[i]);
            System.out.println(Contacts.values()[i].getTitle() + resume.getContact(Contacts.values()[i]));
        }

        // TextSections
        String[] textContent = {
                "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям",
                "Аналитический склад ума, сильная логика, креативность, инициативность. " +
                        "Пурист кода и архитектуры."
        };
        for (int i = 0; i < textContent.length; i++) {
            ((TextSection) resume.sections.get(SectionType.values()[i])).setContent(textContent[i]);
            System.out.println("\n\n" + SectionType.values()[i].getTitle());
            System.out.println(((TextSection) resume.sections.get(SectionType.values()[i])).getContent());
        }

        // ListSection ACHIEVEMENT
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
        ((ListSection) resume.sections.get(SectionType.ACHIEVEMENT)).setContent(Arrays.asList(achievement));
        System.out.println("\n\n" + SectionType.ACHIEVEMENT.getTitle());
        for (String item : ((ListSection) resume.sections.get(SectionType.ACHIEVEMENT)).getContent()) {
            System.out.println(item);
        }

        // ListSection QUALIFICATIONS
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
        ((ListSection) resume.sections.get(SectionType.QUALIFICATIONS))
                .setContent(Arrays.asList(qualifications));
        System.out.println("\n\n" + SectionType.QUALIFICATIONS.getTitle());
        for (String item : ((ListSection) resume.sections.get(SectionType.QUALIFICATIONS)).getContent()) {
            System.out.println(item);
        }

        // StageSection EXPERIENCE
        String[][] experience = {
                {"– Java Online Projects", "Автор проекта.", "10/2013 - Сейчас",
                        "Создание, организация и проведение Java онлайн проектов и стажировок.\n"},
                {"– Wrike", "Старший разработчик (backend)", "10/2014 - 01/2016",
                        "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                                "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis)." +
                                "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.\n"},
                {"– RIT Center", "Java архитектор", "04/2012 - 10/2014",
                        "Организация процесса разработки системы ERP для разных окружений: " +
                                "релизная политика, версионирование, ведение CI (Jenkins), " +
                                "миграция базы (кастомизация Flyway), конфигурирование системы " +
                                "(pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы." +
                                "Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), " +
                                "сервисов общего назначения (почта, экспорт в pdf, doc, html). " +
                                "Интеграция Alfresco JLAN для online редактирование из браузера " +
                                "документов MS Office. Maven + plugin development, Ant, Apache Commons, " +
                                "Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python " +
                                "scripting, Unix shell remote scripting via ssh tunnels, PL/Python\n"},
                {"– Luxoft (Deutsche Bank)", "Ведущий программист", "12/2010 - 04/2012",
                        "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, " +
                                "Spring MVC, SmartGWT, GWT, Jasper, Oracle). " +
                                "Реализация клиентской и серверной части CRM." +
                                "Реализация RIA-приложения для администрирования, " +
                                "мониторинга и анализа результатов в области алгоритмического трейдинга. " +
                                "JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.\n"},
                {"– Yota", "Ведущий специалист", "06/2008 - 12/2010",
                        "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" " +
                                "(GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4," +
                                "JSP, JMX, JMS, Maven2). Реализация администрирования, статистики " +
                                "и мониторинга фреймворка. Разработка online JMX клиента " +
                                "(Python/ Jython, Django, ExtJS)\n"},
                {"– Enkata", "Разработчик ПО", "03/2007 - 06/2008",
                        "Реализация клиентской (Eclipse RCP) и серверной " +
                                "(JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного " +
                                "J2EE приложения (OLAP, Data mining).\n"},
                {"– Siemens AG", "Разработчик ПО", "01/2005 - 02/2007",
                        "Разработка информационной модели, проектирование интерфейсов, " +
                        "реализация и отладка ПО на мобильной IN платформе Siemens " +
                        "@vantage (Java, Unix).\n"},
                {"– Alcatel", "Инженер по аппаратному и программному тестированию", "09/1997 - 01/2005",
                        "Создание, организация и проведение Java онлайн проектов и стажировок."},
        };
        System.out.println("\n\n" + SectionType.EXPERIENCE.getTitle());
        for (String[] strings : experience) {
            Block block = new Block();
            block.setTitle(strings[0]);
            block.setSubTitle(strings[1]);
            block.setDate(strings[2]);
            block.setContent(strings[3]);
            ((StageSection) resume.sections.get(SectionType.EXPERIENCE)).blocks.add(block);
        }
        for (Block item : ((StageSection) resume.sections.get(SectionType.EXPERIENCE)).blocks) {
            System.out.println(item.getTitle());
            System.out.println(item.getSubTitle());
            System.out.println(item.getDate());
            System.out.println(item.getContent());
        }

        // StageSection EDUCATION
        String[][] education = {
                {"– Coursera", "'Functional Programming Principles in Scala' by Martin Odersky",
                        "03/2013 - 05/2013\n"},
                {"– Luxoft", "Курс 'Объектно-ориентированный анализ ИС. " +
                        "Концептуальное моделирование на UML.'", "03/2011 - 04/2011\n"},
                {"– Siemens AG", "3 месяца обучения мобильным IN сетям (Берлин)",
                        "01/2005 - 04/2005\n"},
                {"– Alcatel", "6 месяцев обучения цифровым телефонным сетям (Москва)",
                        "09/1997 - 03/1998\n"},
                {"– Санкт-Петербургский национальный исследовательский университет информационных" +
                        "технологий, механики и оптики", "Инженер (программист Fortran, C) Аспирантура" +
                        "(программист С, С++)", "09/1987 - 07/1993 09/1993 - 07/1996\n"},
                {"– Заочная физико-техническая школа при МФТИ", "Закончил с отличием", "09/1984 - 06/1987"},
        };
        System.out.println("\n\n" + SectionType.EDUCATION.getTitle());
        for (String[] strings : education) {
            Block block = new Block();
            block.setTitle(strings[0]);
            block.setSubTitle(strings[1]);
            block.setDate(strings[2]);
            ((StageSection) resume.sections.get(SectionType.EDUCATION)).blocks.add(block);
        }
        for (Block item : ((StageSection) resume.sections.get(SectionType.EDUCATION)).blocks) {
            System.out.println(item.getTitle());
            System.out.println(item.getSubTitle());
            System.out.println(item.getDate());
        }
    }
}
