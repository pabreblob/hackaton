<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
<!-- <li class="nav-item">
        <a class="nav-link" href="#">Link</a>
      </li> -->
      <!-- <li class="nav-item dropdown">
      <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
        Dropdown link
      </a>
      <div class="dropdown-menu">
        <a class="dropdown-item" href="#">Link 1</a>
        <a class="dropdown-item" href="#">Link 2</a>
        <a class="dropdown-item" href="#">Link 3</a>
      </div>
   	 </li> -->
   	 
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container-fluid">
<div class="row">
<div class="col-2 col-sm-2 col-lg-4"></div>
<div class="col-8 col-sm-8 col-lg-4 ">
	<a href="welcome/index.do"><img style="max-width: 100%; max-heigh:50vh" class="img-responsive" src='<jstl:out value="${bannerUrl}"/>' alt="Acme Taxi Co., Inc." /></a>
</div>
<div class="col-2 col-sm-2 col-lg-4"></div></div>
</div>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo01" aria-controls="navbarTogglerDemo01" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarTogglerDemo01">
    <a class="navbar-brand" href="#">Acme-Taxi</a>
    <ul class="navbar-nav mr-auto mt-2 mt-lg-0">

     <!-- Acciones de usuario -->
     <security:authorize access="hasRole('USER')">
      <li class="nav-item dropdown">
      	<a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
        	<spring:message code="master.page.user"/>
      	</a>
      	<div class="dropdown-menu">
        	<a class="dropdown-item" href="reservation/user/list.do"><spring:message code="master.page.user.reservation.list"/></a>
          	<a class="dropdown-item" href="request/user/create.do"><spring:message code="master.page.user.request.create"/></a> 
          	<a class="dropdown-item" href="request/user/list.do"><spring:message code="master.page.user.request.list"/></a>
          	<a class="dropdown-item" href="review/user/list.do"><spring:message code="master.page.user.review.list"/></a>
          	<a class="dropdown-item" href="review/user/list-created.do"><spring:message code="master.page.user.review.created"/></a> 
          	<a class="dropdown-item" href="announcement/user/create.do"><spring:message code="master.page.user.announcement.create"/></a> 
          	<a class="dropdown-item" href="announcement/user/list.do"><spring:message code="master.page.user.announcement.list"/></a>
          	<a class="dropdown-item" href="announcement/user/list-created.do"><spring:message code="master.page.user.announcement.created"/></a>
          	<a class="dropdown-item" href="announcement/user/list-joined.do"><spring:message code="master.page.user.announcement.joined"/></a>
      	</div>
   	 </li>
     </security:authorize>
     
     <!-- Acciones de mecánico -->
     <security:authorize access="hasRole('MECHANIC')">
      <li class="nav-item dropdown">
      	<a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
        	<spring:message code="master.page.mechanic"/>
      	</a>
      	<div class="dropdown-menu">
        	<a class="dropdown-item" href="repairShop/mechanic/list-created.do"><spring:message code="master.page.mechanic.repairshop.created"/></a>
        	<a class="dropdown-item" href="repairShop/mechanic/create.do"><spring:message code="master.page.mechanic.repairshop.create"/></a>
     		<a class="dropdown-item" href="reservation/mechanic/list.do"><spring:message code="master.page.mechanic.reservation.list"/></a>
     		<a class="dropdown-item" href="review/mechanic/list.do"><spring:message code="master.page.mechanic.review.list"/></a>
      	</div>
   	 </li>
     </security:authorize>
     
     <!-- Acciones del driver -->
     
     <security:authorize access="hasRole('DRIVER')">
      <li class="nav-item dropdown">
      	<a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
        	<spring:message code="master.page.driver"/>
      	</a>
      	<div class="dropdown-menu">
        	<a class="dropdown-item" href="review/driver/list.do"><spring:message code="master.page.driver.review.list"/></a>
            <a class="dropdown-item" href="request/driver/listToAccept.do"><spring:message code="master.page.driver.request.listAccept"/></a> 
            <a class="dropdown-item" href="request/driver/listToDo.do"><spring:message code="master.page.driver.request.listDo"/></a> 
            <a class="dropdown-item" href="request/driver/oldList.do"><spring:message code="master.page.driver.request.listOld"/></a>
      	</div>
   	 </li>
     </security:authorize>
     
     <!-- Acciones del sponsor -->
     
     <security:authorize access="hasRole('SPONSOR')">
     <li class="nav-item dropdown">
      	<a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
        	<spring:message code="master.page.sponsor"/>
      	</a>
      	<div class="dropdown-menu">
        	<a class="dropdown-item" href="sponsorship/sponsor/create.do"><spring:message code="master.page.sponsor.sponsorship.create" /></a> 
            <a class="dropdown-item" href="sponsorship/sponsor/list.do"><spring:message code="master.page.sponsor.sponsorship.list" /></a>
      	</div>
   	 </li>
     </security:authorize>
     
     <!-- Acciones del admin -->
     <security:authorize access="hasRole('ADMIN')">
     <li class="nav-item dropdown">
      	<a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
        	<spring:message code="master.page.admin"/>
      	</a>
      	<div class="dropdown-menu">
          <a class="dropdown-item" href="configuration/admin/display.do"><spring:message code="master.page.admin.configuration.display" /></a> 
          <a class="dropdown-item" href="spamWord/list.do"><spring:message code="master.page.admin.spamword.list" /></a>
          <a class="dropdown-item" href="idNumberPattern/list.do"><spring:message code="master.page.admin.idnumber.list" /></a> 
          <a class="dropdown-item" href="sponsorship/admin/list.do"><spring:message code="master.page.admin.sponsorship.list" /></a> 
          <a class="dropdown-item" href="report/admin/list.do"><spring:message code="master.page.admin.report.list" /></a>
          <a class="dropdown-item" href="report/admin/listUnread.do"><spring:message code="master.page.admin.report.unread" /></a> 
          <a class="dropdown-item" href="request/admin/list.do"><spring:message code="master.page.admin.request.list"/></a>
      	</div>
   	 </li>
   	 
   	 <li class="nav-item dropdown">
      	<a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
        	<spring:message code="master.page.admin.marked"/>
      	</a>
      	<div class="dropdown-menu">
      		<a class="dropdown-item" href="repairShop/admin/list-marked.do"><spring:message code="master.page.admin.repairshop.marked" /></a>
          	<a class="dropdown-item" href="review/admin/list.do"><spring:message code="master.page.admin.review.marked"/></a>
          	<a class="dropdown-item" href="request/admin/markedList.do"><spring:message code="master.page.admin.request.marked"/></a>
      	</div>
   	 </li>
   	 
   	 <li class="nav-item dropdown">
      	<a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
        	<spring:message code="master.page.admin.actor.marked"/>
      	</a>
      	<div class="dropdown-menu">
      		<a class="dropdown-item" href="actor/admin/listSuspicious.do"><spring:message code="master.page.admin.actor.suspicious" /></a> 
        	<a class="dropdown-item" href="actor/admin/listBanned.do"><spring:message code="master.page.admin.actor.banned" /></a>
      	</div>
   	 </li>
   	 
   	 
   	 </security:authorize>
     
     <!-- Búsqueda -->
     <li class="nav-item dropdown">
     	<a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
        	<spring:message code="master.page.search"/>
      	</a>
      	<div class="dropdown-menu">
	      	<a class="dropdown-item" href="actor/list.do"><spring:message code="master.page.search.actor"/></a>
        	<a class="dropdown-item" href="announcement/search.do"><spring:message code="master.page.search.announcement"/></a>
        	<a class="dropdown-item" href="repairShop/list.do"><spring:message code="master.page.repairshop.list"/></a>
      	</div>
   	 </li>
     
     
     <!-- Registro de actores -->
     <security:authorize access="isAnonymous()">  
	 <li class="nav-item dropdown">
     	<a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
        	<spring:message code="master.page.access"/>
      	</a>
      	<div class="dropdown-menu">
        	<a class="dropdown-item" href="user/create.do"><spring:message code="master.page.user.register" /></a>
        	<a class="dropdown-item" href="driver/create.do"><spring:message code="master.page.driver.register" /></a>
        	<a class="dropdown-item" href="mechanic/create.do"><spring:message code="master.page.mechanic.register" /></a>
        	<a class="dropdown-item" href="sponsor/create.do"><spring:message code="master.page.sponsor.register" /></a>
      	</div>
   	 </li>
     </security:authorize>
     
     <security:authorize access="isAuthenticated()">
	 	<li><a class="nav-link" href="folder/actor/list.do"><spring:message code="master.page.folder.list" />(<jstl:out value="${unread}"/>)</a></li>	     
     </security:authorize>
    	<li class="nav-item">
        	<a class="nav-link" href="<spring:message code="master.page.language.url"/>"><spring:message code="master.page.language"/></a>
      	</li> 	       
    </ul>
    <ul class="navbar-nav navbar-right">
    	<!-- Reloj -->
    	<li><a id="clock" class="nav-link"></a></li>
    	
    	
    	<!-- Acciones de perfil -->
    	<security:authorize access="hasRole('USER')">
			<li><a class="nav-link" href="user/user/display.do"><spring:message code="master.page.profile.display" /></a></li>    	
    	</security:authorize>
    	<security:authorize access="hasRole('DRIVER')">
			<li><a class="nav-link" href="driver/driver/display.do"><spring:message code="master.page.profile.display" /></a></li>    	
    	</security:authorize>
    	<security:authorize access="hasRole('ADMIN')">
			<li><a class="nav-link" href="admin/admin/display.do"><spring:message code="master.page.profile.display" /></a></li>    	
    	</security:authorize>
    	<security:authorize access="hasRole('MECHANIC')">
			<li><a class="nav-link" href="mechanic/mechanic/display.do"><spring:message code="master.page.profile.display" /></a></li>    	
    	</security:authorize>
    	<security:authorize access="hasRole('SPONSOR')">
			<li><a class="nav-link" href="sponsor/sponsor/display.do"><spring:message code="master.page.profile.display" /></a></li>    	
    	</security:authorize>    	
    	
    	<!-- Login y logout -->
    	<security:authorize access="isAnonymous()">
    		 <li><a class="nav-link" href="security/login.do"><spring:message code="master.page.login" /></a></li>
    	</security:authorize>
    	<security:authorize access="isAuthenticated()">
    		 <li><a class="nav-link" href="j_spring_security_logout"><spring:message code="master.page.logout" /></a></li>
    	</security:authorize>
    </ul>
  </div>
</nav>

<style>
.navbar-nav > li > .dropdown-menu { 
	background-color: rgb(52,58,64);
}
.navbar-nav > li > .dropdown-menu > a{ 
	color: rgb(153,156,159);
}
.navbar-nav > li > .dropdown-menu > a:hover{ 
	color: rgb(52,58,64);
}
</style>
<script>
function startTime() {
    var today = new Date();
    var h = today.getHours();
    var m = today.getMinutes();
    var s = today.getSeconds();
    m = checkTime(m);
    s = checkTime(s);
    document.getElementById('clock').innerHTML =
    h + ":" + m + ":" + s;
    var t = setTimeout(startTime, 500);
}
function checkTime(i) {
    if (i < 10) {i = "0" + i};  // add zero in front of numbers < 10
    return i;
}
</script>