<%@page import="de.ppi.samples.fuwesta.model.Sex"%>
<form:errors path="*" htmlEscape="false" />
<form:hidden path="version" />
<table>
	<tbody>
		<tr>
			<td align="right" width="100"><spring:message
					code="de.ppi.samples.fuwesta.model.User.userId" /></td>
			<td width="150"><form:input path="userId" disabled="${disabled}" class="${disabled?'show':''}"/></td>
			<td align="left"><form:errors cssstyle="color:red" path="userId" /></td>
		</tr>
		<tr>
			<td align="right" width="100"><spring:message
					code="de.ppi.samples.fuwesta.model.User.firstName" /></td>
			<td width="150"><form:input path="firstName"
					disabled="${disabled}" class="${disabled?'show':''}"/></td>
			<td align="left"><form:errors cssstyle="color:red"
					path="firstName" /></td>
		</tr>
		<tr>
			<td align="right" width="100"><spring:message
					code="de.ppi.samples.fuwesta.model.User.lastName" /></td>
			<td width="150"><form:input path="lastName"
					disabled="${disabled}" class="${disabled?'show':''}"/></td>
			<td align="left"><form:errors cssstyle="color:red"
					path="lastName" /></td>
		</tr>

		<tr>
			<td align="right" width="100"><spring:message
					code="de.ppi.samples.fuwesta.model.User.sex" /></td>
			<td><form:select path="sex" multiple="false"
					disabled="${disabled}" class="${disabled?'show':''}">					
					<c:forEach items="${sexList}" var="sex">
						<form:option value="${sex}">
							<spring:message code="Sex.${sex}" />
						</form:option>
			         </c:forEach>
				</form:select></td>
			<td align="left"><form:errors cssstyle="color:red" path="sex" /></td>
		</tr>
		<tr>
			<td align="right" width="100"><spring:message
					code="de.ppi.samples.fuwesta.model.User.postings" /></td>
			<td><form:select path="postings" multiple="true"
					disabled="${disabled}" class="${disabled?'show':''}">
					<form:options items="${posts}" itemLabel="title" itemValue="id" />
				</form:select></td>
			<td align="left"><form:errors cssstyle="color:red"
					path="postings" /></td>
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
