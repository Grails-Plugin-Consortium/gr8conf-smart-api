package org.grails.demo.customer

import grails.transaction.Transactional

/**
 * GetCustomerResponseDomainController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Transactional(readOnly = true)
class GetCustomerResponseDomainController {

    static scaffold = GetCustomerResponseDomain
}
