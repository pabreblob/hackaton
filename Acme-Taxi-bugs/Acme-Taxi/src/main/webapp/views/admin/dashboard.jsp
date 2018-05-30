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

<spring:message code="admin.topusers"/>
<table>
<tr>
<th><spring:message code="actor.username"/></th>
<th><spring:message code="actor.name"/></th>
<th><spring:message code="actor.surname"/></th>
<th><spring:message code="admin.score"/></th>
</tr>
<jstl:forEach var="user" items="${topUsers}" >
<tr>
<td>
<jstl:out value="${user.userAccount.username}"/>
</td>
<td>
<jstl:out value="${user.name}"/>
</td>
<td>
<jstl:out value="${user.surname}"/>
</td>
<td>
<jstl:out value="${user.meanRating}"/>
</td>
</tr>
</jstl:forEach>
</table>

<spring:message code="admin.topdrivers"/>
<table>
<tr>
<th><spring:message code="actor.username"/></th>
<th><spring:message code="actor.name"/></th>
<th><spring:message code="actor.surname"/></th>
<th><spring:message code="admin.score"/></th>
</tr>
<jstl:forEach var="driver" items="${topDrivers}" >
<tr>
<td>
<jstl:out value="${driver.userAccount.username}"/>
</td>
<td>
<jstl:out value="${driver.name}"/>
</td>
<td>
<jstl:out value="${driver.surname}"/>
</td>
<td>
<jstl:out value="${driver.meanRating}"/>
</td>
</tr>
</jstl:forEach>
</table>

<spring:message code="admin.topmechanics"/>
<table>
<tr>
<th><spring:message code="actor.username"/></th>
<th><spring:message code="actor.name"/></th>
<th><spring:message code="actor.surname"/></th>
<th><spring:message code="admin.score"/></th>
</tr>
<jstl:forEach var="mechanic" items="${topMechanics}" >
<tr>
<td>
<jstl:out value="${mechanic.key.userAccount.username}"/>
</td>
<td>
<jstl:out value="${mechanic.key.name}"/>
</td>
<td>
<jstl:out value="${mechanic.key.surname}"/>
</td>
<td>
<jstl:out value="${mechanic.value}"/>
</td>
</tr>
</jstl:forEach>
</table>

<spring:message code="admin.worstusers"/>
<table>
<tr>
<th><spring:message code="actor.username"/></th>
<th><spring:message code="actor.name"/></th>
<th><spring:message code="actor.surname"/></th>
<th><spring:message code="admin.score"/></th>
</tr>
<jstl:forEach var="user" items="${worstUsers}" >
<tr>
<td>
<jstl:out value="${user.userAccount.username}"/>
</td>
<td>
<jstl:out value="${user.name}"/>
</td>
<td>
<jstl:out value="${user.surname}"/>
</td>
<td>
<jstl:out value="${user.meanRating}"/>
</td>
</tr>
</jstl:forEach>
</table>

<spring:message code="admin.worstdrivers"/>
<table>
<tr>
<th><spring:message code="actor.username"/></th>
<th><spring:message code="actor.name"/></th>
<th><spring:message code="actor.surname"/></th>
<th><spring:message code="admin.score"/></th>
</tr>
<jstl:forEach var="driver" items="${worstDrivers}" >
<tr>
<td>
<jstl:out value="${driver.userAccount.username}"/>
</td>
<td>
<jstl:out value="${driver.name}"/>
</td>
<td>
<jstl:out value="${driver.surname}"/>
</td>
<td>
<jstl:out value="${driver.meanRating}"/>
</td>
</tr>
</jstl:forEach>
</table>

<spring:message code="admin.worstmechanics"/>
<table>
<tr>
<th><spring:message code="actor.username"/></th>
<th><spring:message code="actor.name"/></th>
<th><spring:message code="actor.surname"/></th>
<th><spring:message code="admin.score"/></th>
</tr>
<jstl:forEach var="mechanic" items="${worstMechanics}" >
<tr>
<td>
<jstl:out value="${mechanic.key.userAccount.username}"/>
</td>
<td>
<jstl:out value="${mechanic.key.name}"/>
</td>
<td>
<jstl:out value="${mechanic.key.surname}"/>
</td>
<td>
<jstl:out value="${mechanic.value}"/>
</td>
</tr>
</jstl:forEach>
</table>

<spring:message code="admin.mostwritten"/>
<table>
<tr>
<th><spring:message code="actor.username"/></th>
<th><spring:message code="actor.name"/></th>
<th><spring:message code="actor.surname"/></th>
<th><spring:message code="admin.reports"/></th>
</tr>
<jstl:forEach var="actor" items="${mostWritten}" >
<tr>
<td>
<jstl:out value="${actor.key.userAccount.username}"/>
</td>
<td>
<jstl:out value="${actor.key.name}"/>
</td>
<td>
<jstl:out value="${actor.key.surname}"/>
</td>
<td>
<jstl:out value="${actor.value}"/>
</td>
</tr>
</jstl:forEach>
</table>

<spring:message code="admin.mostreceived"/>
<table>
<tr>
<th><spring:message code="actor.username"/></th>
<th><spring:message code="actor.name"/></th>
<th><spring:message code="actor.surname"/></th>
<th><spring:message code="admin.reports"/></th>
</tr>
<jstl:forEach var="actor" items="${mostReceived}" >
<tr>
<td>
<jstl:out value="${actor.key.userAccount.username}"/>
</td>
<td>
<jstl:out value="${actor.key.name}"/>
</td>
<td>
<jstl:out value="${actor.key.surname}"/>
</td>
<td>
<jstl:out value="${actor.value}"/>
</td>
</tr>
</jstl:forEach>
</table>