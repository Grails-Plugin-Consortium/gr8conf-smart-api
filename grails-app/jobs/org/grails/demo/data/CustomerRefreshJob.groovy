package org.grails.demo.data

import org.grails.demo.customer.CustomerService


class CustomerRefreshJob {

    static queue = 'customerRefreshQueue'
    static workerPool = 'customerRefreshPool'

    CustomerService customerService

    def perform(Integer customerId, String firstName) {
        println "Fetching and caching customer ${customerId} now..."
        customerService.getCustomerAndCache(customerId, firstName, false)
    }
}
