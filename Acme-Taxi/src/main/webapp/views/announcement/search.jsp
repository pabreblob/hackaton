<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<script>
$(document).ready(function() {
	$("#keyword").on("input", function() {
		$.get("announcement/searchcont.do", {
			keyword: $("#keyword").val()
		}, function(announcements) {
			$("#list > tbody").empty();
			$("#list > tbody").append(announcements);
		});
	});
});
</script>

<label for="keyword">
	<spring:message code="announcement.keyword" />
</label>
<spring:message code="announcement.placeholder.search" var="placeholderSearch" />
<input type="text" name="keyword" id="keyword" placeholder="${placeholderSearch}" /> <br>
<acme:button url="announcement/list.do" code="announcement.list.available"/>
<table id="list">
<thead>
<tr>
<th><spring:message code="announcement.title"/></th>
<th><spring:message code="announcement.origin"/></th>
<th><spring:message code="announcement.destination"/></th>
<th><spring:message code="announcement.price"/></th>
<th><spring:message code="announcement.moment"/></th>
<th><spring:message code="announcement.pets"/></th>
<th><spring:message code="announcement.smoking"/></th>
<th><spring:message code="announcement.cancelled"/></th>
<th></th>
</thead>
<tbody></tbody>
</table>