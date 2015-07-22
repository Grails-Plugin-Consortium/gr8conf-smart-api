package org.grails.demo.customer

import com.google.gson.reflect.TypeToken
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty
import org.grails.cxf.utils.GrailsCxfEndpoint
import org.grails.demo.BaseCacheService
import org.grails.demo.data.CustomerRefreshJob
import org.grails.demo.soap.customer.Customer
import org.grails.demo.soap.customer.GetCustomerResponse
import org.grails.demo.soap.customer.GetCustomersResponse
import org.grails.demo.soap.customer.ObjectFactory
import org.springframework.beans.factory.annotation.Value

import javax.jws.WebParam
import javax.xml.ws.soap.SOAPFaultException
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
        Customer customer = cacheMockRecordReplayGetCustomer(customerId, firstName)
        def getCustomerResponse = new GetCustomerResponse(customer: customer)
        return getResponse(customer, objectFactory.createGetCustomerResponse(getCustomerResponse))

    }

    private Customer cacheMockRecordReplayGetCustomer(int customerId, String firstName) {
        Customer customer
        if (mockEnabled) {
            customer = getMockCustomer(customerId?.toString(), firstName)
        } else if (replayEnabled) {
            def response = replayResponse(Customer)
            customer = response instanceof GetCustomerResponse ? response?.customer : response
        } else {
            String cacheKey = getCacheKey(CUSTOMER_CACHE_PREFIX, customerId, firstName)
            String jsonResponse = redisService.get cacheKey
            if (jsonResponse && cachingEnabled) {
                customer = gson.fromJson(jsonResponse, Customer)
            } else {
                customer = getCustomerRemote(customerId, firstName)
                if (customer && cachingEnabled) {
                    cacheObjectInBackground(cacheKey, customer, CUSTOMER_CACHE_EXPIRE)
                }
            }
        }
        customer
    }

    @Override
    List<Customer> getCustomers() {
        List<Customer> customers = cacheMockRecordReplayGetCustomers()
        def getCustomersResponse = new GetCustomersResponse(customers: customers)
        return getResponse(customers, objectFactory.createGetCustomersResponse(getCustomersResponse))
    }

    private List<Customer> cacheMockRecordReplayGetCustomers() {
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
        customers
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
        Customer customer = customerServiceClient.makePayment(customerId, paymentDate, paymentAmount)
        if(customer?.customerId) {
            refreshCustomerCache(customer?.customerId?.intValue(), customer?.firstName)
        }
        customer
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
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "30000")
    ], threadPoolProperties = [
            @HystrixProperty(name = "coreSize", value = "10"),
            @HystrixProperty(name = "maxQueueSize", value = "20"),
            @HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
            @HystrixProperty(name = "queueSizeRejectionThreshold", value = "18")
    ])
    Customer getCustomerRemote(Integer customerId, String firstName) {
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

    Customer getCustomerAndCache(Integer customerId, String firstName, Boolean spawnThread = true) {
        Customer response = customerServiceClient.getCustomer(customerId, firstName)
        //For this demo refresh both the id:name cache and the id cache keys
        cacheObjectInBackground(getCacheKey(CUSTOMER_CACHE_PREFIX, customerId, firstName), response, CUSTOMER_CACHE_EXPIRE, spawnThread)
        cacheObjectInBackground(getCacheKey(CUSTOMER_CACHE_PREFIX, customerId), response, CUSTOMER_CACHE_EXPIRE, spawnThread)
        response
    }

    void refreshCustomerCache(Integer customerId, String firstName) {
        jesqueService.enqueue(CustomerRefreshJob.queue, CustomerRefreshJob.simpleName, customerId, firstName)
    }
}
