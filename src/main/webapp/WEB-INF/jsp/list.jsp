<%@ page import="com.basejava.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Имя</th>
            <th>Email</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="com.basejava.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td><%=ContactType.MAIL.toHtml(resume.getContact(ContactType.MAIL))%></td>
                <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/remove.svg"></a></td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/edit.svg"></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>