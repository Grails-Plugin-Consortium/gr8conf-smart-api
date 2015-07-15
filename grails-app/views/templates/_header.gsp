<header>
    <nav class="navbar navbar-contrast-blue">
        <a class="navbar-brand" href="${createLink(uri: '/')}">
            Simulation Tool Admin
        </a>
        <g:if test="${session.user}">
            <a class="toggle-nav btn pull-left" href="#">
                <i class="icon-reorder"></i>
            </a>
        </g:if>
        <ul class="nav">

            <g:if test="${session.user}">
                <li class="dropdown dark user-menu">

                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <span class="user-name">Logged In</span>
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="${createLink(uri: '/admin/dbconsole')}">
                                <i class="fa fa-database"></i>
                                DB Console
                            </a>
                        </li>
                        <li>
                            <a href="${createLink(controller: 'login', action: 'logout')}">
                                <i class="icon-signout"></i>
                                Sign out
                            </a>
                        </li>
                    </ul>
                </li>
            </g:if>
            <g:else>
                <li class="dark user-menu">
                    <a href="${createLink(controller: 'login', action: 'auth')}">
                        <span class="user-name">Log In</span>
                    </a>
                </li>
            </g:else>
        </ul>
    </nav>
</header>