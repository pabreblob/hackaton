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

<p>
	<spring:message code="user.username" />:
	<jstl:out value="${user.userAccount.username}" />
</p>
<p>
	<spring:message code="user.name" />:
	<jstl:out value="${user.name}" />
</p>
<p>
	<spring:message code="user.surname" />:
	<jstl:out value="${user.surname}" />
</p>
<spring:message code="user.dateFormat" var="dateFormat" />
<p>
	<spring:message code="user.birthdate" />:
	<fmt:formatDate value="${user.birthdate}" pattern="${dateFormat}" />
</p>

<jstl:if test='${not empty user.phone}'>
	<p>
		<spring:message code="user.phone" />:
		<jstl:out value="${user.phone}" />
	</p>
</jstl:if>

<jstl:if test='${not empty user.photoUrl}'>
	<spring:message code="user.photo" var="userPhoto" />
	<p>
		<img src="${user.photoUrl}" alt="${userPhoto}" />
	</p>
</jstl:if>
<p>
	<spring:message code="user.email" />:
	<jstl:out value="${user.email}" />
</p>

<p>
	<spring:message code="user.location" />:
	<jstl:out value="${user.location}" />
</p>

<p>
	<spring:message code="user.rating" />:
	<jstl:out value="${user.meanRating}" />
	
	<jstl:if test="${driver.meanRating == 0}">
	 <span class="valoracion val-0"></span>
	</jstl:if>
	
	<jstl:if test="${driver.meanRating > 0}">
	<jstl:if test="${driver.meanRating <= 0.5}">
	 <span class="valoracion val-5"></span>
	</jstl:if>
	</jstl:if>
	
	<jstl:if test="${driver.meanRating > 0.5}">
	<jstl:if test="${driver.meanRating <= 1}">
	 <span class="valoracion val-10"></span>
	</jstl:if>
	</jstl:if>
	
	<jstl:if test="${driver.meanRating > 1}">
	<jstl:if test="${driver.meanRating <= 1.5}">
	 <span class="valoracion val-15"></span>
	</jstl:if>
	</jstl:if>
	
	<jstl:if test="${driver.meanRating > 1.5}">
	<jstl:if test="${driver.meanRating <= 2}">
	 <span class="valoracion val-20"></span>
	</jstl:if>
	</jstl:if>
	
	<jstl:if test="${driver.meanRating > 2}">
	<jstl:if test="${driver.meanRating <= 2.5}">
	 <span class="valoracion val-25"></span>
	</jstl:if>
	</jstl:if>
	
	<jstl:if test="${driver.meanRating > 2.5}">
	<jstl:if test="${driver.meanRating <= 3}">
	 <span class="valoracion val-30"></span>
	</jstl:if>
	</jstl:if>
	
	<jstl:if test="${driver.meanRating > 3}">
	<jstl:if test="${driver.meanRating <= 3.5}">
	 <span class="valoracion val-35"></span>
	</jstl:if>
	</jstl:if>
	
	<jstl:if test="${driver.meanRating > 3.5}">
	<jstl:if test="${driver.meanRating <= 4}">
	 <span class="valoracion val-40"></span>
	</jstl:if>
	</jstl:if>
	
	<jstl:if test="${driver.meanRating > 4}">
	<jstl:if test="${driver.meanRating <= 4.5}">
	 <span class="valoracion val-45"></span>
	</jstl:if>
	</jstl:if>
	
	<jstl:if test="${driver.meanRating > 4.5}">
	 <span class="valoracion val-50"></span>
	</jstl:if>
	
</p>

<br/>
<spring:message code="user.announcements" var="annsHeader"/>
<h2><jstl:out value="${annsHeader}"/></h2>

<display:table name="announcements" id="row" requestURI="${requestURI}" class="displaytag" pagesize="10">

	<spring:message code="announcement.title" var="titleHeader"/> 
	<display:column property="title" sortable="true" sortName="title" title="${titleHeader}"/>
	
	<security:authorize access="hasRole('USER')">
		<jstl:if test="${me == true}">	
			<jstl:if test="${user.id == row.creator.id}">
				<display:column>
					<a href="announcement/user/display-created.do?announcementId=${row.id}"><spring:message code="announcement.display"/></a>
				</display:column>
			</jstl:if>
		
			<jstl:if test="${user.id != row.creator.id}">
				<display:column>
					<a href="announcement/user/display.do?announcementId=${row.id}"><spring:message code="announcement.display"/></a>
				</display:column>
			</jstl:if>
		</jstl:if>
		
		<jstl:if test="${me == false}">
			<display:column>
				<a href="announcement/user/display.do?announcementId=${row.id}"><spring:message code="announcement.display"/></a>
			</display:column>
		</jstl:if>
	</security:authorize>
	
	<security:authorize access="!hasRole('USER')">
		<display:column>
			<a href="announcement/display.do?announcementId=${row.id}"><spring:message code="announcement.display"/></a>
		</display:column>
	</security:authorize>

</display:table>
<br/>
<br/>

<jstl:if test='${blockeable}'>
<a href="actor/actor/block.do?actorId=${user.id}"> <spring:message code="user.block" />
	</a>
</jstl:if>

<jstl:if test='${unblockeable}'>
<a href="actor/actor/unblock.do?actorId=${user.id}"> <spring:message code="user.unblock" />
	</a>
</jstl:if>

<security:authorize access="isAuthenticated()">
<p>
<jstl:if test='${me == false}'>
<a href="report/actor/create.do?actorId=${user.id}"> <spring:message code="user.report" /></a>
</jstl:if>
</p>
</security:authorize>

<jstl:if test="${!empty user.reviews}"> 
<a href="review/list-user.do?userId=${user.id }">
	<spring:message code="user.reviews" />
</a>
</jstl:if> 
<br/>

<security:authorize access="hasRole('ADMIN')">
<jstl:if test='${!banned}'>
<a href="actor/admin/ban.do?actorId=${user.id}"> <spring:message code="user.ban" /></a>
</jstl:if>

<jstl:if test='${banned}'>
<a href="actor/admin/unban.do?actorId=${user.id}"> <spring:message code="user.unban" /></a>
</jstl:if>
</security:authorize>

<jstl:if test="${requestURI == 'user/user/display.do'}">
<br />
	<a href="user/user/edit.do"> <spring:message code="user.edit" />
	</a>
</jstl:if>

