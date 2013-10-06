<%@ page session="false"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE HTML>
<html lang="de-DE">
<head>
<meta charset="UTF-8">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="pragma" content="no-cache">
<title><spring:message code="App.Title"></spring:message></title>
</head>
<body>
    <form:form
        action="${pageContext.request.contextPath}${deleteURL}"
         method="post">
         Do you really want to delete?
        <input name="" value="Delete" type="submit">
         <a href="${pageContext.request.contextPath}${cancelURL}" class="cancel">Cancel</a>

    </form:form>
</body>
</html>
