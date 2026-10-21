<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/theme/${theme}.css">
    <link rel="stylesheet" href="css/styles.css">
    <link rel="stylesheet" href="css/view-resume-styles.css">
    <jsp:useBean id="resume" type="com.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<div class="scrollable-panel">
    <div class="form-wrapper">

        <div class="full-name">${resume.fullName}
            <a class="no-underline-anchor" href="resume?uuid=${resume.uuid}&action=edit&theme=${theme}">
                <img src="img/${theme}/edit.svg" aria-label="Редактировать резюме">
            </a>
        </div>

        <div class="contacts">
            <c:forEach var="contactEntry" items="${resume.contacts}">
                <jsp:useBean id="contactEntry"
                             type="java.util.Map.Entry<com.basejava.model.ContactType, java.lang.String>"/>
                <div><%=contactEntry.getKey().toHtml(contactEntry.getValue())%>
                </div>
            </c:forEach>
        </div>

        <div class="spacer"></div>

        <c:forEach var="sectionEntry" items="${resume.sections}">

            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>

            <div class="section">${type.title}</div>
            <c:choose>
                <c:when test="${type == 'OBJECTIVE'}">
                    <div class="position">${section.description}</div>
                </c:when>
                <c:when test="${type == 'PERSONAL'}">
                    <div class="qualities">${section.description}</div>
                </c:when>
                <c:when test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATIONS'}">
                    <ul class="list">
                        <c:forEach var="description" items="${section.description}">
                            <li>${description}</li>
                        </c:forEach>
                    </ul>
                </c:when>
                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:forEach var="company" items="${section.companies}">
                        <div class="section-wrapper">
                            <c:choose>
                                <c:when test="${empty company.homePage.url}">
                                    <div class="job-name">${company.homePage.title}</div>
                                </c:when>
                                <c:otherwise>
                                    <div class="job-name"><a class="contact-link"
                                                             href="${company.homePage.url}">${company.homePage.title}</a>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <c:forEach var="period" items="${company.periods}">
                                <div class="period-position">
                                    <div class="period">${period.period}</div>
                                    <div class="position">${period.title}</div>
                                </div>
                                <c:choose>
                                    <c:when test="${empty period.description}">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="description">${period.description}</div>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </div>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>

        <div class="footer-spacer"></div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>