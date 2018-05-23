<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div>
<p><spring:message code="admin.maxattendants"/>: <jstl:out value="${maxAttendants}"/></p>
<p><spring:message code="admin.minattendants"/>: <jstl:out value="${minAttendants}"/></p>
<p><spring:message code="admin.avgattendants"/>: <jstl:out value="${avgAttendants}"/></p>
<p><spring:message code="admin.stdattendants"/>: <jstl:out value="${stdAttendants}"/></p>
<p><spring:message code="admin.cancelledannouncements"/>: <jstl:out value="${cancelledAnnouncements}"/></p>
</div>

<spring:message code="actor.username" var="usernameHeader" />
<spring:message code="actor.name" var="nameHeader" />
<spring:message code="actor.surname" var="surnameHeader" />
<spring:message code="admin.topusers"/>
<display:table name="topUsers" id="row" requestURI="admin/dashboard.do" class="displaytag">
<display:column property="userAccount.username" title="${usernameHeader}" />
<display:column property="name" title="${nameHeader}" />
<display:column property="surname" title="${surnameHeader}" />
</display:table>

<spring:message code="admin.topdrivers"/>
<display:table name="topDrivers" id="row" requestURI="admin/dashboard.do" class="displaytag">
<display:column property="userAccount.username" title="${usernameHeader}" />
<display:column property="name" title="${nameHeader}" />
<display:column property="surname" title="${surnameHeader}" />
</display:table>

<spring:message code="admin.topmechanics"/>
<display:table name="topMechanics" id="row" requestURI="admin/dashboard.do" class="displaytag">
<display:column property="userAccount.username" title="${usernameHeader}" />
<display:column property="name" title="${nameHeader}" />
<display:column property="surname" title="${surnameHeader}" />
</display:table>

<spring:message code="admin.worstusers"/>
<display:table name="worstUsers" id="row" requestURI="admin/dashboard.do" class="displaytag">
<display:column property="userAccount.username" title="${usernameHeader}" />
<display:column property="name" title="${nameHeader}" />
<display:column property="surname" title="${surnameHeader}" />
</display:table>

<spring:message code="admin.worstdrivers"/>
<display:table name="worstDrivers" id="row" requestURI="admin/dashboard.do" class="displaytag">
<display:column property="userAccount.username" title="${usernameHeader}" />
<display:column property="name" title="${nameHeader}" />
<display:column property="surname" title="${surnameHeader}" />
</display:table>

<spring:message code="admin.worstmechanics"/>
<display:table name="worstMechanics" id="row" requestURI="admin/dashboard.do" class="displaytag">
<display:column property="userAccount.username" title="${usernameHeader}" />
<display:column property="name" title="${nameHeader}" />
<display:column property="surname" title="${surnameHeader}" />
</display:table>

<spring:message code="admin.mostwritten"/>
<display:table name="mostWritten" id="row" requestURI="admin/dashboard.do" class="displaytag">
<display:column property="userAccount.username" title="${usernameHeader}" />
<display:column property="name" title="${nameHeader}" />
<display:column property="surname" title="${surnameHeader}" />
</display:table>

<spring:message code="admin.mostreceived"/>
<display:table name="mostReceived" id="row" requestURI="admin/dashboard.do" class="displaytag">
<display:column property="userAccount.username" title="${usernameHeader}" />
<display:column property="name" title="${nameHeader}" />
<display:column property="surname" title="${surnameHeader}" />
</display:table>