<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<div>
    <table>
        <thead>
        <tr>
            <th scope="col"><spring:message code="document.hashCode" htmlEscape="true"/></th>
            <th scope="col"><spring:message code="document.mimeType" htmlEscape="true"/></th>
            <th scope="col"><spring:message code="document.pageCount" htmlEscape="true"/></th>
            <th scope="col"><spring:message code="document.fileName" htmlEscape="true"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="doc" items="${documents}">
            <tr>
                <td>${doc.hash}</td>
                <td>${doc.mimeType}</td>
                <td>${doc.pageCount}</td>
                <td>${doc.fileName}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>