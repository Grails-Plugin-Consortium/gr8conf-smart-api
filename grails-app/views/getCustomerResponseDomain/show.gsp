
<%@ page import="org.grails.demo.customer.GetCustomerResponseDomain" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="main" />
	<g:set var="entityName" value="${message(code: 'getCustomerResponseDomain.label', default: 'GetCustomerResponseDomain')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

<section id="show-getCustomerResponseDomain" class="first">

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="getCustomerResponseDomain.description.label" default="Description" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: getCustomerResponseDomainInstance, field: "description")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="getCustomerResponseDomain.customerId.label" default="Customer Id" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: getCustomerResponseDomainInstance, field: "customerId")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="getCustomerResponseDomain.isDefault.label" default="Is Default" /></td>
				
				<td valign="top" class="value"><g:formatBoolean boolean="${getCustomerResponseDomainInstance?.isDefault}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="getCustomerResponseDomain.responseXml.label" default="Response Xml" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: getCustomerResponseDomainInstance, field: "responseXml")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="getCustomerResponseDomain.dateCreated.label" default="Date Created" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${getCustomerResponseDomainInstance?.dateCreated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="getCustomerResponseDomain.lastUpdated.label" default="Last Updated" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${getCustomerResponseDomainInstance?.lastUpdated}" /></td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
