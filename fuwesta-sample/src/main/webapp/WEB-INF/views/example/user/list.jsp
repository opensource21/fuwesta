<%@include file="taglib_includes.jsp"%>


<html>
<head>
<title><spring:message code="App.Title"></spring:message></title>
</head>
<body>
    <div class="content">
        <h3>List of users</h3>
        <a href="${pageContext.request.contextPath}/" >Home</a>
        <div class="paging">
            <%--For displaying Page numbers.
    The when condition does not display a link for the current page--%>
            <table border="1" cellpadding="5" cellspacing="5">
                <tr>
                    <%--For displaying Previous link except for the 1st page --%>
                    <c:if test="${!userList.firstPage}">
                        <td><a href="list?page.size=${pageRequest.pageSize}&page.page=${userList.number}">Previous</a></td>
                    </c:if>
                    <c:forEach begin="1" end="${userList.totalPages}" var="i">
                        <c:choose>
                            <c:when test="${userList.number +1 eq i}">
                                <td>${i}</td>
                            </c:when>
                            <c:otherwise>
                                <td><a href="list?page.size=${pageRequest.pageSize}&page.page=${i}">${i}</a></td>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <%--For displaying Next link --%>
                    <c:if test="${!userList.lastPage}">
                        <td><a href="list?page.size=${pageRequest.pageSize}&page.page=${userList.number + 2}">Next</a></td>
                    </c:if>
                </tr>
            </table>


        </div>
        <div class="data">
            <table border="0" cellpadding="4" cellspacing="0">
                <tr>
                    <th>ID</th>
                    <th><spring:message
                            code="de.ppi.samples.fuwesta.model.User.userId" /></th>
                    <th><spring:message
                            code="de.ppi.samples.fuwesta.model.User.firstName" /></th>
                    <th><spring:message
                            code="de.ppi.samples.fuwesta.model.User.lastName" /></th>
                    <th>Actions</th>
                </tr>
                <c:forEach var="user" items="${userList.content}">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.userId}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td><a href="${pageContext.request.contextPath}<spring:message code= 'url.user.show' arguments='${user.id}'/>" class="show">show</a> <a
                            href="edit/${user.id}" class="update">update</a> <a
                            href="delete/${user.id}" class="delete">delete</a></td>
                    </tr>
                </c:forEach>
                <c:if test="${userList.numberOfElements == 0}">
                    <tr>
                        <td colspan="5">No User Data</td>
                    </tr>
                </c:if>
            </table>
        </div>
        <br /> <a href="create" class="add">add new user</a>
    </div>
</body>
</html>
