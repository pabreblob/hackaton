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
	<form:form action="user/save.do" modelAttribute="userForm" onsubmit="return checkPhone()">

		<acme:textbox code="user.username" path="username" />
		<acme:password code="user.password" path="password" />
		<acme:password code="user.confirmPassword" path="confirmPass" />
		<acme:textbox code="user.name" path="name" />
		<acme:textbox code="user.surname" path="surname" />
		<acme:textbox code="user.birthdate" path="birthdate"
			placeholder="dd/MM/yyyy" />
		<spring:message code="user.placeholderEmail" var="emailplaceholder" />
		<acme:textbox code="user.email" path="email"
			placeholder='${emailplaceholder}' />
		<acme:textbox code="user.phone" path="phone" />

		<acme:textbox code="user.photo" path="photo" placeholder="http://www.photo.com"/>
		<acme:textbox code="user.location" path="location" />
		
		<div class="checkbox">
			<label><form:checkbox path="acceptTerms" />&#160;&#160;<a href="misc/terms.do" target="_blank"><spring:message code="user.acceptTerms" /></a></label>	
			<form:errors path="acceptTerms" cssClass="error" />
		</div>		
		
		<acme:submit name="save" code="user.save" />
		<acme:cancel code="user.cancel" url="welcome/index.do" />
	</form:form>
</security:authorize>

<security:authorize access="hasRole('USER')">
	<form:form action="user/user/save.do" modelAttribute="user" onsubmit="return checkPhone()">
		<form:hidden path="id" />
		<form:hidden path="version" />
		
		<acme:textbox code="user.name" path="name" />
		<acme:textbox code="user.surname" path="surname" />
		<spring:message code="user.placeholderEmail"
			var="emailplaceholder" />
		<acme:textbox code="user.email" path="email"
			placeholder='${emailplaceholder}' />
		<acme:textbox code="user.phone" path="phone" />
		<acme:textbox code="user.birthdate" path="birthdate" />

		<acme:textbox code="user.photo" path="photoUrl" placeholder="http://www.photo.com"/>
		<acme:textbox code="user.location" path="location" />
		
		<acme:submit name="save" code="user.save"  />
	<acme:cancel code="user.cancel" url="user/user/display.do" />
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
			return confirm("<spring:message code='user.checkPhone'/>");
		}
	}
</script>