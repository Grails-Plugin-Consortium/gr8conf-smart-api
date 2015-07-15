<%@ page import="org.grails.demo.customer.GetCustomerResponseDomain" %>



			<div class="${hasErrors(bean: getCustomerResponseDomainInstance, field: 'description', 'error')} required">
				<label for="description" class="control-label"><g:message code="getCustomerResponseDomain.description.label" default="Description" /><span class="required-indicator">*</span></label>
				<div>
					<g:textField class="form-control" name="description" required="" value="${getCustomerResponseDomainInstance?.description}"/>
					<span class="help-inline">${hasErrors(bean: getCustomerResponseDomainInstance, field: 'description', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: getCustomerResponseDomainInstance, field: 'customerId', 'error')} ">
				<label for="customerId" class="control-label"><g:message code="getCustomerResponseDomain.customerId.label" default="Customer Id" /></label>
				<div>
					<g:textField class="form-control" name="customerId" value="${getCustomerResponseDomainInstance?.customerId}"/>
					<span class="help-inline">${hasErrors(bean: getCustomerResponseDomainInstance, field: 'customerId', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: getCustomerResponseDomainInstance, field: 'isDefault', 'error')} ">
				<label for="isDefault" class="control-label"><g:message code="getCustomerResponseDomain.isDefault.label" default="Is Default" /></label>
				<div>
					<g:checkBox name="isDefault" value="${getCustomerResponseDomainInstance?.isDefault}" />
					<span class="help-inline">${hasErrors(bean: getCustomerResponseDomainInstance, field: 'isDefault', 'error')}</span>
				</div>
			</div>

			<div class="${hasErrors(bean: getCustomerResponseDomainInstance, field: 'responseXml', 'error')} ">
				<label for="responseXml" class="control-label"><g:message code="getCustomerResponseDomain.responseXml.label" default="Response Xml" /></label>
				<div>
					<g:textArea class="form-control" name="responseXml" cols="40" rows="5" value="${getCustomerResponseDomainInstance?.responseXml}"/>
					<span class="help-inline">${hasErrors(bean: getCustomerResponseDomainInstance, field: 'responseXml', 'error')}</span>
				</div>
			</div>

