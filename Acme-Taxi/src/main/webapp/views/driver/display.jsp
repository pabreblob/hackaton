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
	<spring:message code="driver.username" />:
	<jstl:out value="${driver.userAccount.username}" />
</p>
<p>
	<spring:message code="driver.name" />:
	<jstl:out value="${driver.name}" />
</p>
<p>
	<spring:message code="driver.surname" />:
	<jstl:out value="${driver.surname}" />
</p>
<spring:message code="driver.dateFormat" var="dateFormat" />
<p>
	<spring:message code="driver.birthdate" />:
	<fmt:formatDate value="${driver.birthdate}" pattern="${dateFormat}" />
</p>

<p>
	<spring:message code="driver.idNumber" />:
	<jstl:out value="${driver.idNumber}" />
</p>

<p>
	<spring:message code="driver.nacionality" />:
	<jstl:out value="${driver.nationality}" />
</p>
<jstl:if test='${not empty driver.phone}'>
	<p>
		<spring:message code="driver.phone" />:
		<jstl:out value="${driver.phone}" />
	</p>
</jstl:if>

<jstl:if test='${not empty driver.photoUrl}'>
	<spring:message code="driver.photo" var="driverPhoto" />
	<p>
		<img src="${driver.photoUrl}" alt="${driverPhoto}" />
	</p>
</jstl:if>
<p>
	<spring:message code="driver.email" />:
	<jstl:out value="${driver.email}" />
</p>

<p>
	<spring:message code="driver.location" />:
	<jstl:out value="${driver.location}" />
</p>

<p>
	<spring:message code="driver.rating" />:
	<jstl:out value="${driver.meanRating}" />
</p>

 <jstl:if test="${!empty driver.reviews}"> 
<a href="review/list-driver.do?driverId=${driver.id }">
	<spring:message code="driver.reviews" />
</a>
</jstl:if> 
<br/>
<jstl:if test="${requestURI == 'driver/driver/display.do'}">
<jstl:if test="${driver.car != null}"> 
<a href="car/display.do">
	<spring:message code="driver.displaycar" />
</a>
</jstl:if>
<jstl:if test="${driver.car == null}"> 
<a href="car/create.do">
	<spring:message code="driver.createcar"/>
</a>
</jstl:if>  
</jstl:if>


<jstl:if test="${requestURI != 'driver/driver/display.do'}">
<jstl:if test="${driver.car != null}"> 
<a href="car/display.do?carId=${driver.car.id }">
	<spring:message code="driver.displaycar" />
</a>
</jstl:if>
</jstl:if>


<jstl:if test="${requestURI == 'driver/driver/display.do'}">
<br />
	<a href="driver/driver/edit.do"> <spring:message code="driver.edit" />
	</a>
</jstl:if>

