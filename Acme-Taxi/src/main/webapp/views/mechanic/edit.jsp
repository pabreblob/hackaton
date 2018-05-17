<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<security:authorize access="isAnonymous()">
<form:form
	action="mechanic/save.do"
	modelAttribute="mechanicForm">
	
	<acme:textbox code="mechanic.username" path="userAccount.username" /><br/>
	<acme:password code="mechanic.password" path="userAccount.password" /><br/>
	<acme:password code="mechanic.confirmPassword" path="confirmPass" /><br/>
	<acme:textbox code="mechanic.name" path="name" /><br/>
	<acme:textbox code="mechanic.surname" path="surname" /><br/>
	<spring:message code="mechanic.placeholderEmail" var="emailplaceholder"/>
	<acme:textbox code="mechanic.email" path="email" placeholder='${emailplaceholder}' /><br/>
	<acme:textbox code="mechanic.phone" path="phone" /><br/>
	<spring:message code="mechanic.placeholderDate" var="dateplaceholder"/>
	<acme:textbox code="mechanic.birthdate" path="birthdate" placeholder='${dateplaceholder}' />
	<p>
	<form:label path="nationality"><spring:message code="mechanic.nationality"/></form:label>
<form:select path="nationality" >
<form:options items="${nationalities}"  />
</form:select>
</p>
	<acme:textbox code="mechanic.idNumber" path="idNumber" /><br/>
	<acme:textbox code="mechanic.photo" path="photoUrl" /><br/>
	<a href="misc/terms.do" target="_blank"><spring:message code="mechanic.acceptTerms"/></a><acme:checkbox code="mechanic.blank" path="acceptTerms"/><br/>
	<acme:submit name="save" code="mechanic.save"  />
	<acme:cancel code="mechanic.cancel" url="welcome/index.do" />
</form:form>
</security:authorize>

<security:authorize access="hasRole('MECHANIC')">
<form:form
	action="mechanic/mechanic/save.do"
	modelAttribute="mechanic">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<acme:textbox code="mechanic.name" path="name" /><br/>
	<acme:textbox code="mechanic.surname" path="surname" /><br/>
	<spring:message code="mechanic.placeholderEmail" var="emailplaceholder"/>
	<acme:textbox code="mechanic.email" path="email" placeholder='${emailplaceholder}' /><br/>
	<acme:textbox code="mechanic.phone" path="phone" /><br/>
	<spring:message code="mechanic.placeholderDate" var="dateplaceholder"/>
	<acme:textbox code="mechanic.birthdate" path="birthdate" placeholder='${dateplaceholder}' />
	<p>
	<form:label path="nationality"><spring:message code="mechanic.nationality"/></form:label>
<form:select path="nationality" >
<form:options items="${nationalities}" />
</form:select>
	</p>
	<acme:textbox code="mechanic.idNumber" path="idNumber" /><br/>
	<acme:textbox code="mechanic.photo" path="photoUrl" /><br/>
	<acme:submit name="save" code="mechanic.save"  />
	<acme:cancel code="mechanic.cancel" url="welcome/index.do" />
</form:form>
</security:authorize>
<script type="text/javascript">
	function checkPhone(){
		var phone = $("input#phone").val();
		//alert(phone);
		//var pat = new RegExp("[1-9]");
		var pat = /^(\+[1-9][0-9]{0,2}\s(\([1-9][0-9]{0,2}\)\s){0,1}){0,1}[0-9]{4,}$/; 
		//var pat = new RegExp("^(\+[1-9][0-9]{0,2}\s(\([1-9][0-9]{0,2}\)\s){0,1}){0,1}[0-9]{4,}$");
		//alert("P");
		//if(pat.test(phone)){
		if(phone.search(pat) != -1){
			return true;
		} else {
			return confirm("<spring:message code='mechanic.checkPhone'/>");
		}
	}
</script>