<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
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

<h1>-&nbsp;<spring:message code="application.header"/>&nbsp;-</h1>

<h2><spring:message code="application.subheader"/></h2>
<sec:authorize access="isAuthenticated()">
    <div><spring:message code="user"/>: <sec:authentication property="principal.username"
    /></div>
    <nav>
            <%--<ul>
                <li><a href="<%=request.getContextPath()%>/ui"><spring:message code="nav.home"/></a></li>
                <li><a href="<%=request.getContextPath()%>/ui/import"><spring:message code="nav.add.new.document"/></a></li>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <li><a href="<%=request.getContextPath()%>/admin/export"><spring:message code="nav.export"/></a>
                    </li>
                </sec:authorize>
                <li><a href="<%=request.getContextPath()%>/ui/partners"><spring:message code="nav.partners"/></a></li>
                <li><a href="<%=request.getContextPath()%>/ui/settings"><spring:message code="nav.settings"/></a></li>
                <li ng-controller="LogoutController"><a ng-click="logout()"><spring:message code="nav.logout"/></a></li>
            </ul>--%>
        <md-fab-toolbar md-open="false" md-direction="right">
            <md-fab-trigger class="align-with-text">
                <md-button aria-label="menu" class="md-fab md-primary">
                    <md-icon md-svg-src="/resources/img/material/menu.svg"></md-icon>
                </md-button>
            </md-fab-trigger>

            <md-toolbar>
                <md-fab-actions class="md-toolbar-tools">

                    <spring:message code="nav.home" var="homeLabel"/>
                    <spring:message code="nav.add.new.document" var="addNewDocLabel"/>
                    <spring:message code="nav.postboxes" var="postboxes"/>
                    <spring:message code="nav.export" var="exportLabel"/>
                    <spring:message code="nav.partners" var="partnerLabel"/>
                    <spring:message code="nav.settings" var="settingsLabel"/>
                    <spring:message code="nav.logout" var="logoutLabel"/>


                    <a class="md-fab" href="<%=request.getContextPath()%>/ui" aria-label="${homeLabel}"
                       title="${homeLabel}"><i class="material-icons">home</i></a>
                    <a class="md-fab" href="<%=request.getContextPath()%>/ui/import" aria-label="${addNewDocLabel}"
                       title="${addNewDocLabel}"><i class="material-icons">note_add</i></a>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <a class="md-fab" href="<%=request.getContextPath()%>/admin/export" aria-label="${exportLabel}"
                           title="${exportLabel}"><i class="material-icons">import_export</i></a>
                    </sec:authorize>
                    <a class="md-fab" href="<%=request.getContextPath()%>/ui/postboxes" aria-label="${postboxes}"
                       title="${postboxes}"><i class="material-icons">inbox</i></a>
                    <a class="md-fab" href="<%=request.getContextPath()%>/ui/partners" aria-label="${partnerLabel}"
                       title="${partnerLabel}"><i class="material-icons">people</i></a>
                    <a class="md-fab" href="<%=request.getContextPath()%>/ui/settings" aria-label="${settingsLabel}"
                       title="${settingsLabel}"><i class="material-icons">settings</i></a>
                    <span ng-controller="LogoutController">
                        <a class="md-fab" ng-click="logout()" aria-label="${logoutLabel}" title="${logoutLabel}">
                            <i class="material-icons">exit_to_app</i>
                        </a>
                    </span>
                </md-fab-actions>
            </md-toolbar>
        </md-fab-toolbar>
    </nav>
</sec:authorize>