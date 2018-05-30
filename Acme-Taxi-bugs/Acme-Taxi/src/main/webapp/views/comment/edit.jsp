<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:if test="${c == true}">
	<jstl:if test="${reply == false}">
		<form:form action="comment/user/save-comment.do?announcementId=${announcementId}&c=t" modelAttribute="comment">
		
			<form:hidden path="id"/>
			<form:hidden path="version"/>
			
			<acme:textarea code="comment.body" path="body"/>
			
			<acme:submit name="save" code="comment.save" />
			<input type="button" name="cancel" value="<spring:message code="comment.cancel" /> " 
			onclick="window.history.go(-1); return false;" />
		<br/>
	
		</form:form>
	</jstl:if>
	
	<jstl:if test="${reply == true}">
		<form:form action="comment/user/save-reply.do?commentId=${commentId}&c=t" modelAttribute="comment">
		
			<form:hidden path="id"/>
			<form:hidden path="version"/>
			
			<acme:textarea code="comment.body" path="body"/>
			
			<acme:submit name="save" code="comment.save" />
			<input type="button" name="cancel" value="<spring:message code="comment.cancel" /> " 
			onclick="window.history.go(-1); return false;" />
	
		</form:form>
	</jstl:if>
</jstl:if>

<jstl:if test="${c == false}">
	<jstl:if test="${reply == false}">
		<form:form action="comment/user/save-comment.do?announcementId=${announcementId}&c=f" modelAttribute="comment">
		
			<form:hidden path="id"/>
			<form:hidden path="version"/>
			
			<acme:textarea code="comment.body" path="body"/>
			
			<acme:submit name="save" code="comment.save" />
			<input type="button" name="cancel" value="<spring:message code="comment.cancel" /> " 
			onclick="window.history.go(-1); return false;" />
		<br/>
	
		</form:form>
	</jstl:if>
	
	<jstl:if test="${reply == true}">
		<form:form action="comment/user/save-reply.do?commentId=${commentId}&c=f" modelAttribute="comment">
		
			<form:hidden path="id"/>
			<form:hidden path="version"/>
			
			<acme:textarea code="comment.body" path="body"/>
			
			<acme:submit name="save" code="comment.save" />
			<input type="button" name="cancel" value="<spring:message code="comment.cancel" /> " 
			onclick="window.history.go(-1); return false;" />
	
		</form:form>
	</jstl:if>
</jstl:if>