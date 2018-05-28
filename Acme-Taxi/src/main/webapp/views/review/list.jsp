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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<display:table pagesize="5" class="displaytag" 
	name="reviews" requestURI="${requestURI}" id="row" sort="external" partialList="true" size="${total }">
	
	<jstl:if test="${requestURI == 'review/user/list-created.do' }">
	<display:column>
		<a href="review/user/edit.do?reviewId=${row.id}"> <spring:message
				code="review.edit" />
		</a>
	</display:column>
	
	<display:column>
		<a href="review/user/delete.do?reviewId=${row.id}" onclick="return confirm('<spring:message code='review.confirm.delete' />')"> <spring:message
				code="review.delete" />
		</a>
	</display:column>
	</jstl:if>
	
	<security:authorize access="hasRole('ADMIN')">
	<display:column>
		<a href="review/admin/delete.do?reviewId=${row.id}"> <spring:message
				code="review.delete" />
		</a>
	</display:column>
	</security:authorize>
	
	<spring:message code="review.moment" var="momentHeader" />
	<spring:message code="review.dateFormat2" var="dateFormatHeader" />
	<display:column title="${momentHeader}">
		<fmt:formatDate value="${row.moment}"
			pattern="${dateFormatHeader}" />
	</display:column>
	<spring:message code="review.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />
	<spring:message code="review.rating" var="ratingHeader" />
	<display:column property="rating" title="${ratingHeader}" />
	
	<jstl:if test="${requestURI == 'review/user/list-created.do' }">
	<display:column>
		<a href="review/user/display.do?reviewId=${row.id}"> <spring:message
				code="review.display" />
		</a>
	</display:column>
	</jstl:if>
	
	<jstl:if test="${requestURI != 'review/user/list-created.do' }">
	<display:column>
		<a href="review/display.do?reviewId=${row.id}"> <spring:message
				code="review.display" />
		</a>
	</display:column>
	</jstl:if>
	
</display:table>

<jstl:if test="${requestURI == 'review/user/list-created.do' }">
		<a href="review/user/make.do"> <spring:message
				code="review.review" />
		</a>
	</jstl:if>