package org.grails.demo

import grails.converters.JSON
import grails.converters.XML

class HomeController {
    def index = {}

    def build() {
        def version = [
                "app_version": "${System.properties["RELEASE_VERSION"]}",
                "build_time" : "${System.properties["BUILD_ID"]}"
        ]
        withFormat {
            xml {
                render version as XML
            }
            json {
                render version as JSON
            }
        }
    }
}
