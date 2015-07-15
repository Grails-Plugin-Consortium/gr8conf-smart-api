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
         <customer>
            <id>1</id>
            <firstName>Duncan</firstName>
            <lastName>MacLeod</lastName>
            <username>duncan@gmail.com</username>
         </customer>
      </ns2:GetCustomerResponse>"""
    }

  String getGetCustomerResponseDummyXml() {
        """<ns2:GetCustomerResponse xmlns:ns2="http://demo.grails.org" xmlns:ns3="http://demo.grails.org/">
         <customer>
            <id>100</id>
            <firstName>Dummy</firstName>
            <lastName>Dummy</lastName>
            <username>dummy@gmail.com</username>
         </customer>
      </ns2:GetCustomerResponse>"""
    }

  String getGetCustomerResponseInvalidCustomerIdXml() {
      """<ns3:GetCustomerResponse xmlns:ns2="http://demo.grails.org/" xmlns:ns3="http://demo.grails.org">
         <customer/>
      </ns3:GetCustomerResponse>"""
    }

    String getGetCustomerResponseInvalidNullCustomerIdXml() {
        """<ns3:GetCustomerResponse xmlns:ns2="http://demo.grails.org/" xmlns:ns3="http://demo.grails.org">
         <customer/>
      </ns3:GetCustomerResponse>"""
    }
}
