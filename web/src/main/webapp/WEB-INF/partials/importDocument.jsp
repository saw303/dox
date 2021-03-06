<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
</head>
<body>
<div ng-controller="ImportController">

    <spring:message code="document.import.label.document.class"/>: <select ng-model="documentClass" required="required"
                                                                           ng-value="shortName"
                                                                           ng-options="d.translation for d in documentClasses | orderBy: 'translation' ">
    <option value=""><spring:message code="document.import.choose.document.class"/></option>
</select>

    <div ng-show="message.visible" class="messageBox">{{message.value}}</div>

    <div ng-show="documentClass" id="docClassAttributes">

        <form name="form" novalidate="novalidate" ng-submit="doUpload()">

            <c:if test="${clients.size() > 1}">
                <p><spring:message code="client.label"/>:
                    <select name="x_client">
                        <c:forEach items="${clients}" var="client">
                            <option value="${client}">${client}</option>
                        </c:forEach>
                    </select>
                </p>
            </c:if>
            <c:if test="${clients.size() == 1}">
                <input name="x_client" type="hidden" value="${clients.get(0)}">
            </c:if>

            <input name="documentClassShortName" type="hidden" value="{{documentClass.shortName}}"/>

            <p ng-repeat="attribute in documentClass.attributes">

                <label for="{{attribute.shortName}}">{{attribute.translation}}: </label>

            <span ng-switch="attribute.dataType">
                <span ng-switch-when="STRING">
                    <datalist id="list-{{attribute.shortName}}">
                        <option value="{{value}}" ng-repeat="value in attribute.domain.values"/>
                    </datalist>
                    <input name="{{attribute.shortName}}" type="text"
                           ng-required="!attribute.optional" list="list-{{attribute.shortName}}"/>
                </span>
                <input ng-switch-when="DATE" name="{{attribute.shortName}}" type="date"
                       ng-required="!attribute.optional"/>
                <input ng-switch-when="DATETIME" name="{{attribute.shortName}}" type="datetime-local"
                       ng-required="!attribute.optional"/>
                <input ng-switch-when="CURRENCY" name="{{attribute.shortName}}" type="text"
                       value="CHF " ng-pattern="/\D{3} [-+]?[0-9]*[.,]?[0-9]+/" ng-required="!attribute.optional"/>
                <input ng-switch-when="DOUBLE" name="{{attribute.shortName}}" type="number"
                       min="0" step="any"
                       ng-required="!attribute.optional"/>
                <input ng-switch-when="LONG" name="{{attribute.shortName}}" type="number" min="0"
                       step="any"
                       ng-required="!attribute.optional"/>
                <input ng-switch-when="SHORT" name="{{attribute.shortName}}" type="number" min="0"
                       step="any"
                       ng-required="!attribute.optional"/>
                <input ng-switch-when="INTEGER" name="{{attribute.shortName}}" type="number"
                       min="0" step="any"
                       ng-required="!attribute.optional"/>
            </span>
            </p>

            <input type="file" name="file" required="required" accept="application/pdf"/>
            <button type="submit" id="importDocBtn"><spring:message code="button.import.document"/></button>
        </form>
    </div>

</div>

</body>
</html>