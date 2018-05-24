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



<form:form
	action="admin/admin/save.do"
	modelAttribute="admin" onsubmit="return checkPhone()">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<acme:textbox code="admin.name" path="name" /><br/>
	<acme:textbox code="admin.surname" path="surname" /><br/>
	<spring:message code="admin.placeholderEmail" var="emailplaceholder"/>
	<acme:textbox code="admin.email" path="email" placeholder='${emailplaceholder}' /><br/>
	<acme:textbox code="admin.phone" path="phone" /><br/>
	<spring:message code="admin.placeholderDate" var="dateplaceholder"/>
	<acme:textbox code="admin.birthdate" path="birthdate" placeholder='${dateplaceholder}' />
	<input type="submit" name="save"
		value="<spring:message code="admin.save" />"/>
	<acme:cancel code="admin.cancel" url="welcome/index.do" />
</form:form>
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
			return confirm("<spring:message code='admin.checkPhone'/>");
		}
	}
</script>