<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
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

<div>
    <form action="<c:url value="/login/verify"/>" method="post">
        <div>
            <input type="text" maxlength="25" required="true" name="username" id="username"
                   placeholder="<spring:message code="login.username" htmlEscape="true"/>"/>
        </div>
        <div>
            <input type="password" required="true" name="pwd" id="pwd"
                   placeholder="<spring:message code="login.password" htmlEscape="true"/>"/>
        </div>
        <div style="margin-top: 10px;">
            <input type="submit" value="<spring:message code="login.submit" htmlEscape="true"/>"/>
        </div>
    </form>
</div>