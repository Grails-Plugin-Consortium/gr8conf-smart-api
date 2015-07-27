package org.grails.demo.customer

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty
import org.grails.demo.BaseCacheService
import org.grails.demo.soap.customer.Customer

import javax.jws.WebParam
import javax.xml.ws.soap.SOAPFaultException

/**
 * MonitoredCustomerService
 * This will wrap all calls in hystrix commands.  This is a seperate service because the main service looks at the request object and hystrix uses background threads.  #EasyButton for now.
 */
class MonitoredCustomerService extends BaseCacheService implements org.grails.demo.soap.customer.CustomerService{

    @HystrixCommand(commandProperties = [
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "30000")
    ], threadPoolProperties = [
            @HystrixProperty(name = "coreSize", value = "10"),
            @HystrixProperty(name = "maxQueueSize", value = "20"),
            @HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
            @HystrixProperty(name = "queueSizeRejectionThreshold", value = "18")
    ])
    @Override
    Customer getCustomer(int customerId, String firstName) {
        Customer customer = new Customer()
        try {
            customer = customerServiceClient.getCustomer(customerId, firstName)
        } catch(SOAPFaultException connectionException){
            throw connectionException
        } catch (Exception e) {
            log.error(e)
        }

        customer
    }

    @HystrixCommand(commandProperties = [
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "30000")
    ], threadPoolProperties = [
            @HystrixProperty(name = "coreSize", value = "10"),
            @HystrixProperty(name = "maxQueueSize", value = "20"),
            @HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
            @HystrixProperty(name = "queueSizeRejectionThreshold", value = "18")
    ])
    @Override
    List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<Customer>()
        try {
            customers = customerServiceClient.getCustomers()
        } catch(ConnectException connectionException){
            throw connectionException
        } catch (Exception e) {
            log.error(e)
        }

        customers
    }

    @Override
    Customer makePayment(
            @WebParam(name = "CustomerId", targetNamespace = "") int customerId,
            @WebParam(name = "PaymentDate", targetNamespace = "") Date paymentDate, @WebParam(name = "PaymentAmount", targetNamespace = "") Double paymentAmount) {
        return null
    }
}
