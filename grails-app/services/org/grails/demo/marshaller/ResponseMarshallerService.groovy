package org.grails.demo.marshaller

import grails.converters.XML
import org.apache.cxf.staxutils.DelegatingXMLStreamWriter
import org.codehaus.groovy.grails.commons.GrailsClassUtils
import org.grails.demo.annotations.MarshalTo
import org.grails.demo.transformer.EchoTransformerService

import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBElement
import javax.xml.bind.Marshaller
import javax.xml.bind.Unmarshaller
import javax.xml.namespace.NamespaceContext
import javax.xml.stream.XMLOutputFactory
import javax.xml.stream.XMLStreamException
import javax.xml.stream.XMLStreamWriter

class ResponseMarshallerService {

    EchoTransformerService echoTransformerService

    public <T> T unmarshal(String object, T type) {
        unmarshalAndEcho(object, type, [:])
    }

    public <T> T unmarshalAndEcho(Object object, T type, Map echoKVP = [:]) {

        String xml = ""
        def result = null

        if (type && object && object instanceof String) {
            xml = object?.toString()
        } else {
            Class objectClass = object?.class
            MarshalTo marshalTo = (MarshalTo) objectClass?.declaredAnnotations?.find {
                it.annotationType() == MarshalTo
            }
            type = (T) marshalTo?.clazz()
            String field = marshalTo?.field()
            if (field) {
                xml = GrailsClassUtils.getPropertyOrStaticPropertyOrFieldValue(object, field)
            }
        }

        if (type) {
            result = unmarshal(echoKVP, xml, type)
        }

        (T) (result ?: type?.newInstance())
    }

    public unmarshal(Map echoKVP, String xml, Class clazz) {
        def result
        if (echoKVP && xml) {
            xml = echoTransformerService.transform(xml, echoKVP)
        }

        if (xml) {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz.package.name, this.class.classLoader)
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//            StreamSource streamSource = new StreamSource(new StringReader(xml));
//            JAXBElement<T> je = unmarshaller.unmarshal(streamSource, clazz);
//            result = (T) je.value;
            InputStream inputStream = new ByteArrayInputStream(xml.bytes)
            result = unmarshaller.unmarshal(inputStream)?.value
        }
        result
    }

    String marshal(Object object, JAXBElement jaxbElement) {
        XML.use('deep') {
            println object as XML
        }
        StringWriter writer = new StringWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.class.package.name, this.class.classLoader)
            Marshaller marshaller = jaxbContext.createMarshaller();
//            marshaller.marshal(jaxbElement, NoNamesWriter.filter(writer));
            marshaller.marshal(jaxbElement, writer);
        } catch (Exception e) {
            log.error('Could not marshal object', e)
        }
        return writer.toString()
    }
}

public class NoNamesWriter extends DelegatingXMLStreamWriter {

    private static final NamespaceContext emptyNamespaceContext = new NamespaceContext() {

        @Override
        public String getNamespaceURI(String prefix) {
            return "";
        }

        @Override
        public String getPrefix(String namespaceURI) {
            return "";
        }

        @Override
        public Iterator getPrefixes(String namespaceURI) {
            return null;
        }

    };

    public static XMLStreamWriter filter(Writer writer) throws XMLStreamException {
        return new NoNamesWriter(XMLOutputFactory.newInstance().createXMLStreamWriter(writer));
    }

    public NoNamesWriter(XMLStreamWriter writer) {
        super(writer);
    }

    @Override
    public NamespaceContext getNamespaceContext() {
        return emptyNamespaceContext;
    }

}