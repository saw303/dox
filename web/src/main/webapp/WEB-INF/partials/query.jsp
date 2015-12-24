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
            <spring:message code="query.start.button" var="queryButtonText"/>
            <md-input-container>
                <input id="q" name="q" ng-model="query" tabindex="1" placeholder="${placeholderText}" type="search">
            </md-input-container>
            <md-button class="md-icon-button md-primary" ng-click="doQuery()" tabindex="2" accesskey="S"
                       aria-label="${queryButtonText}">
                <i class="material-icons">find_in_page</i>
            </md-button>
            <md-button class="md-icon-button" ng-click="clearResult()" tabindex="3"><i class="material-icons">eject</i>
            </md-button>
            <md-progress-circular md-mode="indeterminate" ng-show="showSpinner"></md-progress-circular>
        </p>
        <p>
            <md-checkbox ng-model="useWildcard" tabindex="4" class="green"><spring:message
                    code="document.research.wildcard.label"/></md-checkbox>
            <md-checkbox ng-model="findOnlyMyDocuments" tabindex="5" class="green"><spring:message
                    code="document.research.only.my.docs.label"/></md-checkbox>

        </p>
    </form>

    <md-content class="md-padding" layout="row" layout-wrap="" layout-align="center start" layout-xs="column">

            <span ng-if="isEmptyResult()"><spring:message
                    code="document.research.no.result.start"/>{{query}}<spring:message
                    code="document.research.no.result.end"/></span>

        <spring:message code="document.research.result.edit" var="editLabel"/>
        <spring:message code="document.research.result.delete" var="deleteLabel"/>
        <spring:message code="document.research.result.show" var="showLabel"/>

        <div flex="30" flex-sm="100" flex-xs="100" layout="column" ng-repeat="document in documents">

            <md-card>
                <img ng-src="/resources/img/file-extensions/{{retrieveExtension(document)}}" width="25%" height="25%">
                <md-card-title>
                    <md-card-title-text>
                        <span class="md-subhead">{{document.fileName}}</span>
                    </md-card-title-text>
                </md-card-title>
                <md-card-actions layout="row" layout-align="end center">
                    <md-button class="md-icon-button" ng-click="showDocument(document)" aria-label="${showLabel}">
                        <i class="material-icons">open_in_new</i>
                    </md-button>
                    <md-button class="md-icon-button" ng-href="/ui/edit/{{document.id}}" aria-label="${editLabel}">
                        <i class="material-icons">mode_edit</i>
                    </md-button>
                    <md-button class="md-icon-button" ng-click="deleteDocument(document, $index)"
                               aria-label="${deleteLabel}">
                        <i class="material-icons">delete</i>
                    </md-button>
                </md-card-actions>
            </md-card>
        </div>
    </md-content>


    <%--<div id="documentList">




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
    </div>--%>
</div>
</body>
</html>
