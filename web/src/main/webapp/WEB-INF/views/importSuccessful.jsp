<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="/WEB-INF/dox.tld" prefix="dox" %>
<%--
  ~ Copyright 2012 Silvio Wangler (silvio.wangler@gmail.com)
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~         http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

<div>
    <p><spring:message code="document.import.success.message"/></p>

    <p>
    <table>
        <tbody>
        <tr>
            <th><spring:message code="document.id"/>:</th>
            <td>${doc.id}</td>
        </tr>
        <tr>
            <th><spring:message code="document.pageCount"/>:</th>
            <td>${doc.pageCount}</td>
        </tr>
        <tr>
            <th><spring:message code="document.mimeType"/>:</th>
            <td><img src="<c:url value="/resources/img/file-extensions/pdf.png"/> " alt="${doc.mimeType}"
                     title="${doc.mimeType}"
                     width="32" height="32"/></td>
        </tr>
        <tr>
            <th><spring:message code="document.import.label.document.class"/>:</th>
            <td>${doc.documentClass.shortName}</td>
        </tr>
        <tr>
            <th><spring:message code="document.fileName"/>:</th>
            <td>${doc.fileName}</td>
        </tr>
        <tr>
            <th><spring:message code="document.indices"/>:</th>
            <td><dox:listAttributes documentReference="${doc}"/></td>
        </tr>
        </tbody>
    </table>
    </p>
</div>