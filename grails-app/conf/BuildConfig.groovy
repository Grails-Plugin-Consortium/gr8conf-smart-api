grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.fork = [
        // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
        //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

        // configure settings for the test-app JVM, uses the daemon by default
        test   : false,
        // configure settings for the run-app JVM
        run    : false,
        // configure settings for the run-war JVM
        war    : [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve: false],
        // configure settings for the Console UI JVM
        console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {

//    management {
//        dependency 'org.springframework:spring-beans:4.0.7.RELEASE'
//    }
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false
    // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        mavenLocal()
        grailsCentral()
        mavenCentral()
    }

    dependencies {
        compile 'org.apache.cxf.xjc-utils:cxf-xjc-runtime:3.0.2'
        compile(group: 'org.apache.cxf.xjc-utils', name: 'cxf-xjc-runtime', version: '2.6.2') {
            excludes 'activation'
        }
        compile 'org.springframework.security:spring-security-ldap:3.2.7.RELEASE'
        compile 'org.springframework.security:spring-security-core:3.2.7.RELEASE'

        runtime 'com.netflix.hystrix:hystrix-core:1.4.5'
        runtime 'com.netflix.hystrix:hystrix-metrics-event-stream:1.4.5'
        compile 'com.netflix.hystrix:hystrix-javanica:1.4.0'
    }

    plugins {
        build ":tomcat:7.0.55.2"

        runtime ":redis:1.6.6"
        runtime ":grails-melody:1.56.0"

        compile ":scaffolding:2.1.2"
        compile ":jesque:0.8.0-SNAPSHOT"
        compile ':cache:1.1.7'
        compile ':redis-flexible-cache:0.3.3'
        compile ":asset-pipeline:2.0.19"
        compile ":cxf-client:2.0.3"
        compile ":cxf:2.0.1"
        compile ":twitter-bootstrap:3.3.1"

        runtime ":hibernate:3.6.10.19"
        runtime ":jquery:1.11.1"
    }
}
