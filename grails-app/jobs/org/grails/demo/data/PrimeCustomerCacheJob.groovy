package org.grails.demo.data

class PrimeCustomerCacheJob {

    static queue = 'primeCustomerCacheQueue'
    static workerPool = 'primeCustomerCachePool'

    def customerService

    def perform(String customerId) {
//        customerService.get
    }
}
