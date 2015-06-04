<%@ page session="false"%>
<!DOCTYPE HTML>
<html lang="de-DE">
<head>
<meta charset="UTF-8">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="pragma" content="no-cache">
</head>
<body>
    <form
        action="${pageContext.request.contextPath}${deleteURL}"
         method="post">
         Do you really want to delete?
        <input name="" value="Delete" type="submit">
         <a href="${pageContext.request.contextPath}${cancelURL}" class="cancel">Cancel</a>

    </form>
</body>
</html>
