package org.grails.demo.data


class CustomerRefreshJob {

    static queue = 'customerRefreshQueue'
    static workerPool = 'customerRefreshPool'

    def customerService

    def perform(String brandID, String customerNumber) {
//        customerService.getCustomerAndCache(brandID, customerNumber, false)
    }
}
