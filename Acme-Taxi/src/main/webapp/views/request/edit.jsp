<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
 
<%@taglib prefix="jstl"  uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%> 
<%@taglib prefix="display" uri="http://displaytag.sf.net"%> 
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %> 

<form:form action="${requestUri}" modelAttribute="request">
	<form:hidden path="id"/>
	<spring:message code="request.giveInformation"/><br><br>

	<spring:message code="request.placeholderPlace" var="placeholderHeader"/>
	<acme:textbox code="request.origin" path="origin" placeholder="${placeholderHeader}" />
	<acme:textbox code="request.destination" path="destination" placeholder="${placeholderHeader}" />

	<acme:textbox code="request.passengerNumber" path="passengersNumber" />

	<spring:message code="request.momentValid" var="placeholderMoment"/>
	<acme:textbox code="request.moment" path="moment" placeholder="${placeholderMoment}"/>

	<p id="anyComment"><spring:message code="request.anyComment"/><br></p>
	<div style="display:none" id="commentBox">
		<div class="form-group">
		<form:label path="comment">
			<spring:message code="request.comment" />: 
		</form:label>	
		<form:textarea class="form-control" id="commentInput" path="comment"/>	
		<form:errors path="comment" cssClass="error" />
		</div>
	</div>
	<acme:submit name="submit" code="request.submit"/>
	<acme:cancel url="/welcome/index.do" code="request.cancel"/>
</form:form>
<style>
	#anyComment{
		color: blue;
		text-decoration: underline;
		display: inline;
	}
	#anyComment:hover{
		cursor: pointer;
	}
</style>
<script>
	$(document).ready(function(){
	if(document.getElementById("commentInput").value.trim() != ""){
		document.getElementById("anyComment").style.display = "none";
		document.getElementById("commentBox").style.display = "block";
	}else{
		$("#anyComment").click(function(){
			$("#anyComment").css("display", "none");
			$("#commentBox").css("display", "block");
		})
	}
	})
</script>