package org.grails.demo.bootstrap

import org.grails.demo.customer.GetCustomerResponseDomain


class CustomerBootStrapDataService extends AbstractBootStrapDataService {

    void loadDefault() {
        GetCustomerResponseDomain.findOrSaveWhere(description: 'Default', isDefault: true, responseXml: getGetCustomerResponseXml())
    }

    void loadTestData() {
        GetCustomerResponseDomain.findOrSaveWhere(description: 'Invalid Customer ID', customerId: '10', responseXml: getGetCustomerResponseInvalidCustomerIdXml())
        GetCustomerResponseDomain.findOrSaveWhere(description: 'Null Customer ID', customerId: '11', responseXml: getGetCustomerResponseInvalidCustomerIdXml())
        GetCustomerResponseDomain.findOrSaveWhere(description: 'Dummy Customer', customerId: '12', responseXml: getGetCustomerResponseDummyXml())
    }

    String getGetCustomerResponseXml() {
        """<ns2:GetCustomerResponse xmlns:ns2="http://demo.grails.org" xmlns:ns3="http://demo.grails.org/">
         <Customer>
            <ID>100</ID>
            <FirstName>Demo</FirstName>
            <LastName>Customer</LastName>
            <Username>demo@gmail.com</Username>
            <Payments>
               <Payment>
                  <Date>2015-07-15T16:03:15.794-05:00</Date>
                  <Amount>19.99</Amount>
               </Payment>
               <Payment>
                  <PaymentDate>2015-06-15T16:03:15.794-05:00</PaymentDate>
                  <PaymentAmount>19.99</PaymentAmount>
               </Payment>
            </Payments>
         </Customer>
      </ns2:GetCustomerResponse>"""
    }

  String getGetCustomerResponseDummyXml() {
        """<ns2:GetCustomerResponse xmlns:ns2="http://demo.grails.org" xmlns:ns3="http://demo.grails.org/">
         <Customer>
            <ID>1</ID>
            <FirstName>Duncan</FirstName>
            <LastName>MacLeod</LastName>
            <Username>duncan@gmail.com</Username>
            <Payments>
               <Payment>
                  <PaymentDate>2015-07-15T16:03:15.794-05:00</PaymentDate>
                  <PaymentAmount>9.99</PaymentAmount>
               </Payment>
            </Payments>
         </Customer>
      </ns2:GetCustomerResponse>"""
    }

  String getGetCustomerResponseInvalidCustomerIdXml() {
      """<ns2:GetCustomerResponse xmlns:ns2="http://demo.grails.org" xmlns:ns3="http://demo.grails.org/">
         <Customer></Customer>
      </ns2:GetCustomerResponse>"""
    }
}
