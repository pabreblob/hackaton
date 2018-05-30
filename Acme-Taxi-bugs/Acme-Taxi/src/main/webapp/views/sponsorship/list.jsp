<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<display:table name="sponsorships" id="sponsorship"
	requestURI="${requestURI}" pagesize="5" class="displaytag"
	sort="external" partialList="true" size="${total }">

	<security:authorize access="hasRole('SPONSOR')">

		<display:column>
			<jstl:if test="${sponsorship.accepted == false }">
				<a
					href="sponsorship/sponsor/delete.do?sponsorshipId=${sponsorship.id}" onclick="return confirm('<spring:message code='sponsorship.confirm.delete' />')">
					<spring:message code="sponsorship.delete" />
				</a>
			</jstl:if>
			<jstl:if test="${sponsorship.accepted == true }">
				<jstl:if test="${sponsorship.cancelled == false}">
					<a
						href="sponsorship/sponsor/cancel.do?sponsorshipId=${sponsorship.id}" onclick="return confirm('<spring:message code='sponsorship.confirm.cancel' />')">
						<spring:message code="sponsorship.cancel" />
					</a>
				</jstl:if>
			</jstl:if>
		</display:column>
	</security:authorize>


	<security:authorize access="hasRole('ADMIN')">
		<display:column>
			<jstl:if test="${sponsorship.accepted == false}">
				<a
					href="sponsorship/admin/accept.do?sponsorshipId=${sponsorship.id}">
					<spring:message code="sponsorship.accept" />
				</a>
			</jstl:if>

		</display:column>
		<display:column>
			<a href="sponsorship/admin/delete.do?sponsorshipId=${sponsorship.id}">
				<spring:message code="sponsorship.delete" />
			</a>
		</display:column>

		<spring:message code="sponsorship.sponsor" var="sponsorHeader" />
		<display:column property="sponsor.name" title="${sponsorHeader}" />
	</security:authorize>



	<spring:message code="sponsorship.date" var="dateHeader" />
	<spring:message code="sponsorship.dateFormat2" var="dateFormatHeader" />
	<display:column title="${dateHeader}">
		<fmt:formatDate value="${sponsorship.moment}"
			pattern="${dateFormatHeader}" />
	</display:column>
	<spring:message code="sponsorship.accepted" var="acceptedHeader" />
	<display:column title="${acceptedHeader}">
		<jstl:if test="${sponsorship.accepted == true}">
			<spring:message code="sponsorship.true" />
		</jstl:if>
		<jstl:if test="${sponsorship.accepted == false}">
			<spring:message code="sponsorship.false" />
		</jstl:if>
	</display:column>
	<spring:message code="sponsorship.cancelled" var="cancelledHeader" />
	<display:column title="${cancelledHeader}">
		<jstl:if test="${sponsorship.cancelled == true}">
			<spring:message code="sponsorship.true" />
		</jstl:if>
		<jstl:if test="${sponsorship.cancelled == false}">
			<spring:message code="sponsorship.false" />
		</jstl:if>
	</display:column>

	<security:authorize access="hasRole('SPONSOR')">
		<display:column>
			<a
				href="sponsorship/sponsor/display.do?sponsorshipId=${sponsorship.id}">
				<spring:message code="sponsorship.display" />
			</a>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('ADMIN')">
		<display:column>
			<a
				href="sponsorship/admin/display.do?sponsorshipId=${sponsorship.id}">
				<spring:message code="sponsorship.display" />
			</a>
		</display:column>
	</security:authorize>
</display:table>

<br />

<security:authorize access="hasRole('SPONSOR')">
	<a href="sponsorship/sponsor/create.do"> <spring:message
			code="sponsorship.create" />
	</a>
</security:authorize>
