
<%@ page import="org.grails.demo.customer.GetCustomerResponseDomain" %>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="main" />
	<g:set var="entityName" value="${message(code: 'getCustomerResponseDomain.label', default: 'GetCustomerResponseDomain')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

<body>

<g:render template="list"/>

</body>

</html>
