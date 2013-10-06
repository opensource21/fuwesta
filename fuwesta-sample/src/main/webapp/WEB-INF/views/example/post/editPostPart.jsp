<form:errors path="*"  htmlEscape="false"/>
<form:hidden path="version" />
<table>
    <tbody>
        <tr>
            <td align="right" width="100"><spring:message
                    code="de.ppi.samples.fuwesta.model.Post.title" /></td>
            <td width="150"><form:input path="title" disabled="${disabled}"/></td>
            <td align="left"><form:errors cssstyle="color:red" path="title"/></td>
        </tr>
        <tr>
            <td align="right" width="100"><spring:message
                    code="de.ppi.samples.fuwesta.model.Post.content" /></td>
            <td width="150"><form:textarea path="content"  disabled="${disabled}"/></td>
            <td align="left"><form:errors cssstyle="color:red"
                    path="content"/></td>
        </tr>
        <tr>
            <td align="right" width="100"><spring:message
                    code="de.ppi.samples.fuwesta.model.Post.creationTime" /></td>
            <td width="150"><form:input path="creationTime"  disabled="${disabled}"/></td>
            <td align="left"><form:errors cssstyle="color:red"
                    path="creationTime"/></td>
        </tr>
        <tr>
            <td align="right" width="100"><spring:message
                    code="de.ppi.samples.fuwesta.model.Post.user" /></td>
            <td><form:select path="user" multiple="false"  disabled="${disabled}">
                    <form:options items="${users}" itemLabel="userId" itemValue="id" />
                </form:select></td>
            <td align="left"><form:errors cssstyle="color:red"
                    path="user"/></td>
        </tr>
        <tr>
            <td align="right" width="100"><spring:message
                    code="de.ppi.samples.fuwesta.model.Post.tags" /></td>
            <td><form:select path="tags" multiple="true"  disabled="${disabled}">
                    <form:options items="${tags}" itemLabel="name" itemValue="id" />
                </form:select></td>
            <td align="left"><form:errors cssstyle="color:red"
                    path="tags"/></td>
        </tr>
        <c:if test="${not disabled}">
        <tr>
            <td colspan="3" align="center"><input name="" value="Save"
                type="submit"> &nbsp; <input name="" value="Reset"
                type="reset"> &nbsp;</td>
        </tr>
        </c:if>
    </tbody>
</table>
