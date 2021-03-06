<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table class="displaytag"  requestURI="actor/admin/listBanned.do" name="actors" sort="external" partialList="true" size="${total}"
	pagesize="5" id="row" keepStatus="true">
	
	<spring:message code="actor.role" var="roleHeader"/>
  	<display:column title="${roleHeader}">
  	<jstl:if test="${row.userAccount.authorities[0].authority == 'MECHANIC'}">
  		<spring:message code="actor.mechanic"/>
  	</jstl:if>
  	<jstl:if test="${row.userAccount.authorities[0].authority == 'ADMIN'}">
  		<spring:message code="actor.admin"/>
  	</jstl:if>
  	<jstl:if test="${row.userAccount.authorities[0].authority == 'USER'}">
  		<spring:message code="actor.user"/>
  	</jstl:if>
  	<jstl:if test="${row.userAccount.authorities[0].authority == 'DRIVER'}">
  		<spring:message code="actor.driver"/>
  	</jstl:if>
  	<jstl:if test="${row.userAccount.authorities[0].authority == 'SPONSOR'}">
  		<spring:message code="actor.sponsor"/>
  	</jstl:if>
  	</display:column>
	   
  	<spring:message code="actor.username" var="usernameHeader"/>
  	<display:column property="userAccount.username" sortable="true" sortName="userAccount.username" title="${usernameHeader}"/>
  	
  	<spring:message code="actor.name" var="nameHeader"/>
  	<display:column property="name" sortable="true" sortName="name" title="${nameHeader}"/>
  	
  	<spring:message code="actor.surname" var="surnameHeader"/>
  	<display:column property="surname" sortable="true" sortName="surname" title="${surnameHeader}"/>
  	
  	<spring:message code="actor.display" var="profileHeader"/>
  	<display:column title="${profileHeader}"> 
    	<a href="actor/display.do?actorId=${row.id}"><spring:message code="actor.seeProfile"/> </a> 
  	</display:column>
	
	<spring:message code="actor.ban" var="banHeader"/>
	<display:column title="${banHeader}">
		<jstl:if test="${row.userAccount.banned}">
			<a href="actor/admin/unban.do?actorId=${row.id}"><spring:message code="actor.unban"/></a>
		</jstl:if>
	</display:column>
</display:table>