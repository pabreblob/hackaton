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

<jstl:if test="${form == 'true' }">
	<form:form action="review/user/save-driver.do" modelAttribute="reviewForm">

		<form:hidden path="driver"/>
		<form:hidden path="repairShop"/>
		<form:hidden path="user"/>
		<form:hidden path="creator"/>

		<acme:textbox code="review.title" path="title" />
		<acme:textarea code="review.body" path="body"/>
		<form:label path="rating">
			<spring:message code="review.rating" />
		</form:label>
		<form:select path="rating">
			<form:option value="1" label="1" />
			<form:option value="2" label="2" />
			<form:option value="3" label="3" />
			<form:option value="4" label="4" />
			<form:option value="5" label="5" />	
		</form:select>
		<br />	
		<acme:submit name="save" code="review.save" />
		<acme:cancel code="review.cancel" url="${cancel }" />
	</form:form>


</jstl:if>

<jstl:if test="${form == 'false' }">
	<form:form action="review/user/edit.do" modelAttribute="review">

		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="moment"/>
		<form:hidden path="creator"/>

		<acme:textbox code="review.title" path="title" />
		<acme:textarea code="review.body" path="body"/>
		<form:label path="rating">
			<spring:message code="review.rating" />
		</form:label>
		<form:select path="rating">
			<form:option value="1" label="1" />
			<form:option value="2" label="2" />
			<form:option value="3" label="3" />
			<form:option value="4" label="4" />
			<form:option value="5" label="5" />	
		</form:select>
		<br />	
		<acme:submit name="save" code="review.save" />
		<acme:cancel code="review.cancel" url="${cancel }" />
	</form:form>


</jstl:if>