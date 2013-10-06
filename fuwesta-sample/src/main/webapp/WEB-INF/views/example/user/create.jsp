<%@include file="taglib_includes.jsp"%>
<%@ page session="false"%>
<!DOCTYPE HTML>
<html lang="de-DE">
<head>
<meta charset="UTF-8">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="pragma" content="no-cache">
<title><spring:message code="App.Title"></spring:message></title>
</head>
<body>
    <a href="${pageContext.request.contextPath}/user/list" class="list">Goto list</a>
    <table>
        <tbody>
            <tr>
                <td align="center"><h3>Create User Form</h3></td>
            </tr>
            <tr>
                <td><c:if test="${not empty message}">
                        <div class="message">
                            <spring:message code="${message}" />
                        </div>
                    </c:if>
                </td>
            </tr>
            <tr align="center" valign="top">
                <td align="center"><form:form action="create"
                        modelAttribute="user" method="post">
                        <c:set var="disabled" value="false"/>
                        <%@include file="editUserPart.jsp"%>
                    </form:form></td>
            </tr>
        </tbody>
    </table>
</body>
</html>
