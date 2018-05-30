<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="announcement/user/finderresult.do" modelAttribute="announcementFinderForm">
<acme:textbox code="announcement.keyword" path="keyword"/>
<acme:textbox code="announcement.minprice" path="minPrice"/>
<acme:textbox code="announcement.maxprice" path="maxPrice"/>
<acme:textbox code="announcement.moment" path="moment" placeholder="dd/MM/yyyy HH:mm"/>
<acme:textbox code="announcement.origin" path="origin"/>
<acme:textbox code="announcement.destination" path="destination"/>
<acme:submit name="send" code="announcement.send"/>
</form:form> <br>
<acme:button url="announcement/user/list.do" code="announcement.list.available"/>