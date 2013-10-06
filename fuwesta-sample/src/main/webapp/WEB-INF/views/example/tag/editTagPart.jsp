<form:errors path="*" htmlEscape="false"/>
<form:hidden path="version" />
<table>
	<tbody>
		<tr>
			<td align="right" width="100"><spring:message
					code="de.ppi.samples.fuwesta.model.Tag.name" /></td>
			<td width="150"><form:input path="name" disabled="${disabled}"/></td>
			<td align="left"><form:errors cssstyle="color:red" path="name"/></td>
		</tr>
		<tr>
			<td align="right" width="100"><spring:message
					code="de.ppi.samples.fuwesta.model.Tag.active" /></td>
			<td width="150"><form:checkbox path="active"  disabled="${disabled}"/></td>
			<td align="left"><form:errors cssstyle="color:red"
					path="active"/></td>
		</tr>
		<tr>
			<td align="right" width="100"><spring:message
					code="de.ppi.samples.fuwesta.model.Tag.postings" /></td>
			<td><form:select path="postings" multiple="true"  disabled="${disabled}">
					<form:options items="${posts}" itemLabel="title" itemValue="id" />
				</form:select></td>
			<td align="left"><form:errors cssstyle="color:red"
                    path="postings"/></td>
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
