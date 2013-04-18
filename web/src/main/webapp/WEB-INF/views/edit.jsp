<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%--
  ~ Copyright 2012 - 2013 Silvio Wangler (silvio.wangler@gmail.com)
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~    http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>
<form method="POST">

    <c:forEach var="index" items="${doc.indices.keySet()}">

       <p>
           <label for="${index.key}">${index.translation}:</label>

           ${attributes.get(index.key).dataType}

           <c:if test="${attributes.get(index.key).dataType.name().equals('DATE')}">
            <input name="${index.key}" type="date" ${attributes.get(index.key).isOptional()? "" : "required"} value="${doc.indices.get(index)}"/>
           </c:if>
           <c:if test="${attributes.get(index.key).dataType.name().equals('STRING')}">
               <input name="${index.key}" type="text" ${attributes.get(index.key).isOptional()? "" : "required"} value="${doc.indices.get(index)}"/>
           </c:if>
       </p>
    </c:forEach>
    <button type="submit" id="importDocBtn"><spring:message code="button.modify.document"/></button>
</form>