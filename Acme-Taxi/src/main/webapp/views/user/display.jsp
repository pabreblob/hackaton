<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<p>
	<spring:message code="user.username" />:
	<jstl:out value="${user.userAccount.username}" />
</p>
<p>
	<spring:message code="user.name" />:
	<jstl:out value="${user.name}" />
</p>
<p>
	<spring:message code="user.surname" />:
	<jstl:out value="${user.surname}" />
</p>
<spring:message code="user.dateFormat" var="dateFormat" />
<p>
	<spring:message code="user.birthdate" />:
	<fmt:formatDate value="${user.birthdate}" pattern="${dateFormat}" />
</p>

<jstl:if test='${not empty user.phone}'>
	<p>
		<spring:message code="user.phone" />:
		<jstl:out value="${user.phone}" />
	</p>
</jstl:if>

<jstl:if test='${not empty user.photoUrl}'>
	<spring:message code="user.photo" var="userPhoto" />
	<p>
		<img src="${user.photoUrl}" alt="${userPhoto}" />
	</p>
</jstl:if>
<p>
	<spring:message code="user.email" />:
	<jstl:out value="${user.email}" />
</p>

<p>
	<spring:message code="user.location" />:
	<jstl:out value="${user.location}" />
</p>

<p>
	<spring:message code="user.rating" />:
	<jstl:out value="${user.meanRating}" />
</p>

<jstl:if test='${blockeable}'>
<a href="actor/actor/block.do?actorId=${user.id}"> <spring:message code="user.block" />
	</a>
</jstl:if>

<jstl:if test='${unblockeable}'>
<a href="actor/actor/unblock.do?actorId=${user.id}"> <spring:message code="user.unblock" />
	</a>
</jstl:if><p>
<jstl:if test='${me == false}'>
<a href="report/actor/create.do?actorId=${user.id}"> <spring:message code="user.report" /></a>
</jstl:if>
</p>

<jstl:if test="${!empty user.reviews}"> 
<a href="review/list-user.do?userId=${user.id }">
	<spring:message code="user.reviews" />
</a>
</jstl:if> 
<br/>

<jstl:if test="${requestURI == 'user/user/display.do'}">
<br />
	<a href="user/user/edit.do"> <spring:message code="user.edit" />
	</a>
</jstl:if>

