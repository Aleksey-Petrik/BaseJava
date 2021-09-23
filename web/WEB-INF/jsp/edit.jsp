<%@ page import="com.urise.webapp.model.*" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
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

        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>

            <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>
            <h3><a>${type.title}</a></h3>
            <c:choose>
                <c:when test="${type == 'OBJECTIVE'}">
                    <input type="text" name="${type}" size=75 value="${section.contents}">
                </c:when>
                <c:when test="${type == 'PERSONAL'}">
                    <textarea name="${type}" cols=75 rows=5>${section.contents}</textarea>
                </c:when>
                <c:when test="${type == 'QUALIFICATIONS' || type == 'ACHIEVEMENT'}">
                    <textarea name="${type}" cols=75 rows=5>${section.contents}</textarea>
                </c:when>
                <c:when test="${type == 'EXPERIENCE' || type == 'EDUCATION'}">
                    <c:forEach var="org" items="<%=((ListOrganizationSection)section).getList()%>" varStatus="counter">
                        <dl>
                            <dt><h4>Наименование:</h4></dt>
                            <dd><input type="text" name="${type}" size=100 value="${org.organization.name}"></dd>
                        </dl>
                        <dl>
                            <dt><h4>Сайт:</h4></dt>
                            <dd><input type="text" name="${type}" size=100 value="${org.organization.url}"></dd>
                        </dl>
                        <br>
                        <div style="margin-left: 30px">
                            <c:forEach var="pos" items="${org.periods}">
                                <jsp:useBean id="pos" type="com.urise.webapp.model.Organization.Period"/>
                                <dl>
                                    <dt><b>Начальная дата:</b></dt>
                                    <dd>
                                        <input type="month" name="${type}${counter.index}startDate" size=10
                                               value="<%=DateUtil.format(pos.getDateBegin(), "yyyy-MM")%>">
                                    </dd>
                                </dl>
                                <dl>
                                    <dt><b>Конечная дата:</b></dt>
                                    <dd>
                                        <input type="month" name="${type}${counter.index}endDate" size=10
                                               value="<%=DateUtil.format(pos.getDateEnd(), "yyyy-MM")%>">
                                    </dd>
                                </dl>
                                <dl>
                                    <dt><b>Должность:</b></dt>
                                    <dd><textarea name="${type}${counter.index}title" cols=75 rows=5>${pos.content}</textarea></dd>
                                </dl>
                            </c:forEach>
                        </div>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
    <br>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
