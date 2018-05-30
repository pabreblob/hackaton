<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:if test="${eng == true}">
<form:form action="configuration/admin/edit-legaltexteng.do" modelAttribute="legalText">
	
	<acme:textarea code="configuration.legalText" path="text"/>
	
	<acme:submit name="submit" code="configuration.submit"/>
	<acme:cancel url="configuration/admin/display.do" code="configuration.cancel"/>

	</form:form>
</jstl:if>

<jstl:if test="${eng == false}">
	<form:form action="configuration/admin/edit-legaltextesp.do" modelAttribute="legalText">
	
		<acme:textarea code="configuration.legalText" path="text"/>
		
		<acme:submit name="submit" code="configuration.submit"/>
		<acme:cancel url="configuration/admin/display.do" code="configuration.cancel"/>
		
	</form:form>
</jstl:if>