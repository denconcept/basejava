<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/edit.svg"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.basejava.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br>
        </c:forEach>
    </p>
</section>
<section>
    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<com.basejava.model.SectionType, com.basejava.model.AbstractSection>"/>
        <h3>${sectionEntry.key.title}</h3>
        <c:if test="${sectionEntry.key.name() == 'OBJECTIVE' || sectionEntry.key.name() == 'PERSONAL'}">
            ${sectionEntry.value.description}
        </c:if>
        <c:if test="${sectionEntry.key.name() == 'ACHIEVEMENT' || sectionEntry.key.name() == 'QUALIFICATIONS'}">
            <c:forEach var="description" items="${sectionEntry.value.description}">
                <ul>
                    <li>${description}</li>
                </ul>
            </c:forEach>
        </c:if>
        <c:if test="${sectionEntry.key.name() == 'EXPERIENCE' || sectionEntry.key.name() == 'EDUCATION'}">
            <c:forEach var="company" items="${sectionEntry.value.companies}">
                <a href="${company.homePage.url}">${company.homePage.title}</a><br>
                <c:forEach var="period" items="${company.periods}">
                    <ul>
                        <li>
                                ${period.period}<br>
                                ${period.title}<br>
                            <c:if test="${not empty period.description}">
                                ${period.description}
                            </c:if>
                        </li>
                    </ul>
                </c:forEach>
                <br><br>
            </c:forEach>
        </c:if>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>