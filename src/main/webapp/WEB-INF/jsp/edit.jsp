<%@ page import="com.basejava.model.ContactType" %>
<%@ page import="com.basejava.model.SectionType" %>
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
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <c:if test="${not empty resume.uuid}">
            <input type="hidden" name="uuid" value="${resume.uuid}">
        </c:if>
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}" placeholder="ФИО"></dd>
        </dl>
        <h3>Контакты</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"
                           placeholder="${type.title}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <h3>${type.title}</h3>
            <c:choose>
                <c:when test="${type == 'OBJECTIVE'}">
                    <input type="text" name="${type.name()}" size="100" value="${section.description}">
                </c:when>
                <c:when test="${type == 'PERSONAL'}">
                    <textarea name="${type.name()}" cols="100" rows="5">${section.description}</textarea>
                </c:when>
                <c:when test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATIONS'}">
                    <c:forEach var="description" items="${section.description}">
                        <br><input type="text" name="${type.name()}" size="100" value="${description}">
                    </c:forEach>
                </c:when>
                <c:when test="${type == 'EXPERIENCE' || type == 'EDUCATION'}">
                    <c:forEach var="company" items="${section.companies}" varStatus="counter">
                        <dl>
                            <dd><input type="text" name="${type.name()}" size="100" value="${company.homePage.title}"
                                       placeholder="Название"></dd>
                        </dl>
                        <dl>
                            <dd><input type="text" name="${type.name()}url" size="100" value="${company.homePage.url}"
                                       placeholder="Ссылка"></dd>
                        </dl>
                        <br>
                        <div style="margin-left: 30px">
                            <c:forEach var="period" items="${company.periods}">
                                <dl>
                                    <dd>
                                        <input type="text" name="${type.name()}${counter.index}startDate" size="30"
                                               value="${period.formatedStartDate}" placeholder="Начало, ММ/ГГГГ">
                                    </dd>
                                </dl>
                                <dl>
                                    <dd>
                                        <input type="text" name="${type.name()}${counter.index}endDate" size="30"
                                               value="${period.formatedEndDate}" placeholder="Окончание, ММ/ГГГГ">
                                    </dd>
                                </dl>
                                <dl>
                                    <dd>
                                        <input type="text" name="${type.name()}${counter.index}title" size="75"
                                               value="${period.title}" placeholder="Заголовок">
                                    </dd>
                                </dl>
                                <dl>
                                    <dd>
                                        <textarea name="${type.name()}${counter.index}description" rows="2"
                                                  cols="75">${period.description}</textarea>
                                    </dd>
                                </dl>
                            </c:forEach>
                        </div>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="button" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>