<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%--
  ~ Copyright 2012 - 2013 Silvio Wangler (silvio.wangler@gmail.com)
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~          http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

<h1>-&nbsp;<spring:message code="application.header"/>&nbsp;-</h1>

<h2><spring:message code="application.subheader"/></h2>
<sec:authorize access="isAuthenticated()">
    <div><spring:message code="user" htmlEscape="true"/>: <sec:authentication property="principal.username"
                                                                              htmlEscape="true"/></div>
    <nav>
        <ul>
            <li><a href="<c:url value="/"/>"><spring:message code="nav.home" htmlEscape="true"/></a></li>
            <li><a href="<c:url value="/showForm.html"/>"><spring:message code="nav.add.new.document"
                                                                        htmlEscape="true"/></a></li>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <li><a href="<c:url value="/admin/export"/>"><spring:message code="nav.export" htmlEscape="true"/></a>
                </li>
            </sec:authorize>
            <li><a href="<c:url value="/logout"/>"><spring:message code="nav.logout" htmlEscape="true"/></a></li>
        </ul>
    </nav>
</sec:authorize>