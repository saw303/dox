<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="dox" uri="http://silviowangler.ch/dox" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
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

<div id="query" ng-controller="QueryController">
    <form>
        <p>
            <input id="q" name="q" ng-model="query" tabindex="1" placeholder="Nach was suchst Du?"/>
            <button ng-click="doQuery()" tabindex="2" accesskey="S"><spring:message code="query.start.button"/></button>
        </p>
        <p><input type="checkbox" ng-model="useWildcard" tabindex="3"/><spring:message
                code="document.research.wildcard.label"/> | <input type="checkbox" ng-model="findOnlyMyDocuments"
                                                                   tabindex="4"/><spring:message
                code="document.research.only.my.docs.label"/> |<a href="/showForm.html?advancedQuery=1"
                                                                  tabindex="5"><spring:message
                code="document.research.advanced.search"/></a></p>
    </form>


    <div id="documentList">

        <span ng-if="isEmptyResult()"><spring:message code="document.research.no.result" arguments="${query}"/></span>


        <div ng-repeat="document in documents" style="background: url('<c:url
                value="/resources/img/file-extensions/pdf.png"/>') left top no-repeat; border: 1px solid; border-radius: 5px; margin-bottom: 10px; overflow: auto; box-shadow:5px 5px 5px #999999; background-color: #F5DEB3;">
            <div style="margin-left: 25%;">
                <div style="float:right;vertical-align: top;margin-right: 10px;font-size: 10px;"><a
                        href="#"><spring:message
                        code="document.research.result.edit"/></a>, <a
                        ng-click="deleteDocument(document, $index)"><spring:message
                        code="document.research.result.delete"/></a></div>

                <img ng-src="<c:url value="/document/{{document.hash}}.thumbnail"/>"
                     style="display: block; float: right; margin: 25px; box-shadow: 5px 5px 5px #999999;"/>

                <h2 id="{{document.id}}"
                    style="text-align: left; padding: 30px 0 0 35px; margin: 2px 0 0 0; overflow: hidden;">
                    {{document.fileName}}</h2>

                <ul style="list-style: none; text-align: left; font-size: 0.8em; padding-left: 35px;">
                    <li><spring:message
                            code="document.import.label.document.class"/>: {{document.documentClass.translation}}
                    </li>
                    <li><spring:message code="pages"/>: {{document.pageCount}}</li>
                    <li title="{{document.hash}}"><spring:message
                            code="document.hashCode"/>: {{document.hash}}...
                    </li>
                    <li><spring:message code="document.userReference"/>: {{document.userReference}}</li>
                    <li><spring:message code="document.filesize"/>: {{document.fileSize}}</li>
                    <li><spring:message code="document.creationDate"/>: {{document.creationDate | date:'dd.MM.yyyy
                        HH:mm:ss'}}
                    </li>
                </ul>
                <div class="attributeListing">
                    <span ng-repeat="(label, value) in document.indices">{{label}}: {{value}},&nbsp;</span>
                </div>
            </div>

        </div>
    </div>
</div>