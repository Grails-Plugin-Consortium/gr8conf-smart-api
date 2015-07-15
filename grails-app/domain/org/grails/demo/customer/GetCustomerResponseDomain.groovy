package org.grails.demo.customer

import org.grails.demo.annotations.MarshalTo
import org.grails.demo.soap.customer.GetCustomerResponse

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement

@MarshalTo(clazz = GetCustomerResponse, field = 'responseXml')
class GetCustomerResponseDomain {

    String description
    String responseXml
    Boolean isDefault = false
    String customerId
    Date dateCreated
    Date lastUpdated

    static mapping = {
        version true
        autoTimestamp true
        responseXml type: 'text'
    }

    static constraints = {
        description blank: false, nullable: false
        customerId  blank: true, nullable: true
        isDefault()

        responseXml widget: 'textarea'
    }
}
