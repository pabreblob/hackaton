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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="isAnonymous()">
	<form:form action="sponsor/save.do" modelAttribute="sponsorForm" onsubmit="return checkPhone()">

		<acme:textbox code="sponsor.username" path="username" />
		<acme:password code="sponsor.password" path="password" />
		<acme:password code="sponsor.confirmPassword" path="confirmPass" />
		<acme:textbox code="sponsor.name" path="name" />
		<acme:textbox code="sponsor.surname" path="surname" />
		<acme:textbox code="sponsor.birthdate" path="birthdate"
			placeholder="dd/MM/yyyy" />
		<spring:message code="sponsor.placeholderEmail" var="emailplaceholder" />
		<acme:textbox code="sponsor.email" path="email"
			placeholder='${emailplaceholder}' />
		<acme:textbox code="sponsor.phone" path="phone" />
		<acme:textbox code="sponsor.idNumber" path="idNumber" />
		<%-- <acme:textbox code="sponsor.nacionality" path="nacionality" /> --%>
		<%-- <acme:select items="${nacionalities }" itemLabel="nationality" code="sponsor.nacionality" path="nacionality"/> --%>
		<div class="form-group">
			<form:label path="nationality">
				<spring:message code="sponsor.nacionality" />
			</form:label>
			<form:select class="form-control" path="nationality">
				<form:options items="${nationalities}" />
			</form:select>
		</div>
		
		<div class="checkbox">
			<label><form:checkbox path="acceptTerms" />&#160;&#160;<a href="misc/terms.do" target="_blank"><spring:message code="mechanic.acceptTerms" /></a></label>	
			<form:errors path="acceptTerms" cssClass="error" />
		</div>
		<acme:submit name="save" code="sponsor.save" />
		<acme:cancel code="sponsor.cancel" url="welcome/index.do" />
	</form:form>
</security:authorize>

<security:authorize access="hasRole('SPONSOR')">
	<form:form action="sponsor/sponsor/save.do" modelAttribute="sponsor" onsubmit="return checkPhone()">
		<form:hidden path="id" />
		<form:hidden path="version" />
		
		<acme:textbox code="sponsor.name" path="name" />
		<acme:textbox code="sponsor.surname" path="surname" />
		<spring:message code="sponsor.placeholderEmail"
			var="emailplaceholder" />
		<acme:textbox code="sponsor.email" path="email"
			placeholder='${emailplaceholder}' />
		<acme:textbox code="sponsor.phone" path="phone" />
		<acme:textbox code="sponsor.birthdate" path="birthdate" />
		<div class="form-group">
			<form:label path="nationality">
				<spring:message code="sponsor.nacionality" />
			</form:label>
			<form:select class="form-control" path="nationality">
				<form:options items="${nationalities}" />
			</form:select>
		</div>
		<acme:textbox code="sponsor.idNumber" path="idNumber" />
		<acme:submit name="save" code="sponsor.save"  />
	<acme:cancel code="sponsor.cancel" url="welcome/index.do" />
	</form:form>
</security:authorize>

<script type="text/javascript">
	function checkPhone(){
		var phone = $("input#phone").val();
		//var pat = new RegExp("[1-9]");
		var pat = /^(\+[1-9][0-9]{0,2}\s(\([1-9][0-9]{0,2}\)\s){0,1}){0,1}[0-9]{4,}$/; 
		//var pat = new RegExp("^(\+[1-9][0-9]{0,2}\s(\([1-9][0-9]{0,2}\)\s){0,1}){0,1}[0-9]{4,}$");
		//alert("P");
		//if(pat.test(phone)){
		if(phone.search(pat) != -1){
			return true;
		} else {
			return confirm("<spring:message code='sponsor.checkPhone'/>");
		}
	}
</script>