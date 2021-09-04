<%@ page import="com.urise.webapp.model.ContactsType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <!-- scope - Резюме берется из request-->
    <link rel="stylesheet" href="CSS/style.css">
    <title>Резюме ${resume.fullName} </title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt><b>Имя:</b></dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
            <c:forEach var="type" items="<%=ContactsType.values()%>">
                <dl>
                    <dt>${type.title}</dt>
                    <dd><input type="text" name="${type.name()}" size=50 value="${resume.getContact(type)}"></dd>
                </dl>
            </c:forEach>
        <hr>
        <dl>
            <dt><b>${SectionType.PERSONAL.title}</b></dt>
            <dd><input type="text" name="${SectionType.PERSONAL.name()}" size=80 value="${resume.getSection(SectionType.PERSONAL).contents}"></dd>
        </dl>

        <dl>
            <dt><b>${SectionType.OBJECTIVE.title}</b></dt>
            <dd><input type="text" name="${SectionType.OBJECTIVE.name()}" size=80 value="${resume.getSection(SectionType.OBJECTIVE).contents}"></dd>
        </dl>

        <h3>${SectionType.ACHIEVEMENT.title}:</h3>
        <p><textarea name="${SectionType.ACHIEVEMENT.name()}" rows=10 cols=100 name="text">${resume.getSection(SectionType.ACHIEVEMENT).contents}</textarea></p>

        <h3>${SectionType.QUALIFICATIONS.title}:</h3>
        <p><textarea name="${SectionType.QUALIFICATIONS.name()}" rows=10 cols=100 name="text">${resume.getSection(SectionType.QUALIFICATIONS).contents}</textarea></p>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
    <br>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
