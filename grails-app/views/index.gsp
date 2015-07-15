<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Grails Smart Api</title>
</head>

<body>

<div class="row"><h2>Urls</h2></div>

<table class="table-striped table">
<g:each in="${grailsApplication.config.cxf.client}" var="client">
    <tr >
        <td>${((Map.Entry)client).key}=${((Map.Entry)client).value}</td>
    </tr>
</g:each>
</table>

</body>
</html>
