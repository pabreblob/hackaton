<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<script>
$(document).ready(function() {
	$("#username").on("input", function() {
		$.get("actor/ajaxsearchcont.do", {
			username: $("#username").val()
		}, function(actors) {
			$("#list > tbody").empty();
			$("#list > tbody").append(actors);
		});
	});
});
</script>

<label for="username">
	<spring:message code="actor.username" />
</label>
<spring:message code="actor.placeholderSearchUsername" var="placeholder" />
<input type="text" name="username" id="username" size="30" autocomplete="off" placeholder="${placeholder}" />

<table id="list">
<thead>
<tr>
<th><spring:message code="actor.role"/></th>
<th><spring:message code="actor.username"/></th>
<th><spring:message code="actor.name"/></th>
<th><spring:message code="actor.surname"/></th>
<th><spring:message code="actor.display"/></th>
</thead>
<tbody></tbody>
</table>