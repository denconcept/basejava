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
        <h3>Контакты</h3>
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
                <dl id="${type.name()}_list">
                    <c:forEach var="description" items="${resume.getSection(type).getDescription()}">
                        <dd><input type="text" name="${type.name()}" size="200" value="${description}"></dd>
                    </c:forEach>
                </dl>
                <button type="button" onclick="addListItem('${type.name()}')">Добавить</button>
            </c:if>
            <c:if test="${type.name() == 'EXPERIENCE' || type.name() == 'EDUCATION'}">
                <dl id="${type.name()}_companies">
                    <c:forEach var="company" items="${resume.getSection(type).getCompanies()}">
                        <dd><br>
                            <input type="text" name="${type.name()}_companyTitle" size="100"
                                   value="${company.homePage.title}" placeholder="Название"><br>
                            <input type="text" name="${type.name()}_companyUrl" size="100"
                                   value="${company.homePage.url}" placeholder="Ссылка">
                            <input type="hidden" name="${type.name()}_periodCount"
                                   value="${fn:length(company.periods)}">
                            <dl>
                                <c:forEach var="period" items="${company.periods}">
                                    <dd>
                                        <input type="text" name="${type.name()}_periodStart" size="20"
                                               value="${period.formatedStartDate}" placeholder="Начало, ММ/ГГГГ"><br>
                                        <input type="text" name="${type.name()}_periodEnd" size="20"
                                               value="${period.formatedEndDate}" placeholder="Окончание, ММ/ГГГГ"><br>
                                        <input type="text" name="${type.name()}_periodTitle" size="100"
                                               value="${period.title}" placeholder="Заголовок"><br>
                                        <input type="text" name="${type.name()}_periodDescription" size="200"
                                               value="${period.description}" placeholder="Описание"><br>
                                    </dd>
                                </c:forEach>
                            </dl>
                            <button type="button" onclick="addPeriod(this, '${type.name()}')">Добавить период</button>
                        </dd>
                    </c:forEach>
                </dl>
                <button type="button" onclick="addCompany('${type.name()}')">Добавить компанию</button>
            </c:if>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="button" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/resume.js"></script>
</body>
</html>