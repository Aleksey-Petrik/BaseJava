<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/> <!-- scope - Резюме берется из request-->
    <link rel="stylesheet" href="CSS/style.css">
    <title>Резюме ${resume.fullName} </title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2><img src="img/Male-Light.png"> ${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png" alt="Редактировать"></a>
        <a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete.png" alt="Удалить"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry" type="java.util.Map.Entry <com.urise.webapp.model.ContactsType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>

    <p>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry" type="java.util.Map.Entry <com.urise.webapp.model.SectionType, com.urise.webapp.model.AbstractSection>"/>
            <h3><%=sectionEntry.getKey().getTitle()%>:</h3>
            <%=sectionEntry.getValue().getHtmlContents()%><br/>
        </c:forEach>
    </p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
