<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<body>
<div layout="row" ng-controller="PostBoxController as pbc">
    <div flex>
        <md-input-container>
            <label><spring:message code="postbox.label"/></label>
            <md-select ng-model="pbc.selectedPostBox">
                <md-option ng-value="p.id" ng-repeat="p in pbc.postboxes">{{ p.translation }}</md-option>
            </md-select>
        </md-input-container>

        <md-button ng-click="pbc.loadContent()"><spring:message code="postbox.button.show.content.label"/></md-button>
    </div>
</div>
</body>
</html>
