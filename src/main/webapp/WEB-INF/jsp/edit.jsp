<%@ page import="com.basejava.model.ContactType" %>
<%@ page import="com.basejava.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
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
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>

        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>

        <h3>Секции:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <dl>
                <dt>${type.title}</dt>
            </dl>
            <c:if test="${type.name() == 'OBJECTIVE' || type.name() == 'PERSONAL'}">
                <dl>
                    <dd><input type="text" name="${type.name()}" size=200
                               value="${resume.getSection(type).getDescription()}"></dd>
                </dl>
            </c:if>
            <c:if test="${type.name() == 'ACHIEVEMENT' || type.name() == 'QUALIFICATIONS'}">
                <c:forEach var="description" items="${resume.getSection(type).getDescription()}">
                    <dl>
                        <dd><input type="text" name="${type.name()}" size=200 value="${description}"></dd>
                    </dl>
                </c:forEach>
            </c:if>
            <c:if test="${type.name() == 'EXPERIENCE' || type.name() == 'EDUCATION'}">
                <c:forEach var="company" items="${resume.getSection(type).getCompanies()}">
                    <br>
                    <dl>
                        <dd><input type="text" name="${type.name()}_companyTitle" size="100"
                                   value="${company.homePage.title}"></dd>
                    </dl>
                    <dl>
                        <dd><input type="text" name="${type.name()}_companyUrl" size="100"
                                   value="${company.homePage.url}"></dd>
                    </dl>
                    <input type="hidden" name="${type.name()}_periodCount" value="${fn:length(company.periods)}">
                    <c:forEach var="period" items="${company.periods}">
                        <dl>
                            <dd><input type="text" name="${type.name()}_periodDates" size="20"
                                       value="${period.period}"></dd>
                        </dl>
                        <dl>
                            <dd><input type="text" name="${type.name()}_periodTitle" size="100"
                                       value="${period.title}"></dd>
                        </dl>
                        <dl>
                            <dd><input type="text" name="${type.name()}_periodDescription" size="200"
                                       value="${period.description}"></dd>
                        </dl>
                    </c:forEach>
                </c:forEach>
            </c:if>
            <br>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="button" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>