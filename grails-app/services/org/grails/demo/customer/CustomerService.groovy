package org.grails.demo.customer

import com.google.gson.reflect.TypeToken
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty
import org.grails.cxf.utils.GrailsCxfEndpoint
import org.grails.demo.BaseCacheService
import org.grails.demo.soap.customer.Customer
import org.grails.demo.soap.customer.GetCustomerResponse
import org.grails.demo.soap.customer.GetCustomersResponse
import org.grails.demo.soap.customer.ObjectFactory
import org.springframework.beans.factory.annotation.Value

import javax.jws.WebParam
import java.lang.reflect.Type

@GrailsCxfEndpoint
class CustomerService extends BaseCacheService implements org.grails.demo.soap.customer.CustomerService {

    private static final ObjectFactory objectFactory = new ObjectFactory()

    @Value('${customer.cache.expire}')
    private int CUSTOMER_CACHE_EXPIRE

    @Value('${customer.cache.prefix}')
    private String CUSTOMER_CACHE_PREFIX

    @Override
    Customer getCustomer(@WebParam(name = "customerId", targetNamespace = "") int customerId) {
        Customer customer

        if (mockEnabled) {
            customer = getMockCustomer(customerId?.toString())
        } else if (replayEnabled) {
            def response = replayResponse(Customer)
            customer = response instanceof GetCustomerResponse ? response?.customer : response
        } else {
            String cacheKey = getCacheKey(CUSTOMER_CACHE_PREFIX, customerId)
            String jsonResponse = redisService.get cacheKey
            if (jsonResponse && cachingEnabled) {
                customer = gson.fromJson(jsonResponse, Customer)
            } else {
                customer = getCustomerRemote(customerId)
                if (customer?.id && cachingEnabled) {
                    cacheObjectInBackground(cacheKey, customer, CUSTOMER_CACHE_EXPIRE)
                }
            }
        }

        def getCustomerResponse = new GetCustomerResponse(customer: customer)
        return getResponse(customer, objectFactory.createGetCustomerResponse(getCustomerResponse))

    }

    List<Customer> getCustomers() {
        List<Customer> customers
        Type listType = new TypeToken<ArrayList<Customer>>() {}.getType();

        if (replayEnabled) {
            customers = replayResponse(listType)
        } else {
            String cacheKey = getCacheKey(CUSTOMER_CACHE_PREFIX)
            String jsonResponse = redisService.get cacheKey
            if (jsonResponse) {
                customers = gson.fromJson(jsonResponse, listType)
            } else {
                customers = getCustomersRemote()
                if (customers) {
                    cacheObjectInBackground(cacheKey, customers, CUSTOMER_CACHE_EXPIRE)
                }
            }
        }

        def getCustomersResponse = new GetCustomersResponse(customers: customers)
        return getResponse(customers, objectFactory.createGetCustomersResponse(getCustomersResponse))
    }

    @Override
    Customer makePayment(
            @WebParam(name = "customerId", targetNamespace = "") int customerId,
            @WebParam(name = "paymentDate", targetNamespace = "") Date paymentDate,
            @WebParam(name = "paymentAmount", targetNamespace = "") Double paymentAmount) {
        return null
    }

    Customer getMockCustomer(String customerId) {
        def params = [:]

        if (customerId) params << [customerId: customerId]

        GetCustomerResponseDomain getCustomerResponseDomain = getDomainClass(GetCustomerResponseDomain, params)

        GetCustomerResponse customerResponse =
                (GetCustomerResponse) responseMarshallerService.unmarshalAndEcho(
                        getCustomerResponseDomain,
                        GetCustomerResponseDomain,
                        [customerId: customerId]
                )

        customerResponse?.getCustomer()
    }


    @HystrixCommand(commandProperties = [
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")
    ], threadPoolProperties = [
            @HystrixProperty(name = "coreSize", value = "10"),
            @HystrixProperty(name = "maxQueueSize", value = "15"),
            @HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
            @HystrixProperty(name = "queueSizeRejectionThreshold", value = "12")
    ])
    Customer getCustomerRemote(Integer customerId) {
        Customer customer = new Customer()
        try {
            customer = customerServiceClient.getCustomer(customerId)
        } catch (Exception e) {
            log.error(e)
        }

        customer
    }


    @HystrixCommand(commandProperties = [
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")
    ], threadPoolProperties = [
            @HystrixProperty(name = "coreSize", value = "10"),
            @HystrixProperty(name = "maxQueueSize", value = "15"),
            @HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
            @HystrixProperty(name = "queueSizeRejectionThreshold", value = "12")
    ])
    List<Customer> getCustomersRemote() {
        List<Customer> customers = new ArrayList<Customer>()
        try {
            customers = customerServiceClient.getCustomers()
        } catch (Exception e) {
            log.error(e)
        }

        customers
    }

}
