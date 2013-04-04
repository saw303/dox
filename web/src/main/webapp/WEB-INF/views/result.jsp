<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="/WEB-INF/dox.tld" prefix="dox" %>
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

<div>
    <table id="resultTable">
        <thead>
        <tr>
            <th scope="col"><spring:message code="document.id" htmlEscape="true"/></th>
            <th scope="col"><spring:message code="document.mimeType" htmlEscape="true"/></th>
            <th scope="col"><spring:message code="document.pageCount" htmlEscape="true"/></th>
            <th scope="col"><spring:message code="document.fileName" htmlEscape="true"/></th>
            <th scope="col"><spring:message code="document.indices" htmlEscape="true"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="doc" items="${documents}">
            <tr onclick="window.open('<c:url value="/document/${doc.id}"/>')">
                <td title="${doc.hash}">${doc.id}</td>
                <td><img
                        src="<c:url value="/resources/img/file-extensions/${doc.mimeType.split('/')[1].substring(0,3)}.png"/>"
                        alt="${doc.mimeType}"
                        title="${doc.mimeType}"
                        width="32" height="32"/></td>
                <td>${doc.pageCount}</td>
                <td>${doc.fileName}</td>
                <td><dox:listAttributes documentReference="${doc}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>