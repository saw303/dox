<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<div id="query">
    <form action="query.html" method="post">
        <input id="q" name="q" value="" tabindex="1"/>
        <button type="submit" tabindex="2" accesskey="S"><spring:message code="query.start.button"/></button>
    </form>
</div>