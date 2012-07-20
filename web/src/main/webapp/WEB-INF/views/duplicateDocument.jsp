<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<div>
    <p><spring:message code="document.import.duplicate" htmlEscape="true"/></p>

    <p><spring:message code="document.id" htmlEscape="true"/>: ${docId}</p>

    <p><spring:message code="document.hashCode" htmlEscape="true"/>: ${docHash}</p>
</div>