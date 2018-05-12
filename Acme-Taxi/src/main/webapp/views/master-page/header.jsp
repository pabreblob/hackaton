<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<style type="text/css">
	.headerImg{
	height: auto;
	max-height: 350px;
	width: auto;
	max-width: 955px; 
	
	}
</style>
<div>
	<a href="welcome/index.do"><img class="headerImg" src='<jstl:out value="${bannerUrl}"/>' alt="Acme Taxi Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		
		
		
		<security:authorize access="hasRole('ADMIN')">
		
		<!-- Acciones de Admin -->
			<li><a class="fNiv"><spring:message	code="master.page.admin" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="configuration/admin/display.do"><spring:message code="master.page.configuration.admin.display" /></a></li>
					<li><a href="spamWord/admin/list.do"><spring:message code="master.page.spamword.admin.list" /></a></li>
					<li><a href="idNumberPattern/admin/list.do"><spring:message code="master.page.idnumber.admin.list" /></a></li>
					<li><a href="sponsorship/admin/list.do"><spring:message code="master.page.sponsorship.admin.list" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('SPONSOR')">
		
		<!-- Acciones de Sponsor -->
			<li><a class="fNiv"><spring:message	code="master.page.sponsor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="sponsorship/sponsor/create.do"><spring:message code="master.page.sponsor.sponsorship.create" /></a></li>
					<li><a href="sponsorship/sponsor/list.do"><spring:message code="master.page.sponsor.sponsorship.list" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
		
		<!-- Acciones del perfil -->
		<li><a class="fNiv"><spring:message code="master.page.profile" /></a>
			<ul>
			
			<!-- Ver perfil -->
				<li class="arrow"></li>
				<security:authorize access="hasRole('MECHANIC')">
					<li><a href="mechanic/mechanic/display.do"><spring:message code="master.page.profile.display"/></a></li>
				</security:authorize>
				
				<security:authorize access="hasRole('SPONSOR')">
					<li><a href="sponsor/sponsor/display.do"><spring:message code="master.page.profile.display"/></a></li>
				</security:authorize>
				
				<security:authorize access="hasRole('USER')">
					<li><a href="user/user/display.do"><spring:message code="master.page.profile.display"/></a></li>
				</security:authorize>
				
				<security:authorize access="hasRole('DRIVER')">
					<li><a href="driver/driver/display.do"><spring:message code="master.page.profile.display"/></a></li>
				</security:authorize>
				
				<security:authorize access="hasRole('ADMIN')">
					<li><a href="admin/admin/display.do"><spring:message code="master.page.profile.display"/></a></li>
				</security:authorize>
				
				<!-- Carpetas -->
				<li><a href="folder/actor/list.do"><spring:message code="master.page.folder.list"/></a></li>
			</ul>
		</li>
		</security:authorize>
				
		<security:authorize access="isAnonymous()">
		
		<!-- Acceder al sistema -->
			<li><a class="fNiv"><spring:message code="master.page.access"/></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="security/login.do"><spring:message code="master.page.login" /></a></li>
					<li><a href="mechanic/create.do"><spring:message code="master.page.mechanic.register" /></a></li>
					<li><a href="sponsor/create.do"><spring:message code="master.page.sponsor.register" /></a></li>
					<li><a href="user/create.do"><spring:message code="master.page.user.register" /></a></li>
					<li><a href="driver/create.do"><spring:message code="master.page.driver.register" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
		
		<!-- Salir del sistema -->
			<li><a class="fNiv" href="j_spring_security_logout"><spring:message code="master.page.logout" /></a></li>
		</security:authorize>
		
		<!-- Cambio de Idioma -->
		<li id="rightB">
			<a class="fNiv" href="<spring:message code="master.page.language.url"/>"><spring:message code="master.page.language"/></a>
		</li>
	</ul>
</div>

