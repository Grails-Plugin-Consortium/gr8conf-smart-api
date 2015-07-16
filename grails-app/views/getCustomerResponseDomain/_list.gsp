
<%@ page import="org.grails.demo.customer.GetCustomerResponseDomain" %>

<section id="index-getCustomerResponseDomain" class="first">
    <table class="table table-bordered table-striped margin-top-medium">
        <thead>
        <tr>
            
            <g:sortableColumn property="description" title="${message(code: 'getCustomerResponseDomain.description.label', default: 'Description')}" />
            
            <g:sortableColumn property="customerId" title="${message(code: 'getCustomerResponseDomain.customerId.label', default: 'Customer Id')}" />
            
            <g:sortableColumn property="firstName" title="${message(code: 'getCustomerResponseDomain.firstName.label', default: 'First Name')}" />
            
            <g:sortableColumn property="isDefault" title="${message(code: 'getCustomerResponseDomain.isDefault.label', default: 'Is Default')}" />
            
        </tr>
        </thead>
        <tbody>
        <g:each in="${getCustomerResponseDomainInstanceList}" status="i" var="getCustomerResponseDomainInstance">
            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                
                <td><g:link action="show" id="${getCustomerResponseDomainInstance.id}">${fieldValue(bean: getCustomerResponseDomainInstance, field: "description")}</g:link></td>
                
                <td>${fieldValue(bean: getCustomerResponseDomainInstance, field: "customerId")}</td>
                
                <td>${fieldValue(bean: getCustomerResponseDomainInstance, field: "firstName")}</td>
                
                <td><g:formatBoolean boolean="${getCustomerResponseDomainInstance.isDefault}" /></td>
                
            </tr>
        </g:each>
        </tbody>
    </table>
    <div>
        <bs:paginate total="${getCustomerResponseDomainInstanceCount}" />
    </div>
</section>