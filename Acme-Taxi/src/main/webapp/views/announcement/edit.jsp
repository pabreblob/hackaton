<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="announcement/user/edit.do" modelAttribute="announcement">
<form:hidden path="id"/>
<form:hidden path="version"/>
<acme:textbox code="announcement.title" path="title"/>
<acme:textbox code="announcement.origin" path="origin"/>
<acme:textbox code="announcement.destination" path="destination"/>
<acme:textbox code="announcement.price" path="pricePerPerson"/>
<acme:textbox code="announcement.moment" path="moment" placeholder="dd/MM/yyyy HH:mm"/>
<acme:textarea code="announcement.description" path="description"/>
<acme:checkbox code="announcement.pets" path="petsAllowed"/>
<acme:checkbox code="announcement.smoking" path="smokingAllowed"/>
<acme:textbox code="announcement.seats" path="seats"/>
<acme:submit name="save" code="announcement.save"/>
<acme:cancel code="announcement.cancel" url="/announcement/user/list-created.do"/>
<jstl:if test="${announcement.id != 0}">
<acme:submit name="delete" code="announcement.delete"/>
</jstl:if>
</form:form>
