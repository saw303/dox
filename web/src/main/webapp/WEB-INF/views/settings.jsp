<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page import="ch.silviowangler.dox.api.settings.SettingsConstants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>


<h1><spring:message code="settings.title"/></h1>
<section>
    <ul id="settingsList">
        <li><spring:message code="settings.wildcard.always.on"/>
            <form method="post" action="/updateSetting.html"><input type="hidden" name="setting"
                                                                    value="<%=SettingsConstants.SETTING_WILDCARD_QUERY%>"/>
                <input type="checkbox" name="v" value="1"
                       <c:if test="${wq == '1'}">checked="checked"</c:if> />
                <input type="submit" name="s" value="<spring:message code="settings.button.label"/>"/>
            </form>
        </li>
        <li><spring:message code="settings.only.my.documents.always.on"/>
            <form method="post" action="/updateSetting.html"><input type="hidden" name="setting"
                                                                    value="<%=SettingsConstants.SETTING_FIND_ONLY_MY_DOCUMENTS%>"/>
                <input type="checkbox" name="v" value="1" <c:if test="${fomd == '1'}">checked="checked"</c:if>/>
                <input type="submit" name="s" value="<spring:message code="settings.button.label"/>"/></form>
        </li>
    </ul>
</section>
</body>
</html>