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
    <table class="table">
        <caption><h2>База резюме:</h2></caption>
        <tr>
            <td><b>Full name</b></td>
            <td><b>Email</b></td>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="com.urise.webapp.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td>
                    <%=ContactsType.EMAIL.toHtml(resume.getContact(ContactsType.EMAIL))%>
                </td>
                <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete.png"></a></td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <a href="resume?uuid=${resume.uuid}&action=add"><img src="img/add-user.png"></a>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
