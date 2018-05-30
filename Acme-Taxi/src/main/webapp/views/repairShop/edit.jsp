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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="repairShop/mechanic/edit.do" modelAttribute="repairShop" onsubmit="return checkPhone()">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<acme:textbox code="repairShop.name" path="name" />
	<acme:textarea code="repairShop.description" path="description" />
	<acme:textbox code="repairShop.location" path="location"/>
	<acme:textbox code="repairShop.phone" path="phone"/>
	<acme:textbox code="repairShop.photo" path="photoUrl"/>
	<acme:submit name="save" code="repairShop.save"  />
	<jstl:if test="${repairShop.id != 0}">
	<input type="submit" name="delete"
				value="<spring:message code="repairShop.delete" />"
				onclick="return confirm('<spring:message code="repairShop.confirm.delete" />')" />
	</jstl:if> 
	<acme:cancel code="repairShop.cancel" url="/repairShop/mechanic/list-created.do" />	
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
			return confirm("<spring:message code='repairShop.checkPhone'/>");
		}
	}
</script>


