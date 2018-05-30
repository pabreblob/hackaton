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
	<acme:textarea code="report.reason" path="reason"/>
	<div class="form-group">
	<form:label path="imageUrl">
		<spring:message code="report.image" /><br>
	</form:label>	
	<form:input class="form-control" id="url" autocomplete="off" path="imageUrl" placeholder="https://www.google.com" />	
	<form:errors path="imageUrl" cssClass="error" />
	</div>
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