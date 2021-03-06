<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<p>
	<spring:message code="sponsor.username" />
	:
	<jstl:out value="${sponsor.userAccount.username}" />
</p>
<p>
	<spring:message code="sponsor.name" />
	:
	<jstl:out value="${sponsor.name}" />
</p>
<p>
	<spring:message code="sponsor.surname" />
	:
	<jstl:out value="${sponsor.surname}" />
</p>
<spring:message code="sponsor.dateFormat" var="dateFormat" />
<p>
<spring:message code="sponsor.birthdate"/>:<fmt:formatDate value="${sponsor.birthdate}" pattern="${dateFormat}" />
</p>

<p>
	<spring:message code="sponsor.idNumber" />
	:
	<jstl:out value="${sponsor.idNumber}" />
</p>
<jstl:if test='${not empty sponsor.phone}'>
<p>
	<spring:message code="sponsor.phone" />
	:
	<jstl:out value="${sponsor.phone}" />
</p>
</jstl:if>
<p>
	<spring:message code="sponsor.email" />
	:
	<jstl:out value="${sponsor.email}" />
</p>

<jstl:if test='${blockeable}'>
<a href="actor/actor/block.do?actorId=${sponsor.id}"> <spring:message code="sponsor.block" />
	</a>
</jstl:if>

<jstl:if test='${unblockeable}'>
<a href="actor/actor/unblock.do?actorId=${sponsor.id}"> <spring:message code="sponsor.unblock" />
	</a>
</jstl:if><p>
<jstl:if test='${me == false}'>
<a href="report/actor/create.do?actorId=${sponsor.id}"> <spring:message code="sponsor.report" /></a>
</jstl:if>
</p>

<security:authorize access="hasRole('ADMIN')">
<jstl:if test='${!banned}'>
<a href="actor/admin/ban.do?actorId=${sponsor.id}&returnUri=sponsor"> <spring:message code="sponsor.ban" /></a>
</jstl:if>

<jstl:if test='${banned}'>
<a href="actor/admin/unban.do?actorId=${sponsor.id}&returnUri=sponsor"> <spring:message code="sponsor.unban" /></a>
</jstl:if>
</security:authorize>

<jstl:if test="${requestURI == 'sponsor/sponsor/display.do'}">
<a href = "sponsor/sponsor/edit.do">
<spring:message code="sponsor.edit" />
</a>
</jstl:if>

