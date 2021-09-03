<%@ page import="com.urise.webapp.model.ContactsType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="CSS/style.css">
    <title>Список резюме:</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table border=1>
        <caption>Резюме:</caption>
        <tr>
            <td>UUID</td>
            <td>Full Name</td>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="com.urise.webapp.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td>
                    <%=ContactsType.EMAIL.toHtml(resume.getContact(ContactsType.EMAIL))%>
                </td>
                <td><a href="resume?uuid=${resume.uuid}&action=delete">Delete</a></td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit">Edit</a></td>
            </tr>
        </c:forEach>
    </table>
    <form>
        <p><input type="submit"></p>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
