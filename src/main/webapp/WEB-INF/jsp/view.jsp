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
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>