<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<h1><spring:message code="application.header"/></h1>
<nav>
    <ul>
        <li><a href="/"><spring:message code="nav.home" htmlEscape="true"/></a></li>
        <li><a href="import.html"><spring:message code="nav.add.new.document" htmlEscape="true"/></a></li>
    </ul>
</nav>