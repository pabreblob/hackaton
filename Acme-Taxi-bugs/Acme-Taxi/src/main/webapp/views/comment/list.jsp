<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table class="displaytag" name="marked" id="row" pagesize="5" requestURI="${requestURI}" sort="external" partialList="true" size="${total}">
	
	<spring:message code="comment.creator" var="creatorHeader"/>
	<display:column sortable="true" sortName="creator" title="${creatorHeader}">
		<a href="user/display.do?userId=${row.creator.id}"><jstl:out value="${row.creator.userAccount.username}"/></a>
	</display:column>
	
	<spring:message code="comment.body" var="bodyHeader"/>
	<display:column property="body" title="${bodyHeader}"/>
	
	<spring:message code="comment.moment" var="momentHeader"/>
	<spring:message code="moment.date.format" var="dateFormat"/>
	<display:column sortable="true" title="momentHeader" sortName="moment">
		<fmt:formatDate value="${row.moment}" pattern="${dateFormat}"/>
	</display:column>
	
	<spring:message code="comment.announcement" var="annHeader"/>
	<display:column sortable="true" title="annHeader" sortName="announcement">
		<a href="announcement/admin/display.do?announcementId=${row.announcement.id}"><jstl:out value="${row.announcement.title}"/></a>
	</display:column>
		
</display:table>