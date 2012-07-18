<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%--
  ~ Copyright 2012 Silvio Wangler (silvio.wangler@gmail.com)
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~        http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

<h1><spring:message code="application.header"/></h1>
<nav>
    <ul>
        <li><a href="/"><spring:message code="nav.home" htmlEscape="true"/></a></li>
        <li><a href="import.html"><spring:message code="nav.add.new.document" htmlEscape="true"/></a></li>
    </ul>
</nav>