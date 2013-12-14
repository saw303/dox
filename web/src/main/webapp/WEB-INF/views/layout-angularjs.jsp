<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

<!doctype html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="de"> <![endif]-->
<!--[if IE 7]> <html class="no-js lt-ie9 lt-ie8" lang="de"> <![endif]-->
<!--[if IE 8]> <html class="no-js lt-ie9" lang="de"> <![endif]-->
<!--[if gt IE 8]><!-->
<html ng-app="dox"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

    <title><spring:message code="application.html.title"/></title>
    <meta name="description" content="">
    <meta name="author" content="Silvio Wangler">

    <meta name="viewport" content="width=device-width">
    <meta name="fragment" content="!" />

    <link href='http://fonts.googleapis.com/css?family=Playball|Montserrat:700,400' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>"/>

    <link type="text/plain" rel="author" href="humans.txt"/>
    <script src="<c:url value="/js/libs/angular-1.2.4/angular.js"/>"></script>
    <script src="<c:url value="/js/libs/angular-1.2.4/angular-resource.js"/>"></script>
    <script src="<c:url value="/js/libs/angular-1.2.4/angular-route.js"/>"></script>
    <script src="<c:url value="/js/dox/dox.js"/>"></script>
    <script src="<c:url value="/js/dox/controllers.js"/>"></script>
    <script src="<c:url value="/js/dox/services.js"/>"></script>
    <script src="<c:url value="/js/dox/directives.js"/>"></script>
</head>
<body>

<div id="container">
    <header>
        <tiles:insertAttribute name="header"/>
    </header>
    <div id="main" role="main">
        <ng-view></ng-view>
    </div>
    <footer>
        <tiles:insertAttribute name="footer"/>
    </footer>
</div>
</body>
</html>
