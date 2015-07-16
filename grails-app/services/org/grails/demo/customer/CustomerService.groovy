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
    Customer getCustomer(
            @WebParam(name = "CustomerId", targetNamespace = "")
                    int customerId,
            @WebParam(name = "FirstName", targetNamespace = "")
                    String firstName
    ) {
        Customer customer

        if (mockEnabled) {
            customer = getMockCustomer(customerId?.toString(), firstName)
        } else if (replayEnabled) {
            def response = replayResponse(Customer)
            customer = response instanceof GetCustomerResponse ? response?.customer : response
        } else {
            String cacheKey = getCacheKey(CUSTOMER_CACHE_PREFIX, customerId)
            String jsonResponse = redisService.get cacheKey
            if (jsonResponse && cachingEnabled) {
                customer = gson.fromJson(jsonResponse, Customer)
            } else {
                customer = getCustomerRemote(customerId, firstName)
                if (customer?.customerId && cachingEnabled) {
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
            @WebParam(name = "CustomerId", targetNamespace = "")
                    int customerId,
            @WebParam(name = "PaymentDate", targetNamespace = "")
                    Date paymentDate,
            @WebParam(name = "PaymentAmount", targetNamespace = "")
                    Double paymentAmount
    ) {
        //todo: flush this out if time
        return null
    }

    Customer getMockCustomer(String customerId, String firstName) {
        def params = [:]

        //Match on these in database
        params << [customerId: customerId, firstName: firstName ?: null]

        GetCustomerResponseDomain getCustomerResponseDomain = getDomainClass(GetCustomerResponseDomain, params)

        GetCustomerResponse customerResponse =
                (GetCustomerResponse) responseMarshallerService.unmarshalAndEcho(
                        getCustomerResponseDomain,
                        GetCustomerResponseDomain,
                        //These will be "echo'd" into the response
                        [customerId: customerId, firstName: firstName]
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
    Customer getCustomerRemote(Integer customerId, String firstName) {
        Customer customer = new Customer()
        try {
            customer = customerServiceClient.getCustomer(customerId, firstName)
        } catch(ConnectException connectionException){
            throw connectionException
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
        } catch(ConnectException connectionException){
            throw connectionException
        } catch (Exception e) {
            log.error(e)
        }

        customers
    }

}
