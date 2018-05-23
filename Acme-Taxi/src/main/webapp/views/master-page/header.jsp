<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

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
      <li class="nav-item">
        <a class="nav-link" href="welcome/index.do">Home</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">Link</a>
      </li>
    </ul>
    <ul class="navbar-nav navbar-right">
    	<security:authorize access="isAnonymous()">
    		 <li><a class="nav-link" href="security/login.do">Login</a></li>
    	</security:authorize>
    	<security:authorize access="isAuthenticated()">
    		 <li><a class="nav-link" href="j_spring_security_logout">Logout</a></li>
    	</security:authorize>
    </ul>
  </div>
</nav>

