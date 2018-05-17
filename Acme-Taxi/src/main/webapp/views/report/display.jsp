<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div style="margin-left: auto; margin-right: auto; position: center; display: block; width:50%; border: black solid 1px;">
	<h2 style="text-align: center"><spring:message code="report.report"/></h2>
	<p style="margin-left:2%;"><b><spring:message code="report.reporting"/></b><jstl:out value="${report.reported.name}"/> <jstl:out value="${report.reported.surname}"/> (<a href="actor/display.do?actorId=${report.reported.id}"><jstl:out value="${report.reported.userAccount.username}"/></a>)</p>
	<p style="margin-left:2%;"><b><spring:message code="report.writtenBy"/></b><jstl:out value="${report.creator.name}"/> <jstl:out value="${report.creator.surname}"/> (<a href="actor/display.do?actorId=${report.creator.id}"><jstl:out value="${report.creator.userAccount.username}"/></a>)</p>
	<p style="margin-left:2%;"><b><spring:message code="report.reason"/></b></p>
	<p style="margin-left:2%; margin-right:2%;"><jstl:out value="${report.reason}"/></p>
	<spring:message code="report.format" var="dateFormat"/>
	<p style="margin-left:2%;"><b><spring:message code="report.moment"/>: </b><fmt:formatDate value="${report.moment}" pattern="${dateFormat}" /></p>
	<jstl:if test="${report.imageUrl != '' or report.imageUrl == null}">
		<img id="myImg" src="<jstl:out value="${report.imageUrl}"/>" width="60%" style="margin-bottom: 2%; margin-left: auto; margin-right: auto; display:block; position:center;"/>
		<p style="text-align: center;"><spring:message code="report.clickOnImage"/></p>
	</jstl:if>
</div>

<div id="myModal" class="modal">
  <span class="close">&times;</span>
  <img class="modal-content" id="img01">
  <div id="caption"></div>
</div>

<script>
var modal = document.getElementById('myModal');
var img = document.getElementById('myImg');
var modalImg = document.getElementById("img01");
var captionText = document.getElementById("caption");
img.onclick = function(){
    modal.style.display = "block";
    modalImg.src = this.src;
    captionText.innerHTML = this.alt;
}
var span = document.getElementsByClassName("close")[0];
span.onclick = function() { 
    modal.style.display = "none";
}
</script>
<style>

#myImg {
    border-radius: 5px;
    cursor: pointer;
    transition: 0.3s;
}

#myImg:hover {opacity: 0.7;}

.modal {
    display: none; /* Hidden by default */
    position: fixed; /* Stay in place */
    z-index: 1; /* Sit on top */
    padding-top: 100px; /* Location of the box */
    left: 0;
    top: 0;
    width: 100%; /* Full width */
    height: 100%; /* Full height */
    overflow: auto; /* Enable scroll if needed */
    background-color: rgb(0,0,0); /* Fallback color */
    background-color: rgba(0,0,0,0.9); /* Black w/ opacity */
}

.modal-content {
    margin: auto;
    display: block;
    width: 80%;
    max-width: 700px;
}

#caption {
    margin: auto;
    display: block;
    width: 80%;
    max-width: 700px;
    text-align: center;
    color: #ccc;
    padding: 10px 0;
    height: 150px;
}

.modal-content, #caption {    
    -webkit-animation-name: zoom;
    -webkit-animation-duration: 0.6s;
    animation-name: zoom;
    animation-duration: 0.6s;
}

@-webkit-keyframes zoom {
    from {-webkit-transform:scale(0)} 
    to {-webkit-transform:scale(1)}
}

@keyframes zoom {
    from {transform:scale(0)} 
    to {transform:scale(1)}
}

.close {
    position: absolute;
    top: 15px;
    right: 35px;
    color: #f1f1f1;
    font-size: 40px;
    font-weight: bold;
    transition: 0.3s;
}

.close:hover,
.close:focus {
    color: #bbb;
    text-decoration: none;
    cursor: pointer;
}

@media only screen and (max-width: 700px){
    .modal-content {
        width: 100%;
    }
}
</style>

