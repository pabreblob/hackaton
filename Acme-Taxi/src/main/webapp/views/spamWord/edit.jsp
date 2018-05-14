<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
 
<%@taglib prefix="jstl"  uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%> 
<%@taglib prefix="display" uri="http://displaytag.sf.net"%> 
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %> 

<form:form action="spamWord/save.do" modelAttribute="spamWord">
	<jstl:if test="${spamWord.id == 0}">
		<spring:message code="spamWord.canAddMore"/>
	</jstl:if>
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<acme:textbox code="spamWord.word" path="word"/>
	<acme:submit name="submit" code="spamWord.submit"/>
	<acme:cancel url="spamWord/list.do" code="spamWord.cancel"/>
</form:form>