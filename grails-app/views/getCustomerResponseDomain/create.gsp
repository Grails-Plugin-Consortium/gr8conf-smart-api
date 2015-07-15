<%@ page import="org.grails.demo.customer.GetCustomerResponseDomain" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="main" />
	<g:set var="entityName" value="${message(code: 'getCustomerResponseDomain.label', default: 'GetCustomerResponseDomain')}" />
	<title><g:message code="default.create.label" args="[entityName]" /></title>
</head>

<body>

	<section id="create-getCustomerResponseDomain" class="first">

		<g:hasErrors bean="${getCustomerResponseDomainInstance}">
		<div class="alert alert-danger">
			<g:renderErrors bean="${getCustomerResponseDomainInstance}" as="list" />
		</div>
		</g:hasErrors>

		<g:form action="save" class="form-horizontal" role="form" >
			<g:render template="form"/>

			<div class="form-actions margin-top-medium">
				<g:submitButton name="create" class="btn btn-primary" value="${message(code: 'default.button.create.label', default: 'Create')}" />
	            <button class="btn" type="reset"><g:message code="default.button.reset.label" default="Reset" /></button>
			</div>
		</g:form>

	</section>

</body>

</html>
