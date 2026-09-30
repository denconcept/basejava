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
        <c:if test="${not empty resume.uuid}">
            <input type="hidden" name="uuid" value="${resume.uuid}">
        </c:if>
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}" placeholder="ФИО"></dd>
        </dl>

        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"
                           placeholder="${type.title}"></dd>
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
                               value="${not empty resume.getSection(type) ?
                               resume.getSection(type).getDescription() : ''}"></dd>
                </dl>
            </c:if>
            <c:if test="${type.name() == 'ACHIEVEMENT' || type.name() == 'QUALIFICATIONS'}">
                <c:choose>
                    <c:when test="${not empty resume.getSection(type)}">
                        <c:forEach var="description" items="${resume.getSection(type).getDescription()}">
                            <dl>
                                <dd><input type="text" name="${type.name()}" size=200 value="${description}"></dd>
                            </dl>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <dl>
                            <dd><input type="text" name="${type.name()}" size=200></dd>
                        </dl>
                    </c:otherwise>
                </c:choose>
            </c:if>
            <c:if test="${type.name() == 'EXPERIENCE' || type.name() == 'EDUCATION'}">
                <c:choose>
                    <c:when test="${not empty resume.getSection(type)}">
                        <c:forEach var="company" items="${resume.getSection(type).getCompanies()}">
                            <br>
                            <dl>
                                <dd><input type="text" name="${type.name()}_companyTitle" size="100"
                                           value="${company.homePage.title}" placeholder="Название"></dd>
                            </dl>
                            <dl>
                                <dd><input type="text" name="${type.name()}_companyUrl" size="100"
                                           value="${company.homePage.url}" placeholder="Ссылка"></dd>
                            </dl>
                            <input type="hidden" name="${type.name()}_periodCount"
                                   value="${fn:length(company.periods)}">
                            <c:forEach var="period" items="${company.periods}">
                                <dl>
                                    <dd><input type="text" name="${type.name()}_periodStart" size="20"
                                               value="${period.formatedStartDate}" placeholder="Начало, ММ/ГГГГ"></dd>
                                </dl>
                                <dl>
                                    <dd><input type="text" name="${type.name()}_periodEnd" size="20"
                                               value="${period.formatedEndDate}" placeholder="Окончание, ММ/ГГГГ"></dd>
                                </dl>
                                <dl>
                                    <dd><input type="text" name="${type.name()}_periodTitle" size="100"
                                               value="${period.title}" placeholder="Заголовок"></dd>
                                </dl>
                                <dl>
                                    <dd><input type="text" name="${type.name()}_periodDescription" size="200"
                                               value="${period.description}" placeholder="Описание"></dd>
                                </dl>
                            </c:forEach>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <br>
                        <dl>
                            <dd><input type="text" name="${type.name()}_companyTitle" size="100"
                                       placeholder="Название"></dd>
                        </dl>
                        <dl>
                            <dd><input type="text" name="${type.name()}_companyUrl" size="100"
                                       placeholder="Ссылка"></dd>
                        </dl>
                        <input type="hidden" name="${type.name()}_periodCount" value="1">
                        <dl>
                            <dd><input type="text" name="${type.name()}_periodStart" size="20"
                                       placeholder="Начало, ММ/ГГГГ"></dd>
                        </dl>
                        <dl>
                            <dd><input type="text" name="${type.name()}_periodEnd" size="20"
                                       placeholder="Окончание, ММ/ГГГГ"></dd>
                        </dl>
                        <dl>
                            <dd><input type="text" name="${type.name()}_periodTitle" size="100"
                                       placeholder="Заголовок"></dd>
                        </dl>
                        <dl>
                            <dd><input type="text" name="${type.name()}_periodDescription" size="200"
                                       placeholder="Описание"></dd>
                        </dl>
                    </c:otherwise>
                </c:choose>
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