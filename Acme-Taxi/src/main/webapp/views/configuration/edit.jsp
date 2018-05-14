<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="configuration/admin/edit.do" modelAttribute="configuration">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="legalTextEng"/>
	<form:hidden path="legalTextEsp"/>
	
	<acme:textbox code="configuration.bannerUrl" path="bannerUrl"/>
	<acme:textbox code="configuration.currency" path="currency"/>
	<acme:textbox code="configuration.pricePerKm" path="pricePerKm"/>
	<acme:textbox code="configuration.minimumFee" path="minimumFee"/>
	<acme:textbox code="configuration.advertisementPrice" path="advertisementPrice"/>
	<acme:textbox code="configuration.limitReportsWeek" path="limitReportsWeek"/>
	<acme:textbox code="configuration.countryCode" path="countryCode"/>
	<acme:textbox code="configuration.vat" path="vat"/>
	<acme:textarea code="configuration.footer" path="footer"/>
	<acme:textarea code="configuration.welcomeEng" path="welcomeEng"/>
	<acme:textarea code="configuration.welcomeEsp" path="welcomeEsp"/>
	<acme:textarea code="configuration.cookiesPolicyEng" path="cookiesPolicyEng"/>
	<acme:textarea code="configuration.cookiesPolicyEsp" path="cookiesPolicyEsp"/>
	<acme:textarea code="configuration.contactEng" path="contactEng"/>
	<acme:textarea code="configuration.contactEsp" path="contactEsp"/>
	<acme:textarea code="configuration.acceptCookiesEng" path="acceptCookiesEng"/>
	<acme:textarea code="configuration.acceptCookiesEsp" path="acceptCookiesEsp"/>
	<acme:textbox code="configuration.nationalities" path="nationalities"/>
	
	<acme:submit name="submit" code="configuration.submit"/>
	<acme:cancel url="configuration/admin/display.do" code="configuration.cancel"/>
</form:form>