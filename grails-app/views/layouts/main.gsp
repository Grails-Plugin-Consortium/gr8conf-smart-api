<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>Grails Smart Api</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <script>
        var siteUrl = '${createLink(absolute: false, uri: '/assets')}/';
        var imageUrl = '${createLink(absolute: false, uri: '/')}/';

    </script>
    <asset:stylesheet href="application.css"/>
    <!--[if lt IE 8 ]><asset:stylesheet href="thirdparty/font-awesome-ie7.css"/><![endif]-->
    <asset:javascript src="application.js"/>
    <g:layoutHead/>


    <asset:link rel="shortcut icon" href="/favicon.ico" type="image/x-icon"/>
    <asset:link rel="icon" href="/favicon.ico" type="image/x-icon"/>
    <!-- fav and touch icons -->
    <asset:link rel="apple-touch-icon-precomposed" href="/ico/apple-touch-icon-144-precomposed.png" sizes="144x144"/>
    <asset:link rel="apple-touch-icon-precomposed" href="/ico/apple-touch-icon-114-precomposed.png" sizes="114x114"/>
    <asset:link rel="apple-touch-icon-precomposed" href="/ico/apple-touch-icon-72-precomposed.png" sizes="72x72"/>
    <asset:link rel="apple-touch-icon-precomposed" href="ico/apple-touch-icon-57-precomposed.png"/>
</head>

<body class="contrast-blue">
<g:render template="/templates/header"/>
<!-- Header End====================================================================== -->
%{--<g:if test="${session.user}">--}%
    <g:render template="/templates/sideNav"/>
%{--</g:if>--}%

%{--<section id="${session?.user ? 'content' : 'open-content'}">--}%
<section id="content">
    <div class="container">
        <div class="row" id="content-wrapper">
            <div class="col-xs-12">
                <g:render template="/layouts/content"/>
            </div>
        </div>

        <g:render template="/templates/footer"/>
    </div>
</section>
<!-- Footer ================================================================== -->
</body>
</html>