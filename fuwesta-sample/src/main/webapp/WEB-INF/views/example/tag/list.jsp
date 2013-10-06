<%@include file="taglib_includes.jsp"%>


<html>
<head>
<title><spring:message code="App.Title"></spring:message></title>
</head>
<body>
    <div class="content">
        <h3>List of tags</h3>
        <a href="${pageContext.request.contextPath}/" >Home</a>
        <div class="paging">
            <%--For displaying Page numbers.
    The when condition does not display a link for the current page--%>
            <table border="1" cellpadding="5" cellspacing="5">
                <tr>
                    <%--For displaying Previous link except for the 1st page --%>
                    <c:if test="${!tagList.firstPage}">
                        <td><a href="list?page.size=${pageRequest.pageSize}&page.page=${tagList.number}">Previous</a></td>
                    </c:if>
                    <c:forEach begin="1" end="${tagList.totalPages}" var="i">
                        <c:choose>
                            <c:when test="${tagList.number +1 eq i}">
                                <td>${i}</td>
                            </c:when>
                            <c:otherwise>
                                <td><a href="list?page.size=${pageRequest.pageSize}&page.page=${i}">${i}</a></td>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <%--For displaying Next link --%>
                    <c:if test="${!tagList.lastPage}">
                        <td><a href="list?page.size=${pageRequest.pageSize}&page.page=${tagList.number + 2}">Next</a></td>
                    </c:if>
                </tr>
            </table>


        </div>
        <div class="data">
            <table border="0" cellpadding="4" cellspacing="0">
                <tr>
                    <th>ID</th>
                    <th><spring:message
                            code="de.ppi.samples.fuwesta.model.Tag.name" /></th>
                    <th><spring:message
                            code="de.ppi.samples.fuwesta.model.Tag.active" /></th>
                    <th>Actions</th>
                </tr>
                <c:forEach var="tag" items="${tagList.content}">
                    <tr>
                        <td>${tag.id}</td>
                        <td>${tag.name}</td>
                        <td>${tag.active}</td>
                        <td><a href="show/${tag.id}" class="show">show</a> <a
                            href="edit/${tag.id}" class="update">update</a> <a
                            href="delete/${tag.id}" class="delete">delete</a></td>
                    </tr>
                </c:forEach>
                <c:if test="${tagList.numberOfElements == 0}">
                    <tr>
                        <td colspan="5">No Tag Data</td>
                    </tr>
                </c:if>
            </table>
        </div>
        <br /> <a href="create" class="add">add new tag</a>
    </div>
</body>
</html>
