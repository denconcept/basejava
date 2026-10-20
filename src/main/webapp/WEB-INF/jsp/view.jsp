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
    <hr>
    <table cellpadding="2">
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <tr>
                <td><h3><a name="type.name">${type.title}</a></h3></td>
                <c:if test="${type == 'OBJECTIVE'}">
                    <td>
                        <h3>${section.description}</h3>
                    </td>
                </c:if>
            </tr>
            <c:if test="${type != 'OBJECTIVE'}">
                <c:choose>
                    <c:when test="${type == 'PERSONAL'}">
                        <tr>
                            <td>${section.description}</td>
                        </tr>
                    </c:when>
                    <c:when test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATIONS'}">
                        <tr>
                            <td>
                                <ul>
                                    <c:forEach var="description" items="${section.description}">
                                        <li>${description}</li>
                                    </c:forEach>
                                </ul>
                            </td>
                        </tr>
                    </c:when>
                    <c:when test="${type == 'EXPERIENCE' || type == 'EDUCATION'}">
                        <c:forEach var="company" items="${section.companies}">
                            <tr>
                                <td>
                                    <c:choose>
                                        <c:when test="${empty company.homePage.url}">
                                            <h3>${company.homePage.title}</h3>
                                        </c:when>
                                        <c:otherwise>
                                            <h3><a href="${company.homePage.url}">${company.homePage.title}</a></h3>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <c:forEach var="period" items="${company.periods}">
                                <tr>
                                    <td>${period.period}</td>
                                    <td><b>${period.title}</b><br>${period.description}</td>
                                </tr>
                            </c:forEach>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </c:if>
        </c:forEach>
    </table>
    <button onclick="window.history.back()">Ok</button>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>