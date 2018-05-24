<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="announcement.title" />: <jstl:out value="${announcement.title}"/></p>
<jstl:if test="${announcement.description != ''}">
<p><spring:message code="announcement.description" />: <jstl:out value="${announcement.description}"/></p>
</jstl:if>
<p><spring:message code="announcement.creator" />: <a href="user/display.do?userId=${announcement.creator.id}"><jstl:out value="${announcement.creator.userAccount.username}"/></a></p>
<p><spring:message code="announcement.origin" />: <jstl:out value="${announcement.origin}"/></p>
<p><spring:message code="announcement.destination" />: <jstl:out value="${announcement.destination}"/></p>
<p><spring:message code="announcement.price" />: <jstl:out value="${announcement.pricePerPerson}"/> <jstl:out value="${currency}"/></p>
<spring:message code="moment.date.format" var="dateFormat" />
<p><spring:message code="announcement.moment" />: <fmt:formatDate value="${announcement.moment}" pattern="${dateFormat}"/></p>
<p><spring:message code="announcement.pets" />: <jstl:if test="${!announcement.petsAllowed}"><spring:message code="announcement.no"/></jstl:if><jstl:if test="${announcement.petsAllowed}"><spring:message code="announcement.yes"/></jstl:if></p>
<p><spring:message code="announcement.smoking" />: <jstl:if test="${!announcement.smokingAllowed}"><spring:message code="announcement.no"/></jstl:if><jstl:if test="${announcement.smokingAllowed}"><spring:message code="announcement.yes"/></jstl:if></p>
<p><spring:message code="announcement.cancelled" />: <jstl:if test="${!announcement.cancelled}"><spring:message code="announcement.no"/></jstl:if><jstl:if test="${announcement.cancelled}"><spring:message code="announcement.yes"/></jstl:if></p>
<p><spring:message code="announcement.seats" />: <jstl:out value="${announcement.seats}"/></p>
<p><spring:message code="announcement.remaining" />: <jstl:out value="${remaining}"/></p>
<jstl:if test="${requestURI == 'announcement/admin/display.do'}">
<p><spring:message code="announcement.marked" />: <jstl:if test="${!announcement.marked}"><spring:message code="announcement.no"/></jstl:if><jstl:if test="${announcement.marked}"><spring:message code="announcement.yes"/></jstl:if></p>
</jstl:if>
<p><spring:message code="announcement.attendants"/></p>
<display:table name="attendants" id="row" requestURI="${requestURI}" class="displaytag">
<spring:message code="announcement.username" var="userHeader"/> 
<display:column property="userAccount.username" title="${userHeader}"/>
<spring:message code="announcement.name" var="nameHeader"/> 
<display:column property="name" title="${nameHeader}"/>
<spring:message code="announcement.surname" var="surnameHeader"/> 
<display:column property="surname" title="${surnameHeader}"/>
<spring:message code="announcement.email" var="emailHeader"/> 
<display:column property="email" title="${emailHeader}"/>
</display:table><br/>

<br/>
<h2>
<spring:message code="announcement.comments" var="commentHeader"/>
<jstl:out value="${commentHeader}"></jstl:out>
</h2>

<jstl:if test="${hasComment == true}">
	<jstl:forEach var="comment" items="${comments}">
		
		<div class="COMMENT">
		
			<div class="COMMENT">
				<a href="user/display.do?userId=${comment.creator.id}"><jstl:out value="${comment.creator.userAccount.username}"/></a>			
					<span class="MOMENT">
						(<fmt:formatDate value="${comment.moment}" pattern="${dateFormat}" />)
					</span>:
			</div>
			<div class="COMMENT">
				&emsp;<jstl:out value="${comment.body}"/>
			</div>

			<div>			
				<security:authorize access="hasRole('ADMIN')">
					<a href="comment/admin/delete.do?commentId=${comment.id}"><spring:message code="comment.delete"/></a>
				</security:authorize>
			</div>
		</div>	
		<jstl:forEach var="reply" items="${comment.replies}">
		<div class="REPLY">
			
				<div>
					&emsp;&emsp;<a href="user/display.do?userId=${reply.creator.id}"><jstl:out value="${reply.creator.userAccount.username}"/></a>
							<span class="MOMENT">
								(<fmt:formatDate value="${reply.moment}" pattern="${dateFormat}" />)
							</span>:	
				</div>
				<div>
					&emsp;&emsp;&emsp;<jstl:out value="${reply.body}"/>
				</div>
				<security:authorize access="hasRole('ADMIN')">
					&emsp;&emsp;<a href="comment/admin/delete.do?commentId=${reply.id}"><spring:message code="comment.reply.delete"/></a>
				</security:authorize>
				
		</div>
		
		</jstl:forEach>
		
		<security:authorize access="hasRole('USER')">
			<jstl:if test="${isCreator == true}">
				<div>
					<a href="comment/user/create-reply.do?commentId=${comment.id}&c=t"><spring:message code="comment.reply.new"/></a>
				</div>
			</jstl:if>
			<jstl:if test="${isCreator == false}">
				<div>
					<a href="comment/user/create-reply.do?commentId=${comment.id}&c=f"><spring:message code="comment.reply.new"/></a>
				</div>
			</jstl:if>
		</security:authorize>
		
		<br/>
		
	</jstl:forEach>
</jstl:if>

<jstl:if test="${hasComment == false}">
		<p><i><spring:message code="comment.empty"/></i></p>
</jstl:if>

<jstl:if test="${isCreator == true}">
	<security:authorize access="hasRole('USER')">
			<a href="comment/user/create-comment.do?announcementId=${announcement.id}&c=t"><spring:message code="comment.new"/></a><br>
	</security:authorize>
</jstl:if>

<jstl:if test="${isCreator == false}">
	<security:authorize access="hasRole('USER')">
			<a href="comment/user/create-comment.do?announcementId=${announcement.id}&c=f"><spring:message code="comment.new"/></a><br>
	</security:authorize>
</jstl:if>	

<jstl:if test="${requestURI == 'announcement/user/display.do'}">
<jstl:if test="${joined}"><a href="announcement/user/dropout.do?announcementId=${announcement.id}"><spring:message code="announcement.dropout"/></a></jstl:if>
<jstl:if test="${joinable}"><a href="announcement/user/join.do?announcementId=${announcement.id}"><spring:message code="announcement.join"/></a></jstl:if>
</jstl:if>
<jstl:if test="${requestURI == 'announcement/admin/display.do'}">
<a href="announcement/admin/delete.do?announcementId=${announcement.id}" onclick="return confirm('<spring:message code="announcement.confirm.delete"/>')"><spring:message code="announcement.delete"/></a>
</jstl:if>