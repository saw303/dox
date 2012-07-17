<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div><spring:message code="document.import.label.document.class"/>: <form:select id="docClass" name="docClass"
                                                                                 path="docClass"
                                                                                 items="${documentClasses}"
                                                                                 itemLabel="shortName"
                                                                                 itemValue="shortName"
                                                                                 htmlEscape="true"/></div>

<div id="docClassAttributes"></div>