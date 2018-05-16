<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<h4><spring:message code="report.reporting"/><jstl:out value="${reported}"/></h4>
<form:form action="report/actor/save.do" modelAttribute="report">
	<form:hidden path="reported"/>
	<form:label path="reason">
		<spring:message code="report.reason" /><br>
	</form:label>
	<form:textarea path="reason" rows="5" style="width:80%"/><br>
	<form:errors path="reason" cssClass="error" />
	<br>
	<br>
	<form:label path="imageUrl">
		<spring:message code="report.image" /><br>
	</form:label>	
	<form:input id="url" autocomplete="off" path="imageUrl" style="width:80%" placeholder="https://www.google.com" />	
	<form:errors path="imageUrl" cssClass="error" />
	<br>
	<br>
	<div style="display:none" id="imageBox">
		<hr>
		<b><spring:message code="report.imagePreview"/></b><br>
		<img width="20%" id="image" src="sa"/>
		<br><hr><br>
	</div>
	<acme:submit name="submit" code="report.submit"/>
	<acme:cancel url="welcome/index.do" code="report.cancel"/>
	<br>
</form:form>

<script>
	$(document).ready(function(){
		if(document.getElementById("url").value.trim() != ""){
			imageBox.style.display = "block";
			document.getElementById("image").src = document.getElementById("url").value;
		}
		$("#url").on('change keyup input',function(){
			if(document.getElementById("url").value.trim() == ""){
				imageBox.style.display = "none";
			}else{
				imageBox.style.display = "block";
				document.getElementById("image").src = document.getElementById("url").value;
			}
		})
	})
</script>