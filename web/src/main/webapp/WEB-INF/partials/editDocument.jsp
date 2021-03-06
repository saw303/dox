<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<div>

    <div ng-show="displayMessage()">
        <spring:message code="document.update.success.message.start"/> {{document.id}} <spring:message code="document.update.success.message.end"/>
    </div>

    <div><spring:message code="document.id"/>: {{document.hash}}</div>

    <form novalidate="novalidate" ng-submit="doSubmit()">

        <input type="hidden" value="{{document.id}}"/>

        <p ng-repeat="(label, index) in document.indices">

            <label for="{{index.attribute.shortName}}">{{index.attribute.translation}}: </label>

            <span ng-switch="index.attribute.dataType">
                <span ng-switch-when="STRING">
                    <datalist id="list-{{index.attribute.shortName}}">
                        <option value="{{value}}" ng-repeat="value in index.attribute.domain.values"/>
                    </datalist>
                    <input style="width:50%" name="{{index.attribute.shortName}}" type="text"
                           ng-required="!index.attribute.optional" list="list-{{index.attribute.shortName}}"
                           ng-model="index.value" size="{{value.length()}}"/>
                </span>
                <input ng-switch-when="DATE" name="{{index.attribute.shortName}}" type="dateTimeLocal"
                       ng-required="!index.attribute.optional" ng-model="index.value"/>
                <input ng-switch-when="DATETIME" name="{{index.attribute.shortName}}" type="dateTimeLocal"
                       ng-required="!index.attribute.optional" ng-model="index.value"/>
                <input ng-switch-when="CURRENCY" name="{{index.attribute.shortName}}.currency" type="text"
                       ng-model="index.value.currency"
                       ng-pattern="/\D{3}/" maxlength="3"
                       ng-required="!index.attribute.optional"/>
                <input ng-switch-when="CURRENCY" name="{{index.attribute.shortName}}.amount" type="text"
                       ng-model="index.value.amount"
                       ng-pattern="/[-+]?[0-9]*[.,]?[0-9]+/"
                       ng-required="!index.attribute.optional"/>
                <input ng-switch-when="DOUBLE" name="{{index.attribute.shortName}}" type="number"
                       min="0" step="any"
                       ng-required="!index.attribute.optional" ng-model="index.value"/>
                <input ng-switch-when="LONG" name="{{index.attribute.shortName}}" type="number" min="0"
                       step="any"
                       ng-required="!index.attribute.optional" ng-model="index.value"/>
                <input ng-switch-when="SHORT" name="{{index.attribute.shortName}}" type="number" min="0"
                       step="any"
                       ng-required="!index.attribute.optional" ng-model="index.value"/>
                <input ng-switch-when="INTEGER" name="{{index.attribute.shortName}}" type="number"
                       min="0" step="any"
                       ng-required="!index.attribute.optional" ng-model="index.value"/>
            </span>
        </p>
        <p>
            <button type="submit" id="importDocBtn"><spring:message code="button.modify.document"/></button>
        </p>
    </form>
</div>
</body>
</html>