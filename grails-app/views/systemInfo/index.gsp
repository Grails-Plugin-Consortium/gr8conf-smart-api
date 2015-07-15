<html>
<head>
    <meta name='layout' content='main'/>
    <title>System Info</title>
    <g:set var="layout_nomainmenu"		value="${true}" scope="request"/>
    <g:set var="layout_nosecondarymenu"	value="${true}" scope="request"/>
    <asset:stylesheet href="login/login.css"/>
</head>

<body>

<div class="row">
    <div class="col-xs-12">
        <h1>System Info</h1>
    </div>
</div>

<div class="row">
    <div class="col-xs-4">
        <h5>DB Path</h5>
    </div>
    <div class="col-xs-8">
        jdbc:h2:${System.properties['catalina.base']}/db/prodDb
    </div>
</div>

</body>
</html>
