package org.grails.demo.bootstrap

import org.grails.demo.customer.GetCustomerResponseDomain


class CustomerBootStrapDataService extends AbstractBootStrapDataService {

    void loadDefault() {
        GetCustomerResponseDomain.findOrSaveWhere(description: 'Default', isDefault: true, responseXml: getDefaultResponseXml())
    }

    void loadTestData() {
        GetCustomerResponseDomain.findOrSaveWhere(description: 'Customer Id and Name', customerId: '9', firstName: 'Christian', responseXml: getChristianCustomerXml())
        GetCustomerResponseDomain.findOrSaveWhere(description: 'No Payments Customer', customerId: '9', responseXml: getNoPaymentsCustomerXml())
        GetCustomerResponseDomain.findOrSaveWhere(description: 'Invalid Customer ID', customerId: '10', responseXml: getGetCustomerResponseInvalidCustomerIdXml())
        GetCustomerResponseDomain.findOrSaveWhere(description: 'Dummy Customer', customerId: '11', responseXml: getGetCustomerResponseDummyXml())
    }

    String getChristianCustomerXml(){
        """<ns2:GetCustomerResponse xmlns:ns2="http://demo.grails.org" xmlns:ns3="http://demo.grails.org/">
         <Customer>
            <CustomerId>8</CustomerId>
            <FirstName>Clark</FirstName>
            <LastName>Kent</LastName>
            <Username>superman@gmail.com</Username>
            <Payments>
               <Payment>
                  <Date>2015-01-01:01:01.000-06:00</Date>
                  <Amount>1000000000.00</Amount>
               </Payment>
            </Payments>
         </Customer>
      </ns2:GetCustomerResponse>"""
    }

  String getNoPaymentsCustomerXml(){
        """<ns2:GetCustomerResponse xmlns:ns2="http://demo.grails.org" xmlns:ns3="http://demo.grails.org/">
         <Customer>
            <CustomerId>11</CustomerId>
            <FirstName></FirstName>
            <LastName>Customer</LastName>
            <Username>nopayments@gmail.com</Username>
            <Payments />
         </Customer>
      </ns2:GetCustomerResponse>"""
    }

    String getDefaultResponseXml() {
        """<ns2:GetCustomerResponse xmlns:ns2="http://demo.grails.org" xmlns:ns3="http://demo.grails.org/">
         <Customer>
            <CustomerId>100</CustomerId>
            <FirstName>Default</FirstName>
            <LastName>Customer</LastName>
            <Username>default@gmail.com</Username>
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
            <CustomerId>1</CustomerId>
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
