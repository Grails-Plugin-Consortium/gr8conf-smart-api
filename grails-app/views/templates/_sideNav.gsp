<%@ page import="org.codehaus.groovy.grails.commons.GrailsClassUtils" %>
<div id="main-nav-bg"></div>
<nav id="main-nav">
    <div class="navigation">

        <ul class="nav nav-stacked">
            <li class="${controllerName == 'home' ? 'active' : ''}">
                <a href="${createLink(controller: 'home')}">
                    <i class="icon-dashboard"></i>
                    <span>Home Page</span>
                </a>
            </li>
            <g:set var="customerControllers" value="${grailsApplication.controllerClasses.grep { GrailsClassUtils.isClassBelowPackage(it.clazz, ['org.grails.demo.customer'])}.collect{it.logicalPropertyName}}"/>
            <li class=" ${customerControllers.contains(controllerName) ? 'active' :''}">
                <a class="dropdown-collapse ${customerControllers.contains(controllerName) ? 'in' :''}" href="#"><i class="icon-edit"></i>
                    <span>Customer</span>
                    <i class="icon-angle-down angle-down"></i>
                </a>

                <ul class="nav nav-stacked ${customerControllers.contains(controllerName) ? 'in' :''}">
                    <g:each in="${customerControllers}" var="controller">
                        <li class="${controllerName == controller ? 'selected' : ''}">
                            <a href="${createLink(controller: controller)}">
                                <i class="icon-caret-right"></i>
                                <span><g:message code="${controller}.label" /> </span>
                            </a>
                        </li>
                    </g:each>
                </ul>
            </li>

            <li>
                <a href="${createLink(controller:'sessionMode', action:'manage')}">
                    <i class="icon-tag"></i>
                    <span>Session Control</span>
                </a>
            </li>

            <li>
                <a href="${createLink(uri: '/services')}">
                    <i class="icon-list-alt"></i>
                    <span>Service WSDLs</span>
                </a>
            </li>
            <li>
                <a href="${createLink(controller: 'systemInfo', action: 'index')}">
                    <i class="icon-gears"></i>
                    <span>System Info</span>
                </a>
            </li>
        </ul>
    </div>
</nav>