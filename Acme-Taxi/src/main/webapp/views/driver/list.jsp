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

<display:table pagesize="5" class="displaytag" 
	name="drivers" requestURI="${requestURI}" id="row">
	
	<display:column>
		<a href="review/user/create-driver.do?driverId=${row.id}"> <spring:message
				code="driver.review" />
		</a>
	</display:column>
	
	<spring:message code="driver.username" var="uNameHeader" />
	<display:column property="userAccount.username" title="${uNameHeader}" />
	<spring:message code="driver.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" />
	<spring:message code="driver.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" />
	
	<display:column>
		<a href="driver/display.do?driverId=${row.id}"> <spring:message
				code="driver.display" />
		</a>
	</display:column>
	
</display:table>