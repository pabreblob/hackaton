<%--
 * action-2.jsp
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="car/driver/edit.do" modelAttribute="car">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<acme:textbox code="car.carModel" path="carModel" /><br />
	<acme:textbox code="car.numberPlate" path="numberPlate" /><br />
	<acme:textbox code="car.maxPassengers" path="maxPassengers" /><br />

	<acme:submit name="save" code="car.save"  />
	<jstl:if test="${car.id != 0}">
	<input type="submit" name="delete"
				value="<spring:message code="car.delete" />"
				onclick="return confirm('<spring:message code="car.confirm.delete" />')" />
	</jstl:if>
	<jstl:if test="${car.id == 0}">
	<acme:cancel code="car.cancel" url="/driver/driver/display.do?driverId=${driver.id}" /><br />
	</jstl:if>
	<jstl:if test="${car.id != 0}">
	<acme:cancel code="car.cancel" url="/car/driver/display.do" /><br />
	</jstl:if>	
</form:form>



