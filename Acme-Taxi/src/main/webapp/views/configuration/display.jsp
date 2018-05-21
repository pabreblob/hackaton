<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<style>
table{
	border-collapse: collapse;
}
tr{
	border: 1px solid black;
}
table tr:last-child{
	border-bottom: none;
}
tr:nth-child(odd){
	background-color: lightgreen;
	}
tr:nth-child(even){
	background-color: lightblue;
}
</style>

<table>
	<tr>
		<td style="width: 15%"><b><spring:message code="configuration.bannerUrl"/></b></td><td style="text-align: center"><jstl:out value="${config.bannerUrl}"/></td>
	</tr>
	<tr>
		<td><b><spring:message code="configuration.currency"/></b></td><td style="text-align: center"><jstl:out value="${config.currency}"/></td>
	</tr>
	<tr>
		<td><b><spring:message code="configuration.pricePerKm"/></b></td><td style="text-align: center"><jstl:out value="${config.pricePerKm}"/></td>
	</tr>
	<tr>
		<td><b><spring:message code="configuration.minimumFee"/></b></td><td style="text-align: center"><jstl:out value="${config.minimumFee}"/></td>
	</tr>
	<tr>
		<td><b><spring:message code="configuration.advertisementPrice"/></b></td><td style="text-align: center"><jstl:out value="${config.advertisementPrice}"/></td>
	</tr>
	<tr>
		<td><b><spring:message code="configuration.limitReportsWeek"/></b></td><td style="text-align: center"><jstl:out value="${config.limitReportsWeek}"/></td>
	</tr>
	<tr>
		<td><b><spring:message code="configuration.vat"/></b></td><td style="text-align: center"><jstl:out value="${config.vat}"/></td>
	</tr>
	<tr>
		<td><b><spring:message code="configuration.countryCode"/></b></td><td style="text-align: center"><jstl:out value="${config.countryCode}"/></td>
	</tr>
	<tr>
		<td><b><spring:message code="configuration.footer"/></b></td><td style="text-align: center"><jstl:out value="${config.footer}"/></td>
	</tr>
	<tr>
		<td><b><spring:message code="configuration.useapi"/></b></td><td style="text-align: center">
			<jstl:if test="${config.useApi == true}">
				<spring:message code="configuration.yes" var="useApi"/>
			</jstl:if>
			<jstl:if test="${config.useApi == false}">
				<spring:message code="configuration.no" var="useApi"/>
			</jstl:if>
			<jstl:out value="${useApi}"/>
		</td>
	</tr>
</table>
<h2><spring:message code="configuration.texts"/></h2>
<table>
	<tr>
		<th></th><th style="text-align: center"><spring:message code="configuration.english"/></th><th style="text-align: center"><spring:message code="configuration.spanish"/></th>
	</tr>
	<tr>
		<td><b><spring:message code="configuration.welcome"/></b></td><td style="text-align: center"><jstl:out value="${config.welcomeEng}"/></td><td style="text-align: center"><jstl:out value="${config.welcomeEsp}"/></td>
	</tr>
	<tr>
		<td><b><spring:message code="configuration.legalText"/></b></td><td style="text-align: center"><a href="configuration/admin/display-legaltexteng.do"><spring:message code="configuration.legaltexteng.link"/></a></td><td style="text-align: center"><a href="configuration/admin/display-legaltextesp.do"><spring:message code="configuration.legaltextesp.link"/></a></td>
	</tr>
	<tr>
		<td><b><spring:message code="configuration.cookiesPolicy"/></b></td><td style="text-align: center"><jstl:out value="${config.cookiesPolicyEng}"/></td><td style="text-align: center"><jstl:out value="${config.cookiesPolicyEsp}"/></td>
	</tr>
	<tr>
		<td><b><spring:message code="configuration.contact"/></b></td><td style="text-align: center"><jstl:out value="${config.contactEng}"/></td><td style="text-align: center"><jstl:out value="${config.contactEsp}"/></td>
	</tr>
	<tr>
		<td><b><spring:message code="configuration.acceptCookies"/></b></td><td style="text-align: center"><jstl:out value="${config.acceptCookiesEng}"/></td><td style="text-align: center"><jstl:out value="${config.acceptCookiesEsp}"/></td>
	</tr>
</table>

<spring:message code="configuration.nationalities" var="nationalitiesHeader"/>
<h2><jstl:out value="${nationalitiesHeader}:" /></h2>

<table>
	<jstl:forEach var="nationality" items="${config.nationalities}">
		<tr><td>&nbsp; &nbsp;<jstl:out value="- ${nationality}"/></td></tr>
	</jstl:forEach>
</table>

<a href="configuration/admin/edit.do"><spring:message code="configuration.edit"/></a>
















