package com.urise.webapp;

import com.urise.webapp.model.*;

import java.util.EnumMap;
import java.util.Map;

public class ResumeTestData {
    private static Map<ContactsType, String> contacts = new EnumMap<>(ContactsType.class);
    private static Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

    private static Resume testResume = new Resume("UUID_1", "Grigory Kislin");

    private static void addContacts() {
        contacts.put(ContactsType.TEL_NUMBER, "+7(921)855-0482");
        contacts.put(ContactsType.SKYPE, "grigory.kislin");
        contacts.put(ContactsType.EMAIL, "gkislin@yandex.ru");
        contacts.put(ContactsType.LINKED_IN, "https://www.linkedin.com/in/gkislin");
        contacts.put(ContactsType.GITHUB, "https://github.com/gkislin");
        contacts.put(ContactsType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        contacts.put(ContactsType.HOME_SITE, "http://gkislin.ru/");

        testResume.setContacts(contacts);
    }

    private static void addObjectiveAndPersonal() {
        sections.put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям \n"));
        sections.put(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры. \n"));
    }

    private static void addAchievementAndQualifications() {
        ListTextSection sectionAchievement = new ListTextSection();

        sectionAchievement.addContent("""
                + С 2013 года: разработка проектов "Разработка Web приложения","Java Enterprise", \s
                "Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP).\s
                Удаленное взаимодействие (JMS/AKKA)". Организация онлайн стажировок и ведение проектов.\s
                Более 1000 выпускников.""");
        sectionAchievement.addContent("+ Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. \n" +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        sectionAchievement.addContent("""
                + Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM.\s
                Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке:\s
                Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей,\s
                интеграция CIFS/SMB java сервера.""");
        sectionAchievement.addContent("""
                + Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, \s +
                Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.""");
        sectionAchievement.addContent("""
                + Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов\s
                (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии\s
                через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга\s
                системы по JMX (Jython/ Django).""");
        sectionAchievement.addContent("+ Реализация протоколов по приему платежей всех основных платежных системы России \n" +
                "(Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        sections.put(SectionType.ACHIEVEMENT, sectionAchievement);

        ListTextSection sectionQualifications = new ListTextSection();

        sectionQualifications.addContent("+ JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        sectionQualifications.addContent("+ Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        sectionQualifications.addContent("+ DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        sectionQualifications.addContent("+ MySQL, SQLite, MS SQL, HSQLDB");
        sectionQualifications.addContent("+ Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        sectionQualifications.addContent("+ XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        sectionQualifications.addContent("""
                + Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis,\s
                Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice,\s
                GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit,\s
                Selenium (htmlelements).""");
        sectionQualifications.addContent("+ Python: Django.");
        sectionQualifications.addContent("+ JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        sectionQualifications.addContent("+ Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        sectionQualifications.addContent("""
                + Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX,\s
                DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP,\s
                OAuth1, OAuth2, JWT.""");
        sectionQualifications.addContent("+ Инструменты: Maven + plugin development, Gradle, настройка Ngnix.");
        sectionQualifications.addContent("+ администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, \n" +
                "iReport, OpenCmis, Bonita, pgBouncer.");
        sectionQualifications.addContent("+ Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, \n" +
                "архитектурных шаблонов, UML, функционального программирования");
        sectionQualifications.addContent("+ Родной русский, английский \"upper intermediate\"");
        sections.put(SectionType.QUALIFICATIONS, sectionQualifications);

        testResume.setSections(sections);

    }

    private static void addExperienceAndEducation() {
        ListOrganizationSection organizationsExperience = new ListOrganizationSection();

        Organization organization = new Organization(new OrganizationLink("Java Online Projects", "http://javaops.ru/"));
        organization.addContent("10/2013", "Сейчас", "Автор проекта.\n" +
                "Создание, организация и проведение Java онлайн проектов и стажировок.");
        organizationsExperience.addOrganization(organization);

        organization = new Organization(new OrganizationLink("Wrike", "https://www.wrike.com/"));
        organization.addContent("10/2014", " 01/2016", """
                Старший разработчик (backend)
                Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring,\s
                MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.""");
        organizationsExperience.addOrganization(organization);

        organization = new Organization(new OrganizationLink("RIT Center", ""));
        organization.addContent("04/2012", "10/2014", """
                Java архитектор
                Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование,\s
                ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx),\s
                AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2,\s
                1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html).\s
                Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office.\s
                Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis,\s
                OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python""");
        organizationsExperience.addOrganization(organization);

        organization = new Organization(new OrganizationLink("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/"));
        organization.addContent("12/2010", " 04/2012", """
                Ведущий программист
                Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper,\s
                Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования,\s
                мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT,\s
                ExtGWT (GXT), Highstock, Commet, HTML5.""");
        organizationsExperience.addOrganization(organization);

        organization = new Organization(new OrganizationLink("Yota", "https://www.yota.ru/"));
        organization.addContent("06/2008", "12/2010", """
                Ведущий специалист
                Дизайн и имплементация Java EE фреймворка для отдела "Платежные Системы" (GlassFish v2.1, v3, OC4J,\s
                EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и\s
                мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)""");
        organizationsExperience.addOrganization(organization);

        organization = new Organization(new OrganizationLink("Enkata", "http://enkata.com/"));
        organization.addContent("03/2007", "06/2008", """
                Разработчик ПО
                Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей\s
                кластерного J2EE приложения (OLAP, Data mining).""");
        organizationsExperience.addOrganization(organization);

        organization = new Organization(new OrganizationLink("Siemens AG", "https://www.siemens.com/ru/ru/home.html"));
        organization.addContent("01/2005", "02/2007", """
                Разработчик ПО
                Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN\s
                платформе Siemens @vantage (Java, Unix).""");
        organizationsExperience.addOrganization(organization);

        organization = new Organization(new OrganizationLink("Alcatel", "http://www.alcatel.ru/"));
        organization.addContent("09/1997", "01/2005", "Инженер по аппаратному и программному тестированию\n" +
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).");
        organizationsExperience.addOrganization(organization);

        sections.put(SectionType.EXPERIENCE, organizationsExperience);

        ListOrganizationSection organizationsEducation = new ListOrganizationSection();

        organization = new Organization(new OrganizationLink("Coursera", "https://www.coursera.org/course/progfun"));
        organization.addContent("03/2013", "05/2013", "\"Functional Programming Principles in Scala\" by Martin Odersky");
        organizationsEducation.addOrganization(organization);

        organization = new Organization(new OrganizationLink("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366"));
        organization.addContent("03/2011", "04/2011", "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"");
        organizationsEducation.addOrganization(organization);

        organization = new Organization(new OrganizationLink("Siemens AG", "http://www.siemens.ru/"));
        organization.addContent("01/2005", "04/2005", "3 месяца обучения мобильным IN сетям (Берлин)");
        organizationsEducation.addOrganization(organization);

        organization = new Organization(new OrganizationLink("Alcatel", "http://www.alcatel.ru/"));
        organization.addContent("09/1997", "03/1998", "6 месяцев обучения цифровым телефонным сетям (Москва)");
        organizationsEducation.addOrganization(organization);

        organization = new Organization(new OrganizationLink("Санкт-Петербургский национальный исследовательский \n" +
                "университет информационных технологий, механики и оптики", "http://www.ifmo.ru/"));
        organization.addContent("09/1993", "07/1996", "Аспирантура (программист С, С++)");
        organization.addContent("09/1987", "07/1993", "Инженер (программист Fortran, C)");
        organizationsEducation.addOrganization(organization);

        organization = new Organization(new OrganizationLink("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru/"));
        organization.addContent("09/1984", "06/1987", "Закончил с отличием");
        organizationsEducation.addOrganization(organization);

        sections.put(SectionType.EDUCATION, organizationsEducation);
    }

    public static void main(String[] args) {

        addContacts();
        addObjectiveAndPersonal();
        addAchievementAndQualifications();
        addExperienceAndEducation();

        System.out.println("\nUUID - " + testResume.getUuid() + "\nИмя Фамилия - " + testResume.getFullName() + "\n");
        testResume.getContacts().forEach((k, v) -> System.out.println(k.getTitle() + " " + v));
        System.out.println();
        testResume.getSections().forEach((k, v) -> System.out.println(k.getTitle() + "\n" + v.getContent()));
    }
}
