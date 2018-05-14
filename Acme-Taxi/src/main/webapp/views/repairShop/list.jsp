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
</jstl:if>
<spring:message code="repairShop.name" var="nameHeader" />
<display:column property="name" title="${nameHeader}" />
<spring:message code="repairShop.location" var="locationHeader" />
<display:column property="location" title="${locationHeader}" sortable="true" sortName="location" />
<spring:message code="repairShop.meanRating" var="meanRatingHeader" />
<display:column property="meanRating" title="${meanRatingHeader}" sortable="true" sortName="meanRating" />
<display:column>
			<a href="repairShop/display.do?repairShopId=${row.id}"> <spring:message
					code="repairShop.display" />
			</a>

		</display:column>
</display:table>
<security:authorize access="hasRole('MECHANIC')">
<a href="repairShop/mechanic/create.do"> <spring:message
					code="repairShop.create" />
			</a>
</security:authorize>



