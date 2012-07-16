<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!doctype html lang="de">
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="no-js lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js" lang="en"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

    <title><tiles:insertAttribute name="title" ignore="true"/></title>
    <meta name="description" content="">
    <meta name="author" content="Silvio Wangler">

    <meta name="viewport" content="width=device-width">

    <link rel="stylesheet" href="css/style.css">
    <link type="text/plain" rel="author" href="humans.txt"/>
    <script src="js/libs/modernizr-2.5.3.min.js"></script>
</head>
<body>

<div id="container">
    <header>
        <h1><spring:message code="application.header"/></h1>
    </header>
    <div role="main">
        <tiles:insertAttribute name="body"/>
    </div>
    <footer>
        <tiles:insertAttribute name="footer"/>
    </footer>
</div>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="js/libs/jquery-1.7.2.min.js"><\/script>')</script>

</body>
</html>
