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
    <c:forEach var="doc" items="${documents}">
        <div style="background: url('<c:url
                value="/resources/img/file-extensions/${doc.mimeType.split('/')[1].substring(0,3)}.png"/>') left top no-repeat; background-size: 32px; border: 1px solid; border-radius: 5px; margin-bottom: 5px;">
            <div>
                <h4 onclick="window.open('<c:url value="/document/${doc.id}"/>')"
                    style="text-align: left; padding: 1px 0 0 35px; margin: 2px 0 0 0">${doc.fileName}</h4>
                <h5 style="text-align: left; padding-left: 35px; margin: 0; font-style: italic">${doc.pageCount}
                    <spring:message code="pages"/> /
                    <spring:message code="filename"/>: ${doc.fileName}</h5>
            </div>
            <div style="text-align: left;font-size: 12px; padding-left: 35px;">
                <c:forEach var="key" items="${doc.getIndices().keySet()}">
                    ${key.getTranslation()}: <span
                        style="background-color: #ffff00; font-weight: bold;">${doc.getIndices().get(key)}</span>
                </c:forEach>
            </div>
        </div>
    </c:forEach>
</div>