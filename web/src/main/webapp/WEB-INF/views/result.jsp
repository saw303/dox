<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="/WEB-INF/dox.tld" prefix="dox" %>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
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
    <c:url value="/" var="url">
        <c:param name="q" value="${query}"/>
    </c:url>
    <p><a href="${url}"><spring:message code="documentlist.modify.query"/></a></p>
</div>

<div id="documentList">
    <c:if test="${documents.isEmpty()}">
        <spring:message code="document.research.no.result" arguments="${query}"/>
    </c:if>
    <c:forEach var="doc" items="${documents}">
        <c:if test="${thumbnail.get(doc.hash) == true}">
            <div style="background: url('<c:url
                    value="/resources/img/file-extensions/${doc.mimeType.split('/')[1].substring(0,3)}.png"/>') left top no-repeat; border: 1px solid; border-radius: 5px; margin-bottom: 10px; overflow: auto; box-shadow:5px 5px 5px #999999; background-color: #F5DEB3;">
                <div style="margin-left: 25%;">
                    <div style="float:right;vertical-align: top;margin-right: 10px;font-size: 10px;"><a
                            href="<c:url value="/document/edit/${doc.id}"/>"><spring:message
                            code="document.research.result.edit"/></a>, <a
                            href="<c:url value="/document/delete/${doc.id}"/>"><spring:message
                            code="document.research.result.delete"/></a></div>

                    <c:if test="${thumbnail.get(doc.hash) == true}">
                        <img src="<c:url value="/document/${doc.hash}.thumbnail"/>"
                             style="display: block; float: right; margin: 25px; box-shadow: 5px 5px 5px #999999;"
                             onclick="window.open('<c:url value="/document/${doc.id}"/>')"/>
                    </c:if>
                    <h2 id="${doc.id}" onclick="window.open('<c:url value="/document/${doc.id}"/>')"
                        style="text-align: left; padding: 30px 0 0 35px; margin: 2px 0 0 0">${doc.fileName}</h2>

                    <ul style="list-style: none; text-align: left; font-size: 0.8em; padding-left: 35px;">
                        <li><spring:message
                                code="document.import.label.document.class"/>: ${doc.documentClass.getTranslation()}</li>
                        <li><spring:message code="pages"/>: ${doc.pageCount}</li>
                        <li title="${doc.hash}"><spring:message code="document.hashCode"/>: ${doc.hash.substring(0, 30)}...</li>
                        <li><spring:message code="document.userReference"/>: ${doc.userReference} </li>
                        <li><spring:message code="document.filesize"/>: ${doc.humanReadableFileSize()}</li>
                        <li>creation date: <joda:format value="${doc.creationDate}" pattern="dd.MM.yyyy HH:mm:ss"/></li>
                    </ul>
                    <dox:attributeListing documentReference="${doc}" query="${query}"/>
                </div>

            </div>
        </c:if>
        <c:if test="${thumbnail.get(doc.hash) == false}">
            <div style="background: url('<c:url
                    value="/resources/img/file-extensions/${doc.mimeType.split('/')[1].substring(0,3)}.png"/>') left top no-repeat; background-size: 32px; border: 1px solid; border-radius: 5px; margin-bottom: 10px; overflow: auto; box-shadow:5px 5px 5px #999999; background-color: #F5DEB3;">
                <div>
                    <div style="float:right;vertical-align: top;margin-right: 10px;font-size: 10px;"><a
                            href="<c:url value="/document/edit/${doc.id}"/>"><spring:message
                            code="document.research.result.edit"/></a>, <a
                            href="<c:url value="/document/delete/${doc.id}"/>"><spring:message
                            code="document.research.result.delete"/></a></div>
                    <h4 id="${doc.id}" onclick="window.open('<c:url value="/document/${doc.id}"/>')"
                        style="text-align: left; padding: 1px 0 0 35px; margin: 2px 0 0 0">${doc.fileName}</h4>
                    <h5 style="text-align: left; padding-left: 35px; margin: 0; font-style: italic"><spring:message
                            code="document.import.label.document.class"/>: ${doc.documentClass.getTranslation()}
                        / ${doc.pageCount}
                        <spring:message code="pages"/> /
                        <spring:message code="document.hashCode"/>: ${doc.hash}, <spring:message
                                code="document.userReference"/>: ${doc.userReference} / <spring:message
                                code="document.filesize"/>: ${doc.humanReadableFileSize()} / creation date: <joda:format
                                value="${doc.creationDate}" pattern="dd.MM.yyyy HH:mm:ss"/></h5>
                </div>
                <dox:attributeListing documentReference="${doc}" query="${query}"/>
            </div>
        </c:if>
    </c:forEach>
</div>