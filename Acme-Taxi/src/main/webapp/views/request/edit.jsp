<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
 
<%@taglib prefix="jstl"  uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%> 
<%@taglib prefix="display" uri="http://displaytag.sf.net"%> 
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %> 

<form:form action="request/user/save.do" modelAttribute="request">
	<spring:message code="request.giveInformation"/><br><br>
	<form:label path="origin">
		<spring:message code="request.origin" />: 
	</form:label>	
	<spring:message code="request.placeholderPlace" var="placeholderHeader"/>
	<form:input path="origin" size="50" placeholder="${placeholderHeader}" />	
	<form:errors path="origin" cssClass="error" />
	<br>
	<br>
	<form:label path="destination">
		<spring:message code="request.destination" />: 
	</form:label>	
	<form:input path="destination" size="50" placeholder="${placeholderHeader}" />	
	<form:errors path="destination" cssClass="error" />
	<br>
	<br>
	<form:label path="passengersNumber">
		<spring:message code="request.passengerNumber" />: 
	</form:label>	
	<form:input type="number" path="passengersNumber" min="1"/>	
	<form:errors path="destination" cssClass="error" />
	<br>
	<br>
	<form:label path="moment">
		<spring:message code="request.moment" />: 
	</form:label>	
	<spring:message code="request.momentPlaceholder" var="placeholderMoment"/>
	<form:input path="moment" placeholder="${placeholderMoment}"/>	
	<form:errors path="moment" cssClass="error" />
	<br>
	<br>
	<p id="anyComment"><spring:message code="request.anyComment"/><br><br></p>
	<div style="display:none" id="commentBox">
		<form:label path="comment">
			<spring:message code="request.comment" />:<br> 
		</form:label>	
		<form:textarea id="commentInput" path="comment" rows="5" style="width:50%"/><br>	
		<form:errors path="comment" cssClass="error" /><br><br>		
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
		document.getElementById("commentBox").style.display = "block";
	}else{
		$("#anyComment").click(function(){
			$("#anyComment").css("display", "none");
			$("#commentBox").css("display", "block");
		})
	}
	})
</script>