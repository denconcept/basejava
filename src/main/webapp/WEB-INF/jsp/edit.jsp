<%@ page import="com.basejava.model.ContactType" %>
<%@ page import="com.basejava.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/theme/${theme}.css">
    <link rel="stylesheet" href="css/styles.css">
    <link rel="stylesheet" href="css/edit-resume-styles.css">
    <jsp:useBean id="resume" type="com.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<form method="post" action="resume" enctype="application/x-www-form-urlencoded">

    <input type="hidden" name="uuid" value="${resume.uuid}">
    <input type="hidden" name="theme" value="${theme}">

    <div class="scrollable-panel">
        <div class="form-wrapper">

            <div class="section">ФИО</div>
            <input class="field" type="text" name="fullName" size="55" placeholder="ФИО" value="${resume.fullName}" required>

            <div class="section">Контакты</div>
            <c:forEach var="type" items="<%=ContactType.values()%>">
                <input class="field" type="text" name="${type.name()}" size="30" placeholder="${type.title}"
                       value="${resume.getContact(type)}">
            </c:forEach>

            <div class="spacer"></div>

            <div class="section">Секции</div>
            <c:forEach var="type" items="<%=SectionType.values()%>">

                <c:set var="section" value="${resume.getSection(type)}"/>

                <div class="field-label">${type.title}</div>

                <c:choose>
                    <c:when test="${type == 'OBJECTIVE' || type == 'PERSONAL'}">
                        <textarea class="field" name='${type.name()}'>${section.description}</textarea>
                    </c:when>
                    <c:when test="${type == 'QUALIFICATIONS' || type == 'ACHIEVEMENT'}">
                        <c:forEach var="description" items="${section.description}">
                            <textarea class="field" name='${type.name()}'>${description}</textarea>
                        </c:forEach>
                    </c:when>
                    <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                        <c:forEach var="company" items="${section.companies}" varStatus="counter">
                            <c:choose>
                                <c:when test="${counter.index == 0}">
                                </c:when>
                                <c:otherwise>
                                    <div class="spacer"></div>
                                </c:otherwise>
                            </c:choose>

                            <input class="field" type="text" placeholder="Название" name='${type.name()}' size="100"
                                   value="${company.homePage.title}">
                            <input class="field" type="text" placeholder="Ссылка" name='${type.name()}url' size="100"
                                   value="${company.homePage.url}">

                            <c:forEach var="period" items="${company.periods}">
                                <div class="date-section">
                                    <input class="field date" name="${type.name()}${counter.index}startDate"
                                           placeholder="Начало, ММ/ГГГГ"
                                           size="10"
                                           value="${period.formatedStartDate}">
                                    <input class="field date date-margin" name="${type.name()}${counter.index}endDate"
                                           placeholder="Окончание, ММ/ГГГГ"
                                           size="10"
                                           value="${period.formatedEndDate}">
                                </div>
                                <input class="field" type="text" placeholder="Заголовок"
                                       name='${type.name()}${counter.index}title' size="75"
                                       value="${period.title}">
                                <textarea class="field" placeholder="Описание"
                                          name="${type.name()}${counter.index}description">${period.description}</textarea>
                            </c:forEach>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </c:forEach>

            <div class="spacer"></div>

            <div class="button-section">
                <button class="red-cancel-button" type="button" onclick="window.history.back()">Отменить</button>
                <button class="green-submit-button" type="submit">Сохранить</button>
            </div>
        </div>
    </div>
</form>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>