<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<head>
    <title></title>
</head>
<body>
<div id="query" ng-controller="QueryController">
    <form>
        <p>
            <spring:message code="query.placeholder" var="placeholderText"/>
            <img src="/resources/images/spinner.gif" ng-show="showSpinner"/>
            <input id="q" name="q" ng-model="query" tabindex="1" placeholder="${placeholderText}" type="search"/>
            <button ng-click="doQuery()" tabindex="2" accesskey="S"><spring:message code="query.start.button"/></button>
        </p>
        <p>
            <input type="checkbox" ng-model="useWildcard" tabindex="3"/> <spring:message code="document.research.wildcard.label"/> | <input type="checkbox"
                                                                                                       ng-model="findOnlyMyDocuments"
                                                                                                       tabindex="4"/>
            <spring:message code="document.research.only.my.docs.label"/>
        </p>
    </form>


    <div id="documentList">

        <span ng-if="isEmptyResult()"><spring:message code="document.research.no.result.start"/>{{query}}<spring:message code="document.research.no.result.end"/></span>

        <div ng-repeat="document in documents"
             style="background: url('/resources/img/file-extensions/{{retrieveExtension(document)}}') left top no-repeat;background-size: 6%;border: 1px solid;border-radius: 5px;margin-bottom: 10px;overflow: auto;box-shadow: 5px 5px 5px #999999;background-color: #F5DEB3;">
            <div style="margin-left: 10%;">
                <div style="float:right;vertical-align: top;margin-right: 10px;font-size: 10px;">
                    <a ng-href="/ui/edit/{{document.id}}"><spring:message code="document.research.result.edit"/></a>,
                    <a ng-click="deleteDocument(document, $index)"><spring:message code="document.research.result.delete"/></a>
                </div>

                <img ng-src="/document/{{document.hash}}.thumbnail"
                     class="thumbnail" ng-click="showDocument(document)"
                     title="{{document.fileName}} (ID: {{document.id}})"/>

                <h2 id="{{document.id}}" ng-click="showDocument(document)"
                    style="text-align: left; padding: 10px 0 0 35px; margin: 2px 0 0 0; overflow: hidden;">
                    {{document.fileName}}</h2>

                <ul id="docTechAttrs">
                    <li><strong><spring:message code="document.import.label.document.class"/>:</strong> {{document.documentClass.translation}}</li>
                    <li><strong><spring:message code="document.pageCount"/>:</strong> {{document.pageCount|number}}</li>
                    <li title="{{document.hash}}" alt="{{document.hash}}"><strong><spring:message code="document.hashCode"/>:</strong> {{document.hash}}</li>
                    <li><strong><spring:message code="document.userReference"/>:</strong> {{document.userReference}}</li>
                    <li><strong><spring:message code="document.filesize"/>:</strong> {{document.fileSize | number}} Bytes</li>
                    <li><strong><spring:message code="document.creationDate"/>:</strong> {{document.creationDate | date:'short'}}</li>
                    <li><strong><spring:message code="document.mimeType"/>:</strong> {{document.mimeType}}</li>
                    <li ng-if="document.tags.length > 0"><strong><spring:message code="document.tags"/>:</strong>{{document.tags}}
                    </li>
                </ul>
                <div class="attributeListing"><strong><spring:message code="document.indices"/>:</strong>
                    <span ng-repeat="(label, index) in document.indices">
                        {{index.attribute.translation}}:
                        <span ng-switch="index.attribute.dataType">
                            <span ng-switch-when="DATE">{{index.value | date: 'shortDate'}}</span>
                            <span ng-switch-default>{{index.value}}</span>
                        </span>
                    </span>
                </div>
            </div>

        </div>
    </div>
</div>
</body>
</html>
