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
	<form:form action="driver/save.do" modelAttribute="driverForm" onsubmit="checkPhone()">

		<acme:textbox code="driver.username" path="username" />
		<acme:password code="driver.password" path="password" />
		<acme:password code="driver.confirmPassword" path="confirmPass" />
		<acme:textbox code="driver.name" path="name" />
		<acme:textbox code="driver.surname" path="surname" />
		<acme:textbox code="driver.birthdate" path="birthdate"
			placeholder="dd/MM/yyyy" />
		<spring:message code="driver.placeholderEmail" var="emailplaceholder" />
		<acme:textbox code="driver.email" path="email"
			placeholder='${emailplaceholder}' />
		<acme:textbox code="driver.phone" path="phone" />
		<acme:textbox code="driver.idNumber" path="idNumber" />
		<%-- <acme:textbox code="sponsor.nacionality" path="nacionality" /> --%>
		<%-- <acme:select items="${nacionalities }" itemLabel="nationality" code="sponsor.nacionality" path="nacionality"/> --%>
		<form:label path="nationality">
			<spring:message code="driver.nacionality" />
		</form:label>
		<form:select path="nationality">
			<form:options items="${nationalities}" />
		</form:select>
		<acme:textbox code="driver.photo" path="photo" placeholder="http://www.photo.com"/>
		<acme:textbox code="driver.location" path="location" />
		<br />
		<a href="misc/terms.do" target="_blank"><spring:message
				code="driver.acceptTerms" /></a>
		<acme:checkbox code="driver.blank" path="acceptTerms" />
		<acme:submit name="save" code="driver.save" />
		<acme:cancel code="driver.cancel" url="welcome/index.do" />
	</form:form>
</security:authorize>

<security:authorize access="hasRole('DRIVER')">
	<form:form action="driver/driver/save.do" modelAttribute="driver" onsubmit="checkPhone()">
		<form:hidden path="id" />
		<form:hidden path="version" />
		
		<acme:textbox code="driver.name" path="name" />
		<acme:textbox code="driver.surname" path="surname" />
		<spring:message code="driver.placeholderEmail"
			var="emailplaceholder" />
		<acme:textbox code="driver.email" path="email"
			placeholder='${emailplaceholder}' />
		<acme:textbox code="driver.phone" path="phone" />
		<acme:textbox code="driver.birthdate" path="birthdate" />
		
			<form:label path="nationality">
				<spring:message code="driver.nacionality" />
			</form:label>
			<form:select path="nationality">
				<form:options items="${nationalities}" />
			</form:select>
		
		<acme:textbox code="driver.idNumber" path="idNumber" />
		<acme:textbox code="driver.photo" path="photoUrl" placeholder="http://www.photo.com"/>
		<acme:textbox code="driver.location" path="location" />
		
		<acme:submit name="save" code="driver.save"  />
	<acme:cancel code="driver.cancel" url="driver/driver/display.do" />
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
			return confirm("<spring:message code='driver.checkPhone'/>");
		}
	}
</script>