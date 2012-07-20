<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  ~ Copyright 2012 Silvio Wangler (silvio.wangler@gmail.com)
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~        http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

<div>
    <spring:message code="document.import.label.document.class"/>: <form:select id="docClass" name="docClass"
                                                                                path="documentClasses"
                                                                                multiple="false">

    <form:option value="-" htmlEscape="true" label="${defaultMessage}"/>
    <form:options items="${documentClasses}" itemLabel="shortName" itemValue="shortName" htmlEscape="true"/>

</form:select>
</div>
<div id="docClassAttributes"></div>