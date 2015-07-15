<% import grails.persistence.Event %>
<%=packageName%>
<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="main" />
	<g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" />
	<title><g:message code="default.index.label" args="[entityName]" /></title>
</head>

<body>
<g:render template="list"/>
</body>

</html>
