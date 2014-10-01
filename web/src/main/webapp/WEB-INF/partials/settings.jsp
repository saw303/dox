<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<head>
    <title></title>
</head>
<body>


<h1><spring:message code="settings.title"/></h1>
<section ng-controller="SettingsController">
    <ul id="settingsList">
        <li ng-repeat="setting in settings">
            {{setting.description}} <input type="checkbox" name="{{setting.key}}" ng-model="setting.value"
                                           ng-true-value="1" ng-false-value="0"/>
        </li>
    </ul>
    <button ng-click="save()"><spring:message code="settings.button.label"/></button>
</section>
</body>
</html>