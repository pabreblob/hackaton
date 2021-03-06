<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
 
<%@taglib prefix="jstl"  uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%> 
<%@taglib prefix="display" uri="http://displaytag.sf.net"%> 
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %> 
 
<acme:button url="spamWord/create.do" code="spamWord.create"/>
<display:table class="displaytag"  requestURI="spamWord/list.do" name="spamWords" sort="external" partialList="true" size="${total}"
	pagesize="5" id="row">   
  <spring:message code="spamWord.word" var="word"/> 
  <display:column property="word" sortable="true" sortName="word" title="${word}"/>    
  
  <spring:message code="spamWord.edit" var="editHeader"/> 
  <display:column title="${editHeader}"> 
      <a href="spamWord/edit.do?spamWordId=${row.id}"><spring:message code="spamWord.edit"/> </a> 
  </display:column> 
 
  <spring:message code="spamWord.delete" var="deleteHeader"/> 
  <display:column title="${deleteHeader}"> 
      <a href="spamWord/delete.do?spamWordId=${row.id}"><spring:message code="spamWord.delete"/> </a> 
  </display:column>
</display:table>