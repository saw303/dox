<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<div id="maintenanceWrapper">
    <h1><spring:message code="maintenance.header"/></h1>
    <c:if test="${store.size() > 0}">
    <div id="documentStoreWrapper">
        <h5><spring:message code="maintenance.document.store.header"/></h5>
        <ul>
            <c:forEach items="${store}" var="item">
                <li>${item.hash}</li>
            </c:forEach>
        </ul>
    </div>
    <h5><spring:message code="maintenance.db.header"/></h5>
    <ul>
        <c:forEach items="${database}" var="item">
            <li>${item.hash}</li>
        </c:forEach>
    </ul>
</div>
</c:if>
</div>
</body>
</html>