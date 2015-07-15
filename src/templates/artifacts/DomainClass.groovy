@artifact.package@

import org.grails.demo.annotations.MarshalTo

@MarshalTo(clazz = Object, field = 'responseXml')
class @artifact.name@ {

    String description
    String responseXml
    Boolean isDefault = false
    //Add keys here

    Date dateCreated
    Date lastUpdated

    static mapping = {
        version true
        autoTimestamp true
        responseXml type: 'text'
    }

    static constraints = {
        description blank: false, nullable: false
        //add keys here like key()

        isDefault()
        responseXml widget: 'textarea'
    }
}
