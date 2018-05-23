<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form action="announcement/user/finderresult.do">
<label for="keyword">
	<spring:message code="announcement.keyword" />
</label>
<input type="text" name="keyword" id="keyword" /><br>
<label for="minPrice">
	<spring:message code="announcement.minprice" />
</label>
<input type="text" name="minPrice" id="minPrice" /><br>
<label for="maxPrice">
	<spring:message code="announcement.maxprice" />
</label>
<input type="text" name="maxPrice" id="maxPrice" /><br>
<spring:message code="moment.date.format" var="placeholdermoment"/>
<label for="moment">
	<spring:message code="announcement.moment" />
</label>
<input type="text" name="moment" id="moment" placeholder="${placeholdermoment}"/><br>
<label for="origin">
	<spring:message code="announcement.origin" />
</label>
<input type="text" name="origin" id="origin" /><br>
<label for="destination">
	<spring:message code="announcement.destination" />
</label>
<input type="text" name="destination" id="destination" /><br>
<spring:message code="announcement.send" var="submitbutton"/>
<input type="submit" value="${submitbutton}"/>
</form>
<jstl:if test="${errormessage != null}">
<span class="message"><jstl:out value="${errormessage}"/></span>
</jstl:if>