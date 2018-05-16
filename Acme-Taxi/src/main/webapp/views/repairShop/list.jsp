<%--
 * 
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
<display:table name="repairShops" id="row" requestURI="${requestURI}" keepStatus="true"
	pagesize="5" class="displaytag" sort="external" partialList="true"
	size="${total}">
<jstl:if test="${requestURI == 'repairShop/mechanic/list-created.do' }">
<display:column>
			<a href="repairShop/mechanic/edit.do?repairShopId=${row.id}"> <spring:message
					code="repairShop.edit" />
			</a>

		</display:column>


<display:column>
			<a href="reservation/mechanic/list-shop.do?repairShopId=${row.id}"> <spring:message
					code="repairShop.reservation.list" />
			</a>

		</display:column>
</jstl:if>
<jstl:if test="${requestURI == 'repairShop/user/list-reviewable.do' }">
<display:column>
			<a href="review/user/create-repairShop.do?repairShopId=${row.id}"> <spring:message
					code="repairShop.review.create" />
			</a>

		</display:column>
		</jstl:if>
<security:authorize access="hasRole('ADMIN')">
<display:column>
			<a href="repairShop/admin/delete.do?repairShopId=${row.id}&requestURI=${requestURI}"> <spring:message
					code="repairShop.delete" />
			</a>
</display:column>
</security:authorize>
<spring:message code="repairShop.name" var="nameHeader" />
<display:column property="name" title="${nameHeader}" />
<spring:message code="repairShop.location" var="locationHeader" />
<display:column property="location" title="${locationHeader}" sortable="true" sortName="location" />
<spring:message code="repairShop.meanRating" var="meanRatingHeader" />
<display:column property="meanRating" title="${meanRatingHeader}" sortable="true" sortName="meanRating" />
<display:column>
<security:authorize access="isAnonymous()">
			<a href="repairShop/display.do?repairShopId=${row.id}"> <spring:message
					code="repairShop.display" />
			</a>
</security:authorize>
<security:authorize access="hasRole('ADMIN')">
			<a href="repairShop/display.do?repairShopId=${row.id}"> <spring:message
					code="repairShop.display" />
			</a>
</security:authorize>
<security:authorize access="hasRole('USER')">
			<a href="repairShop/user/display.do?repairShopId=${row.id}"> <spring:message
					code="repairShop.display" />
			</a>
</security:authorize>
<security:authorize access="hasRole('DRIVER')">
			<a href="repairShop/display.do?repairShopId=${row.id}"> <spring:message
					code="repairShop.display" />
			</a>
</security:authorize>
<security:authorize access="hasRole('SPONSOR')">
			<a href="repairShop/display.do?repairShopId=${row.id}"> <spring:message
					code="repairShop.display" />
			</a>
</security:authorize>
<security:authorize access="hasRole('MECHANIC')">
			<a href="repairShop/mechanic/display.do?repairShopId=${row.id}"> <spring:message
					code="repairShop.display" />
			</a>
</security:authorize>

		</display:column>
</display:table>
<security:authorize access="hasRole('MECHANIC')">
<a href="repairShop/mechanic/create.do"> <spring:message
					code="repairShop.create" />
			</a>
</security:authorize>




