<%--
 * action-2.jsp
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<p>
	<spring:message code="sponsorship.price" />
	<jstl:out value="${price }" />
	<jstl:out value="${currency }" />
</p>

<form:form action="sponsorship/sponsor/edit.do"
	modelAttribute="sponsorship">
	<form:hidden path="id" />
	<form:hidden path="version" />

	<%-- <form:hidden path="sponsor" />
	<form:hidden path="moment" />
	<form:hidden path="accepted" />
	<form:hidden path="cancelled" />
	<form:hidden path="price" /> --%>


	<acme:textbox code="sponsorship.holdername"
		path="creditCard.holderName" />
	<acme:textbox code="sponsorship.brandname" path="creditCard.brandName" />
	<acme:textbox code="sponsorship.number" path="creditCard.number" />
	<acme:textbox code="sponsorship.expmonth" path="creditCard.expMonth"
		placeholder="mm" />
	<acme:textbox code="sponsorship.expyear" path="creditCard.expYear"
		placeholder="yy" />
	<acme:textbox code="sponsorship.cvv" path="creditCard.cvv" />

	<acme:textbox code="sponsorship.pictureURL" path="pictureUrl"
		placeholder="http://www.acme.com" />

	<div style="display:none" id="imageBox">
		<hr>
		<b><spring:message code="sponsorship.imagePreview"/></b><br>
		<img width="20%" id="image" src="sa"/>
		<br><hr><br>
	</div>
	<acme:submit name="save" code="sponsorship.save" />
	<acme:cancel code="sponsorship.cancel"
		url="/sponsorship/sponsor/list.do" />


	<script>
		$(document).ready(function() {
			if (document.getElementById("pictureUrl").value.trim() != "") {
				imageBox.style.display = "block";
				document.getElementById("image").src = document.getElementById("pictureUrl").value;
			}
			$("#pictureUrl").on('change keyup input', function() {
				if (document.getElementById("pictureUrl").value.trim() == "") {
					imageBox.style.display = "none";
				} else {
					imageBox.style.display = "block";
					document.getElementById("image").src = document.getElementById("pictureUrl").value;
				}
			})
		})
	</script>
</form:form>


