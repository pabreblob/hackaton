<%--
 * footer.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:useBean id="date" class="java.util.Date" />

<hr />
<style>
	.footerB:hover{
		background-color: rgba(0,0,255,0.2);
		transition: 0.3s;
	}
</style>

<div class="col-12 col-xs-12 col-lg-3">
<b><jstl:out value="${footer}"/></b>
</div>

<div class="footerB col-12 col-xs-12 col-lg-3">
	<a href="misc/contact.do">
	<spring:message code="master.page.contact"/>
	</a>
</div>
<div class="footerB col-12 col-xs-12 col-lg-3">
	<a href="misc/cookies.do">
	<spring:message code="master.page.cookies"/>
	</a>
</div>
<div class="footerB col-12 col-xs-12 col-lg-3">
<a href="misc/terms.do">
	<spring:message code="master.page.terms"/>
	</a>
</div>

<br />
<div id="pruebacookie"></div>

<script>
$(document).ready(function() {
	if (getCookie("accepted") !== ("true")) {
		var language = getCookie("language");
		var cookies = '<jstl:out value="${acceptCookies}" />';
		if (language == "" || language === "en") {
			document.getElementById("pruebacookie").innerHTML = cookies + " <button type='button' onclick='acceptCookies()'>Accept</button>";
		} else {
			document.getElementById("pruebacookie").innerHTML = cookies + " <button type='button' onclick='acceptCookies()'>Aceptar</button>";
		}
	}else{
		document.getElementById("pruebacookie").style.display="none";
	}
});

function acceptCookies() {
	document.cookie = "accepted=true; path=/";
	document.getElementById("pruebacookie").style.display = "none";
}

function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}
</script>