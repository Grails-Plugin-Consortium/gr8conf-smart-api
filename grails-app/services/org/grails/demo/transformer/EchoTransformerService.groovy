package org.grails.demo.transformer

import groovy.xml.XmlUtil

class EchoTransformerService {

    def transform(String xml, Map map) {
        try {

            def slurped = new XmlSlurper().parseText(xml)

            map.each { key, value ->
                //todo: maybe remove this if you want to echo values of 'groovy false'
                if(value) {
                    slurped.depthFirst().findAll {
                        it.name()?.toString()?.toLowerCase() == key?.toString()?.toLowerCase()
                    }*.replaceBody(value)
                }
            }

            return XmlUtil.serialize( slurped )
        } catch (Exception e) {
            e.printStackTrace();
        }

        xml
    }
}
