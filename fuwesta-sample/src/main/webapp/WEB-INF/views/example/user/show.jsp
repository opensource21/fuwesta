<%@include file="taglib_includes.jsp"%>
<%@ page session="false"%>
<!DOCTYPE HTML>
<html lang="de-DE">
<head>
<meta charset="UTF-8">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="pragma" content="no-cache">
<title><spring:message code="App.Title"></spring:message></title>
<link rel="stylesheet" href="<c:url value = '/resources/css/jsp-ppi.css'/>">
</head>
<body>
        <a href="${pageContext.request.contextPath}/user/list" class="list">Goto list</a>
    
	<table>
		<tbody>
			<tr>
				<td align="center"><h3>Show User Form</h3></td>
			</tr>
			<tr>
				<td><c:if test="${not empty message}">
						<div class="message">
							<spring:message code="${message}" />
						</div>
					</c:if></td>
			</tr>
			<tr align="center" valign="top">
				<td align="center"><form:form
						action="${pageContext.request.contextPath}/user/show/${user.id}"
						modelAttribute="user" method="post">
						<c:set var="disabled" value="true"/> 
                        <%@include file="editUserPart.jsp"%>
					</form:form></td>
			</tr>
		</tbody>
	</table>
</body>
</html>
