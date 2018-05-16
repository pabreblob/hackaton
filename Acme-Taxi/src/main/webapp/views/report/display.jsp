<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div style="width:30%; border: black solid 1px;">
	<h2 style="text-align: center"><spring:message code="report.report"/></h2>
	<p style="margin-left:2%;"><b><spring:message code="report.reporting"/></b><jstl:out value="${report.reported.name}"/> <jstl:out value="${report.reported.surname}"/> (<a href="actor/display.do?actorId=${report.reported.id}"><jstl:out value="${report.reported.userAccount.username}"/></a>)</p>
	<p style="margin-left:2%;"><b><spring:message code="report.writtenBy"/></b><jstl:out value="${report.creator.name}"/> <jstl:out value="${report.creator.surname}"/> (<a href="actor/display.do?actorId=${report.creator.id}"><jstl:out value="${report.reported.userAccount.username}"/></a>)</p>
	<p style="margin-left:2%;"><b><spring:message code="report.reason"/></b></p>
	<p style="margin-left:2%; margin-right:2%;"><jstl:out value="${report.reason}"/></p>
	<spring:message code="report.format" var="dateFormat"/>
	<p style="margin-left:2%;"><b><spring:message code="report.moment"/>: </b><fmt:formatDate value="${report.moment}" pattern="${dateFormat}" /></p>
	<jstl:if test="${report.imageUrl != ''}">
		<img id="myImg" src="<jstl:out value="${report.imageUrl}"/>" width="80%" style="margin-bottom: 2%; margin-left: auto; margin-right: auto; display:block; position:center;"/>
	</jstl:if>
</div>
