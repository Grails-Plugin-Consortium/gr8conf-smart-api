
package org.grails.demo.soap.customer;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.grails.demo.soap.customer package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetCustomersResponse_QNAME = new QName("http://demo.grails.org", "GetCustomersResponse");
    private final static QName _MakePaymentResponse_QNAME = new QName("http://demo.grails.org", "MakePaymentResponse");
    private final static QName _GetCustomerResponse_QNAME = new QName("http://demo.grails.org", "GetCustomerResponse");
    private final static QName _GetCustomers_QNAME = new QName("http://demo.grails.org/", "GetCustomers");
    private final static QName _GetCustomer_QNAME = new QName("http://demo.grails.org/", "GetCustomer");
    private final static QName _MakePayment_QNAME = new QName("http://demo.grails.org/", "MakePayment");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.grails.demo.soap.customer
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Customer }
     * 
     */
    public Customer createCustomer() {
        return new Customer();
    }

    /**
     * Create an instance of {@link GetCustomersResponse }
     * 
     */
    public GetCustomersResponse createGetCustomersResponse() {
        return new GetCustomersResponse();
    }

    /**
     * Create an instance of {@link MakePaymentResponse }
     * 
     */
    public MakePaymentResponse createMakePaymentResponse() {
        return new MakePaymentResponse();
    }

    /**
     * Create an instance of {@link GetCustomerResponse }
     * 
     */
    public GetCustomerResponse createGetCustomerResponse() {
        return new GetCustomerResponse();
    }

    /**
     * Create an instance of {@link MakePayment }
     * 
     */
    public MakePayment createMakePayment() {
        return new MakePayment();
    }

    /**
     * Create an instance of {@link GetCustomer }
     * 
     */
    public GetCustomer createGetCustomer() {
        return new GetCustomer();
    }

    /**
     * Create an instance of {@link GetCustomers }
     * 
     */
    public GetCustomers createGetCustomers() {
        return new GetCustomers();
    }

    /**
     * Create an instance of {@link Payment }
     * 
     */
    public Payment createPayment() {
        return new Payment();
    }

    /**
     * Create an instance of {@link Customer.Payments }
     * 
     */
    public Customer.Payments createCustomerPayments() {
        return new Customer.Payments();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCustomersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://demo.grails.org", name = "GetCustomersResponse")
    public JAXBElement<GetCustomersResponse> createGetCustomersResponse(GetCustomersResponse value) {
        return new JAXBElement<GetCustomersResponse>(_GetCustomersResponse_QNAME, GetCustomersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakePaymentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://demo.grails.org", name = "MakePaymentResponse")
    public JAXBElement<MakePaymentResponse> createMakePaymentResponse(MakePaymentResponse value) {
        return new JAXBElement<MakePaymentResponse>(_MakePaymentResponse_QNAME, MakePaymentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCustomerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://demo.grails.org", name = "GetCustomerResponse")
    public JAXBElement<GetCustomerResponse> createGetCustomerResponse(GetCustomerResponse value) {
        return new JAXBElement<GetCustomerResponse>(_GetCustomerResponse_QNAME, GetCustomerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCustomers }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://demo.grails.org/", name = "GetCustomers")
    public JAXBElement<GetCustomers> createGetCustomers(GetCustomers value) {
        return new JAXBElement<GetCustomers>(_GetCustomers_QNAME, GetCustomers.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCustomer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://demo.grails.org/", name = "GetCustomer")
    public JAXBElement<GetCustomer> createGetCustomer(GetCustomer value) {
        return new JAXBElement<GetCustomer>(_GetCustomer_QNAME, GetCustomer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakePayment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://demo.grails.org/", name = "MakePayment")
    public JAXBElement<MakePayment> createMakePayment(MakePayment value) {
        return new JAXBElement<MakePayment>(_MakePayment_QNAME, MakePayment.class, null, value);
    }

}
