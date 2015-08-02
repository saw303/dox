<%--
  Created by IntelliJ IDEA.
  User: saw
  Date: 21.02.15
  Time: 16:50
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>

<div ng-controller="PartnerController">

    <h4>Partner List</h4>
    L&auml;nge: {{partners.length}}

    <ul>
        <li ng-repeat="partner in partners">A Partner is life is nice: {{partner.name}}</li>
    </ul>
</body>
</html>
