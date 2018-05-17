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
	<spring:message code="repairShop.name" />
	:
	<jstl:out value="${repairShop.name}" />
</p>
<p>
	<spring:message code="repairShop.mechanic" />
	:
	<a href="mechanic/display.do?mechanicId=${repairShop.mechanic.id}"> <jstl:out value="${repairShop.mechanic.userAccount.username}" />
	</a>
	
</p>
<p>
	<spring:message code="repairShop.description" />
	:
	<jstl:out value="${repairShop.description}" />
</p>

<p>
	<spring:message code="repairShop.phone" />
	:
	<jstl:out value="${repairShop.phone}" />
</p>
<jstl:if test='${not empty repairShop.photoUrl}'>
<spring:message code="repairShop.photo" var="repairShopPhoto" />
<p>
	<img src="${repairShop.photoUrl}" alt="${repairShopPhoto}" />
</p>
</jstl:if>
<p>
	<spring:message code="repairShop.location" />
	:
	<jstl:out value="${repairShop.location}" />
</p>
<p>
	<spring:message code="repairShop.meanRating" />
	:
	<jstl:out value="${repairShop.meanRating}" />
</p>
<jstl:if test='${total!=0}'>
<display:table class="displaytag" name="services" requestURI="${requestURI}" pagesize="5" id="row" sort="external" partialList="true"
size="${total}">

<jstl:if test="${requestURI =='repairShop/user/display.do' }">
<display:column>
<jstl:if test="${!row.suspended }">
<jstl:set var="contains" value="false"/>
<jstl:forEach var="item" items="${servicesReserved}">
				<jstl:if test="${item.id == row.id}">
					<jstl:set var="contains" value="true"/>
				</jstl:if>
			</jstl:forEach>

<jstl:if test="${!contains}">

<a href="reservation/user/create.do?serviceId=${row.id}"> <spring:message
					code="repairShop.service.reserve" />
			</a>
</jstl:if>

</jstl:if>
</display:column>
</jstl:if>

<jstl:if test='${owner}'>
<display:column>
<jstl:if test='${row.suspended}'>
<a href="service/mechanic/suspend-service.do?serviceId=${row.id}"> <spring:message
					code="repairShop.service.reopen" />
			</a>
</jstl:if>
<jstl:if test='${!row.suspended}'>
<a href="service/mechanic/suspend-service.do?serviceId=${row.id}"> <spring:message
					code="repairShop.service.suspend" />
			</a>
</jstl:if>
</display:column>
</jstl:if>
<jstl:if test='${owner}'>
<display:column>
<a href="service/mechanic/display.do?serviceId=${row.id}"> <spring:message
					code="repairShop.display" />
			</a>
</display:column>
</jstl:if>
<spring:message code="repairShop.service.title" var="titleHeader" />
<display:column property="title" title="${titleHeader}" />
<spring:message code="repairShop.service.price" var="priceHeader" />
<display:column title="${priceHeader}" sortable="true" sortName="price" >
<jstl:out value="${row.price}" /><jstl:out value="${currency}" />
</display:column>
<spring:message code="repairShop.service.status" var="statusHeader" />
<display:column title="${statusHeader}" >
<jstl:if test="${row.suspended }">
<spring:message code="repairShop.service.suspended" />
</jstl:if>
<jstl:if test="${!row.suspended }">
<spring:message code="repairShop.service.available" />
</jstl:if>
</display:column>
</display:table>
</jstl:if>
<jstl:if test='${owner}'>
<a href="service/mechanic/create.do?repairShopId=${repairShop.id}"> <spring:message
					code="repairShop.service.create" />
			</a>
			</jstl:if>
