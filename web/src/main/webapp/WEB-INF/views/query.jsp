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

<div id="query">
    <form action="<c:url value="/query.html"/>" method="post">
        <p>
            <input id="q" name="q" value="" tabindex="1"/>
            <button type="submit" tabindex="2" accesskey="S"><spring:message code="query.start.button"/></button>
        </p>
        <p><input type="checkbox" name="wildcard" id="wildcard" checked="checked" value="1"/><spring:message
                code="document.research.wildcard.label"/> | <a href="#"><spring:message
                code="document.research.advanced.search"/></a></p>
    </form>
</div>