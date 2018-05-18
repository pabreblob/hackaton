<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fn" uri ="http://java.sun.com/jsp/jstl/functions"  %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<p>
	<spring:message code="car.driver" />
	:
	<a href="driver/display.do?driverId=${driver.id}"> <jstl:out value="${driver.userAccount.username}" />
	</a>
	
</p>
<jstl:if test='${repairShop!=null}'>
<security:authorize access="hasRole('USER')">
<p>
	<spring:message code="car.repairShop" />
	:
	<a href="repairShop/user/display.do?repairShopId=${repairShop.id}"> <jstl:out value="${repairShop.name}" />
	</a>
	
</p>
</security:authorize>
<security:authorize access="hasRole('MECHANIC')">
<p>
	<spring:message code="car.repairShop" />
	:
	<a href="repairShop/mechanic/display.do?repairShopId=${repairShop.id}"> <jstl:out value="${repairShop.name}" />
	</a>
	
</p>
</security:authorize>
<security:authorize access="hasRole('ADMIN')">
<p>
	<spring:message code="car.repairShop" />
	:
	<a href="repairShop/display.do?repairShopId=${repairShop.id}"> <jstl:out value="${repairShop.name}" />
	</a>
	
</p>
</security:authorize>
<security:authorize access="hasRole('DRIVER')">
<p>
	<spring:message code="car.repairShop" />
	:
	<a href="repairShop/display.do?repairShopId=${repairShop.id}"> <jstl:out value="${repairShop.name}" />
	</a>
	
</p>
</security:authorize>
<security:authorize access="hasRole('SPONSOR')">
<p>
	<spring:message code="car.repairShop" />
	:
	<a href="repairShop/display.do?repairShopId=${repairShop.id}"> <jstl:out value="${repairShop.name}" />
	</a>
	
</p>
</security:authorize>
<security:authorize access="isAnonymous()">
<p>
	<spring:message code="car.repairShop" />
	:
	<a href="repairShop/display.do?repairShopId=${repairShop.id}"> <jstl:out value="${repairShop.name}" />
	</a>
	
</p>
</security:authorize>
</jstl:if>
<p>
	<spring:message code="car.carModel" />
	:
	<jstl:out value="${car.carModel}" />
</p>
<p>
	<spring:message code="car.maxPassengers" />
	:
	<jstl:out value="${car.maxPassengers}" />
</p>
<p>
	<spring:message code="car.numberPlate" />
	:
	<jstl:out value="${car.numberPlate}" />
</p>
<security:authorize access="hasRole('ADMIN')">
<p>
	<a href="car/admin/delete.do?carId=${car.id}"> <spring:message code="car.delete" />
	</a>
	
</p>
</security:authorize>
<p>
<jstl:if test="${requestURI == 'car/driver/display.do'}">
<jstl:if test='${pendingRequests==0}'>
<a href="car/driver/edit.do?carId=${car.id}"> <spring:message code="car.edit" />
	</a>
</jstl:if>


</jstl:if>
</p>
<jstl:if test="${requestURI == 'car/driver/display.do'}">
<p>
<a href="repairShop/driver/select.do"> <spring:message code="car.selectRepair" />
	</a>
	</p>
<jstl:if test="${repairShop!=null}">
<p>
<a href="car/driver/remove-shop.do"> <spring:message code="car.removeRepair" /></a>
</p>
	</jstl:if>
	
</jstl:if>




