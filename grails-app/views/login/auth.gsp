<html>
<head>
    <meta name='layout' content='main'/>
    <title>Login</title>
    <asset:stylesheet href="login/login.css"/>
    <g:set var="layout_nosecondarymenu" value="${true}" scope="request"/>
</head>

<body>

<div class="main-login col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3">
    <!-- start: LOGIN BOX -->
    <div class="box-login">
        <h3>Sign in to your account</h3>

        <p>
            Please enter your name and password to log in.
        </p>

        <g:form class="form-login" controller="login" action="login" novalidate="novalidate">
            <g:if test="${authMessage}">
                <div class="errorHandler alert alert-danger">
                    <i class="fa fa-remove-sign"></i> ${authMessage}
                </div>
            </g:if>
            <fieldset>
                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="fa fa-user"></i></span>
                        <input type="text" class="form-control" name="username" placeholder="Username">
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                        <input type="password" class="form-control password" name="password" placeholder="Password">
                    </div>
                </div>

                <div class="form-group">
                    <button type="submit" class="btn btn-primary pull-right">
                        Login <i class="fa fa-arrow-circle-right"></i>
                    </button>
                </div>

            </fieldset>
        </g:form>
    </div>
</div>
</body>
</html>
